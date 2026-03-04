import com.chemicaldev.zerabuilder.dsl.InsertDSL;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

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
}