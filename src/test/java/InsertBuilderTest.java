import com.chemicaldev.zerabuilder.db.ZeraConnection;
import com.chemicaldev.zerabuilder.db.ZeraExecutor;
import com.chemicaldev.zerabuilder.dsl.CreateDSL;
import com.chemicaldev.zerabuilder.dsl.InsertDSL;
import com.chemicaldev.zerabuilder.dsl.datatype.DataType;
import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InsertBuilderTest {

    @Test
    void mysql_insert_should_generate_sql_and_parameters() {

        InsertDSL insert = new ZeraBuilder(SQLDialect.MYSQL).insertTo("users").columns("email", "age");

        String sql = insert.toString();

        System.out.println(sql);

        assertEquals(
                "INSERT INTO users (email, age) VALUES (?, ?);",
                sql
        );
    }

    @Test
    void sqlite_insert_should_generate_sql_and_parameters() {

        InsertDSL insert = new ZeraBuilder(SQLDialect.SQLITE)
                .insertTo("users")
                .columns("email", "age", "sex");

        String sql = insert.toString();

        System.out.println(sql);

        assertEquals(
                "INSERT INTO users (email, age, sex) VALUES (?, ?, ?);",
                sql
        );
    }

    @Test
    void sqlite_insert_should_be_successful() throws SQLException {

        ZeraBuilder builder = new ZeraBuilder(SQLDialect.SQLITE);
        ZeraConnection conn = new ZeraConnection(String.format("jdbc:sqlite:%s.db", "hallo"), "", "");
        ZeraExecutor executor = new ZeraExecutor(conn);

        CreateDSL users = builder.createTable("users");
        users.column("age").type(Datatypes.integer()).notNull().done();
        users.column("name").type(Datatypes.string()).notNull().done();
        System.out.println(users.toString());
        PreparedStatement stmt = conn.prepareStatement(users);
        executor.update(stmt);

        InsertDSL insert = builder.insertTo("users").columns("age", "name");
        System.out.println(insert.toString());

        ArrayList<Object[]> batch = new ArrayList<>();
        batch.add(new Object[]{21, "John"});
        batch.add(new Object[]{21, "Jane"});
        batch.add(new Object[]{20, "Fox"});
        PreparedStatement insStmt = conn.prepareStatement(insert);
        executor.setBatch(insStmt, batch);

    }
}