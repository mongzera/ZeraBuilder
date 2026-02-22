import com.chemicaldev.zerabuilder.dsl.InsertDSL;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsertBuilderTest {

    @Test
    void mysql_insert_should_generate_sql_and_parameters() {

        InsertDSL insert = new ZeraBuilder(SQLDialect.MYSQL)
                .insertTo("users")
                .columns("email", "age")
                .values("john@mail.com", 25);

        String sql = insert.toString();
        Object[] params = insert.getParameters();

        System.out.println(sql);

        assertEquals(
                "INSERT INTO users (email, age) VALUES (?, ?);",
                sql
        );

        assertEquals(2, params.length);
        assertEquals("john@mail.com", params[0]);
        assertEquals(25, params[1]);
    }

    @Test
    void sqlite_insert_should_generate_sql_and_parameters() {

        InsertDSL insert = new ZeraBuilder(SQLDialect.SQLITE)
                .insertTo("users")
                .columns("email", "age")
                .values("jane@mail.com", 30);

        String sql = insert.toString();
        Object[] params = insert.getParameters();

        System.out.println(sql);

        assertEquals(
                "INSERT INTO users (email, age) VALUES (?, ?);",
                sql
        );

        assertEquals(2, params.length);
        assertEquals("jane@mail.com", params[0]);
        assertEquals(30, params[1]);
    }
}