import com.chemicaldev.zerabuilder.dsl.*;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExtendedDSLTest {
    ZeraBuilder builder = new ZeraBuilder(SQLDialect.MYSQL);

    // -------------------- SELECT --------------------
    @Test
    public void testSelectDSLComplexConditions() {


        SelectDSL select = new SelectDSL(builder)
                .list("id", "name", "email")
                .from("users")
                .where(Conditions.eq("status", "ACTIVE"))
                .and(Conditions.gt("age", 18))
                .or(Conditions.eq("role", "admin"))
                .groupBy("role")
                .orderBy("name")
                .limit(5);

        String expectedSql = "SELECT id, name, email FROM users WHERE (status = ? AND age > ? OR role = ?) GROUP BY role ORDER BY name LIMIT 5;";
        assertEquals(expectedSql, select.toString());

        Object[] expectedParams = {"ACTIVE", 18, "admin"};
        assertArrayEquals(expectedParams, select.getParameters());
    }

    // -------------------- INSERT --------------------
    @Test
    public void testInsertDSLMultipleColumns() {
        InsertDSL insert = new InsertDSL(builder)
                .into("users")
                .columns("name", "email", "age", "role")
                .values("Alice", "alice@mail.com", 30, "user");

        String expectedSql = "INSERT INTO users (name, email, age, role) VALUES (?, ?, ?, ?);";
        assertEquals(expectedSql, insert.build().toString());

        Object[] expectedParams = {"Alice", "alice@mail.com", 30, "user"};
        assertArrayEquals(expectedParams, insert.getParameters());
    }

    // -------------------- UPDATE --------------------
    @Test
    public void testUpdateDSLMultipleSets() {
        UpdateDSL update = new UpdateDSL(builder)
                .table("users")
                .set(new String[]{"name","email"}, "Bob", "bob@mail.com")
                .set("age", 35)
                .where(Conditions.eq("id", 1))
                .and(Conditions.eq("status", "ACTIVE"));

        String expectedSql = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ? AND status = ?;";
        assertEquals(expectedSql, update.build().toString());

        Object[] expectedParams = {"Bob", "bob@mail.com", 35, 1, "ACTIVE"};
        assertArrayEquals(expectedParams, update.getParams());
    }

    // -------------------- DELETE --------------------
    @Test
    public void testDeleteDSLMultipleConditions() {
        DeleteDSL delete = new DeleteDSL(builder)
                .from("users")
                .where(Conditions.eq("status", "INACTIVE"))
                .and(Conditions.lt("age", 18))
                .or(Conditions.eq("role", "banned"));

        String expectedSql = "DELETE FROM users WHERE (status = ? AND age < ? OR role = ?);";
        assertEquals(expectedSql, delete.build().toString());

        Object[] expectedParams = {"INACTIVE", 18, "banned"};
        assertArrayEquals(expectedParams, delete.getParams());
    }

    // -------------------- CREATE --------------------
    @Test
    public void testCreateDSLMultipleConstraints() {
        CreateDSL create = new CreateDSL(builder)
                .table("users")
                .column("id", "INT AUTO_INCREMENT")
                .column("name", "VARCHAR(255)")
                .column("email", "VARCHAR(255)")
                .column("role", "VARCHAR(50)")
                .primaryKey("id")
                .unique("email", "role");

        String expectedSql =
                "CREATE TABLE users (\n" +
                        "    id INT AUTO_INCREMENT,\n" +
                        "    name VARCHAR(255),\n" +
                        "    email VARCHAR(255),\n" +
                        "    role VARCHAR(50),\n" +
                        "    PRIMARY KEY (id),\n" +
                        "    UNIQUE (email, role)\n" +
                        ");";

        assertEquals(expectedSql, create.toString());
    }

    // -------------------- ALTER --------------------
    @Test
    public void testAlterDSLMultipleActions() {
        AlterDSL alter = new AlterDSL(builder)
                .table("users")
                .addColumn("age", "INT")
                .addColumn("last_login", "DATETIME")
                .dropColumn("old_column")
                .renameColumn("username", "user_name")
                .renameTable("customers");

        String expectedSql = "ALTER TABLE users ADD COLUMN age INT, ADD COLUMN last_login DATETIME, DROP COLUMN old_column, RENAME COLUMN username TO user_name, RENAME TO customers;";
        assertEquals(expectedSql, alter.toString());
    }

    // -------------------- EDGE CASES --------------------
    @Test
    public void testEmptyWhereThrows() {
        DeleteDSL delete = new DeleteDSL(builder).from("users");
        // No where, should not throw, just generate DELETE FROM users;
        String expectedSql = "DELETE FROM users;";
        assertEquals(expectedSql, delete.build().toString());
    }

    @Test
    public void testUpdateWithoutSetThrows() {
        UpdateDSL update = new UpdateDSL(builder).table("users").where(Conditions.eq("id", 1));
        Exception exception = assertThrows(IllegalStateException.class, update::toString);
        assertTrue(exception.getMessage().contains("Table and at least one SET clause must be specified"));
    }
}
