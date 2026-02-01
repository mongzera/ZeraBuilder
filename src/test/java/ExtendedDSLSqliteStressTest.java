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
public class ExtendedDSLSqliteStressTest {

    private static final String JDBC_URL = "jdbc:sqlite:stress_test.db";
    private static final ZeraBuilder builder = new ZeraBuilder(SQLDialect.SQLITE);

    @Test
    @Order(1)
    void createTable() throws SQLException {
        CreateDSL create = builder.makeTable("users")
                .column("id INTEGER")
                .column("email TEXT")
                .column("age INTEGER")
                .primaryKey("id")
                .unique("email");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "", "")) {
            new ZeraExecutor(conn).update(create);
        }
    }

    @Test
    @Order(2)
    void insertManyRows() throws SQLException {
        InsertDSL insert = builder.insertTo("users")
                .columns("id", "email", "age");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "", "")) {
            ZeraExecutor exec = new ZeraExecutor(conn);

            for (int i = 1; i <= 10_000; i++) {
                insert.values(i, "user" + i + "@test.com", 18 + (i % 50));
                exec.update(insert);
            }
        }
    }

    @Test
    @Order(3)
    void selectAndValidate() throws SQLException {
        SelectDSL select = builder.list("*").from("users");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "", "")) {
            ZeraExecutor exec = new ZeraExecutor(conn);

            List<Integer> ids = exec.query(select, rs -> rs.getInt("id"));

            Assertions.assertEquals(10_000, ids.size());
        }
    }
}
