import com.chemicaldev.zerabuilder.dsl.*;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DSLSqliteTest {

    ZeraBuilder builder = new ZeraBuilder(SQLDialect.SQLITE);

    @Test
    void testSelectDSL() {
        SelectDSL select = new SelectDSL(builder)
                .list("id", "name")
                .from("users")
                .where(Conditions.eq("status", "ACTIVE"))
                .and(Conditions.gt("age", 18))
                .limit(10);

        assertEquals(
                "SELECT id, name FROM users WHERE status = ? AND age > ? LIMIT 10;",
                select.toString()
        );

        assertArrayEquals(
                new Object[]{"ACTIVE", 18},
                select.getParams()
        );
    }

    @Test
    void testInsertDSL() {
        InsertDSL insert = new InsertDSL(builder)
                .into("users")
                .columns("name", "email")
                .values("Alice", "alice@test.com");

        assertEquals(
                "INSERT INTO users (name, email) VALUES (?, ?);",
                insert.toString()
        );

        assertArrayEquals(
                new Object[]{"Alice", "alice@test.com"},
                insert.getParams()
        );
    }

    @Test
    void testUpdateDSL() {
        UpdateDSL update = new UpdateDSL(builder)
                .table("users")
                .set(new String[]{"name", "email"}, "Bob", "bob@test.com")
                .where(Conditions.eq("id", 1));

        assertEquals(
                "UPDATE users SET name = ?, email = ? WHERE id = ?;",
                update.toString()
        );

        assertArrayEquals(
                new Object[]{"Bob", "bob@test.com", 1},
                update.getParams()
        );
    }

    @Test
    void testDeleteDSL() {
        DeleteDSL delete = new DeleteDSL(builder)
                .from("users")
                .where(Conditions.eq("status", "INACTIVE"));

        assertEquals(
                "DELETE FROM users WHERE status = ?;",
                delete.toString()
        );

        assertArrayEquals(
                new Object[]{"INACTIVE"},
                delete.getParams()
        );
    }

    @Test
    void testAlterAddColumn() {
        AlterDSL alter = new AlterDSL(builder)
                .table("users")
                .addColumn("email", "TEXT");

        assertEquals(
                "ALTER TABLE users ADD COLUMN email TEXT;",
                alter.toString()
        );
    }
}
