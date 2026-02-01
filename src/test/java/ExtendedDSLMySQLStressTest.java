import com.chemicaldev.zerabuilder.db.ZeraConnection;
import com.chemicaldev.zerabuilder.db.ZeraExecutor;
import com.chemicaldev.zerabuilder.dsl.CreateDSL;
import com.chemicaldev.zerabuilder.dsl.InsertDSL;
import com.chemicaldev.zerabuilder.dsl.SelectDSL;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExtendedDSLMySQLStressTest {

    private static final String JDBC_URL =
            "jdbc:mysql://127.0.0.1:3306/testdb?useSSL=false&serverTimezone=UTC";

    private static final ZeraBuilder builder = new ZeraBuilder(SQLDialect.MYSQL);

    @Test
    @Order(1)
    void createTable() throws SQLException {
        CreateDSL create = builder.makeTable("customers")
                .column("id INT")
                .column("email VARCHAR(255)")
                .column("age INT")
                .primaryKey("id")
                .unique("email");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "root", "")) {
            new ZeraExecutor(conn).update(create);
        }
    }

    @Test
    @Order(2)
    void insertManyRows() throws SQLException {
        InsertDSL insert = builder.insertTo("customers")
                .columns("id", "email", "age");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "root", "")) {
            ZeraExecutor exec = new ZeraExecutor(conn);

            for (int i = 1; i <= 20_000; i++) {
                insert.values(i, "user" + i + "@mysql.com", 20 + (i % 40));
                exec.update(insert);
            }
        }
    }

    @Test
    @Order(3)
    void selectAndValidate() throws SQLException {
        SelectDSL select = builder.list("id").from("customers");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "root", "")) {
            ZeraExecutor exec = new ZeraExecutor(conn);

            List<Integer> ids = exec.query(select, rs -> rs.getInt(1));

            Assertions.assertEquals(20_000, ids.size());
        }
    }
}
