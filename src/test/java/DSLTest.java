import com.chemicaldev.zerabuilder.dsl.*;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DSLTest {

    @Test
    public void testSelectDSL() {
        ZeraBuilder builder = new ZeraBuilder();
        builder.dialect = SQLDialect.MYSQL;


        SelectDSL select = new SelectDSL(builder)
                .list("id", "name")
                .from("users")
                .where(Conditions.eq("status", "ACTIVE"))
                .and(Conditions.gt("age", 18))
                .orderBy("name")
                .limit(10);

        String expectedSql = "SELECT id, name FROM users WHERE (status = ? AND age > ?) ORDER BY name LIMIT 10;";
        assertEquals(expectedSql, select.toString());

        Object[] expectedParams = {"ACTIVE", 18};
        assertArrayEquals(expectedParams, select.getParams());
    }

    @Test
    public void testSelectDSLSQLite(){
        ZeraBuilder builder = new ZeraBuilder();
        builder.dialect = SQLDialect.SQLITE;

        SelectDSL select = new SelectDSL(builder)
                .list("id", "name", "email")
                .from("users")
                .where(Conditions.eq("status", "ACTIVE"))
                .and(Conditions.gt("age", 18))
                .or(Conditions.eq("role", "admin"))
                .groupBy("role")
                .orderBy("name")
                .limit(5);

        String expectedSql = "SELECT id, name, email FROM users WHERE ((status = ? AND age > ?) OR role = ?) GROUP BY role ORDER BY name LIMIT 5;";
        assertEquals(expectedSql, select.toString());

        Object[] expectedParams = {"ACTIVE", 18, "admin"};
        assertArrayEquals(expectedParams, select.getParams());

    }

    @Test
    public void testInsertDSL() {
        InsertDSL insert = new InsertDSL()
                .into("users")
                .columns("name", "email", "age")
                .values("Alice", "alice@mail.com", 30);

        String expectedSql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?);";
        assertEquals(expectedSql, insert.build().toString());

        Object[] expectedParams = {"Alice", "alice@mail.com", 30};
        assertArrayEquals(expectedParams, insert.getParams());
    }

    @Test
    public void testUpdateDSL() {
        UpdateDSL update = new UpdateDSL()
                .table("users")
                .set(new String[]{"name","age"}, "Bob", 25)
                .where(Conditions.eq("id", 1));

        String expectedSql = "UPDATE users SET name = ?, age = ? WHERE id = ?;";
        assertEquals(expectedSql, update.build().toString());

        Object[] expectedParams = {"Bob", 25, 1};
        assertArrayEquals(expectedParams, update.getParams());
    }

    @Test
    public void testDeleteDSL() {
        DeleteDSL delete = new DeleteDSL()
                .from("users")
                .where(Conditions.eq("status", "INACTIVE"))
                .and(Conditions.lt("age", 18));

        String expectedSql = "DELETE FROM users WHERE (status = ? AND age < ?);";
        assertEquals(expectedSql, delete.build().toString());

        Object[] expectedParams = {"INACTIVE", 18};
        assertArrayEquals(expectedParams, delete.getParams());
    }

    @Test
    public void testCreateDSL() {
        CreateDSL create = new CreateDSL()
                .table("users")
                .column("id", "INT AUTO_INCREMENT")
                .column("name", "VARCHAR(255)")
                .column("email", "VARCHAR(255)")
                .primaryKey("id")
                .unique("email");

        String expectedSql =
                "CREATE TABLE users (\n" +
                        "    id INT AUTO_INCREMENT,\n" +
                        "    name VARCHAR(255),\n" +
                        "    email VARCHAR(255),\n" +
                        "    PRIMARY KEY (id),\n" +
                        "    UNIQUE (email)\n" +
                        ");";
        assertEquals(expectedSql, create.toString());
    }

    @Test
    public void testAlterDSL() {
        AlterDSL alter = new AlterDSL("users")
                .addColumn("age", "INT")
                .dropColumn("old_column")
                .renameColumn("username", "user_name")
                .renameTable("customers");

        String expectedSql = "ALTER TABLE users ADD COLUMN age INT, DROP COLUMN old_column, RENAME COLUMN username TO user_name, RENAME TO customers;";
        assertEquals(expectedSql, alter.toString());
    }
}
