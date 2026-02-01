import com.chemicaldev.zerabuilder.dsl.*;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtendedDSLSqliteTest {

    ZeraBuilder builder = new ZeraBuilder(SQLDialect.SQLITE);
    /* ----------------------------- SELECT ----------------------------- */

    @Test
    void testSQLiteSelectSimple() {
        String sql = new SelectDSL(builder)
                .list("id", "name")
                .from("users")
                .toString();

        assertEquals(
                "SELECT id, name FROM users;",
                sql
        );
    }

    @Test
    void testSQLiteSelectWithWhereAndOr() {
        SelectDSL dsl = new SelectDSL(builder)
                .list("*")
                .from("users")
                .where(Conditions.eq("status", "ACTIVE"))
                .and(Conditions.gt("age", 18))
                .or(Conditions.eq("role", "admin"));

        assertEquals(
                "SELECT * FROM users WHERE (status = ? AND age > ? OR role = ?);",
                dsl.toString()
        );

        assertArrayEquals(
                new Object[]{"ACTIVE", 18, "admin"},
                dsl.getParameters()
        );
    }

    /* ----------------------------- CREATE ----------------------------- */

    @Test
    void testSQLiteCreateTable() {
        String sql = new CreateDSL(builder)
                .table("users")
                .column("id INTEGER")
                .column("email TEXT")
                .primaryKey("id")
                .unique("email")
                .toString();

        assertEquals(
                "CREATE TABLE users (id INTEGER, email TEXT, PRIMARY KEY (id), UNIQUE (email));",
                sql
        );
    }

    /* ----------------------------- INSERT ----------------------------- */

    @Test
    void testSQLiteInsert() {
        InsertDSL dsl = new InsertDSL(builder)
                .into("users")
                .columns("name", "age")
                .values("?", "?");

        assertEquals(
                "INSERT INTO users (name, age) VALUES (?, ?);",
                dsl.toString()
        );
    }

    /* ----------------------------- UPDATE ----------------------------- */

    @Test
    void testSQLiteUpdateWithWhere() {
        UpdateDSL dsl = new UpdateDSL(builder)
                .table("users")
                .set("name", "Alice")
                .set("age", 30)
                .where(Conditions.eq("id", 1));

        assertEquals(
                "UPDATE users SET name = ?, age = ? WHERE id = ?;",
                dsl.toString()
        );

        assertArrayEquals(
                new Object[]{"Alice", 30, 1},
                dsl.getParams()
        );
    }

    /* ----------------------------- DELETE ----------------------------- */

    @Test
    void testSQLiteDelete() {
        DeleteDSL dsl = new DeleteDSL(builder)
                .from("users")
                .where(Conditions.eq("status", "INACTIVE"));

        assertEquals(
                "DELETE FROM users WHERE status = ?;",
                dsl.toString()
        );

        assertArrayEquals(
                new Object[]{"INACTIVE"},
                dsl.getParams()
        );
    }

    @Test
    void testSQLiteDeleteWithAndOr() {
        DeleteDSL dsl = new DeleteDSL(builder)
                .from("users")
                .where(Conditions.eq("status", "INACTIVE"))
                .and(Conditions.lt("age", 18))
                .or(Conditions.eq("role", "banned"));

        assertEquals(
                "DELETE FROM users WHERE status = ? AND age < ? OR role = ?;",
                dsl.toString()
        );

        assertArrayEquals(
                new Object[]{"INACTIVE", 18, "banned"},
                dsl.getParams()
        );
    }

    /* ----------------------------- ALTER ----------------------------- */

    @Test
    void testSQLiteAlterUnsupportedFeatures() {
        AlterDSL dsl = new AlterDSL(builder)
                .table("users")
                .dropColumn("age")
                .modifyColumn("name", "TEXT");

        // SQLite builder should no-op unsupported features
        assertEquals("", dsl.toString());
    }
}
