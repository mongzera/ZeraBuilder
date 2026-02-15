import com.chemicaldev.zerabuilder.dsl.CreateDSL;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.CreateBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLCreateBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteCreateBuilder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TimestampTest {

    @Test
    public void timestampSQLite() {
        String tableName = "users";

        // Build table
        SQLiteCreateBuilder builder = new SQLiteCreateBuilder()
                .table(tableName)
                .column("id INTEGER PRIMARY KEY AUTOINCREMENT")
                .column("name TEXT NOT NULL")
                // Add timestamp columns
                .column("created_at DATETIME DEFAULT CURRENT_TIMESTAMP")
                .column("updated_at DATETIME DEFAULT CURRENT_TIMESTAMP")
                // Add trigger for updated_at
                .addTrigger(
                        "CREATE TRIGGER " + tableName + "_updated_at AFTER UPDATE ON " + tableName + " " +
                                "FOR EACH ROW BEGIN " +
                                "UPDATE " + tableName + " SET updated_at = CURRENT_TIMESTAMP WHERE rowid = OLD.rowid; " +
                                "END;"
                );

        String sql = builder.toString();
        System.out.println(sql);

        // Assertions
        assertTrue(sql.contains("CREATE TABLE " + tableName));
        assertTrue(sql.contains("id INTEGER PRIMARY KEY AUTOINCREMENT"));
        assertTrue(sql.contains("name TEXT NOT NULL"));
        assertTrue(sql.contains("created_at DATETIME DEFAULT CURRENT_TIMESTAMP"));
        assertTrue(sql.contains("updated_at DATETIME DEFAULT CURRENT_TIMESTAMP"));
        assertTrue(sql.contains("CREATE TRIGGER " + tableName + "_updated_at"));
    }

    @Test
    public void timestampMySQL() {
        String tableName = "users";

        // Build table
        MySQLCreateBuilder builder = new MySQLCreateBuilder()
                .table(tableName)
                .column("id INT AUTO_INCREMENT")
                .column("name VARCHAR(255) NOT NULL")
                // Add timestamp columns
                .column("created_at", "TIMESTAMP", "DEFAULT CURRENT_TIMESTAMP")
                .column("updated_at", "TIMESTAMP", "DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP");

        String sql = builder.toString();
        System.out.println(sql);

        // Assertions
        assertTrue(sql.contains("CREATE TABLE " + tableName));
        assertTrue(sql.contains("id INT AUTO_INCREMENT"));
        assertTrue(sql.contains("name VARCHAR(255) NOT NULL"));
        assertTrue(sql.contains("created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"));
        assertTrue(sql.contains("updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"));
    }

    @Test
    public void testCreateDSLWithTimestamps_MySQL() {
        ZeraBuilder builder = new ZeraBuilder(SQLDialect.MYSQL);

        CreateDSL createUserTable = builder.makeTable("users")
                .column("id INT")
                .column("email VARCHAR(255)")
                .column("password VARCHAR(255)")
                .column("age INT")
                .primaryKey("id")
                .unique("email")
                .timestamps(); // add created_at and updated_at

        CreateBuilder sqlBuilder = createUserTable.build();
        String sql = sqlBuilder.toString();
        System.out.println("MySQL SQL:\n" + sql);

        // Assertions
        assertTrue(sql.contains("CREATE TABLE users"));
        assertTrue(sql.contains("id INT"));
        assertTrue(sql.contains("email VARCHAR(255)"));
        assertTrue(sql.contains("password VARCHAR(255)"));
        assertTrue(sql.contains("age INT"));
        assertTrue(sql.contains("PRIMARY KEY (id)"));
        assertTrue(sql.contains("UNIQUE (email)"));
        assertTrue(sql.contains("created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"));
        assertTrue(sql.contains("updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"));
    }

    @Test
    public void testCreateDSLWithTimestamps_SQLite() {
        ZeraBuilder builder = new ZeraBuilder(SQLDialect.SQLITE);

        CreateDSL createUserTable = builder.makeTable("users")
                .column("id INTEGER PRIMARY KEY AUTOINCREMENT")
                .column("email TEXT NOT NULL")
                .column("password TEXT NOT NULL")
                .column("age INTEGER")
                .unique("email")
                .timestamps(); // add created_at and updated_at

        CreateBuilder sqlBuilder = createUserTable.build();
        String sql = sqlBuilder.toString();
        System.out.println("SQLite SQL:\n" + sql);

        // Assertions
        assertTrue(sql.contains("CREATE TABLE users"));
        assertTrue(sql.contains("id INTEGER PRIMARY KEY AUTOINCREMENT"));
        assertTrue(sql.contains("email TEXT NOT NULL"));
        assertTrue(sql.contains("password TEXT NOT NULL"));
        assertTrue(sql.contains("age INTEGER"));
        assertTrue(sql.contains("UNIQUE (email)"));
        assertTrue(sql.contains("created_at DATETIME DEFAULT CURRENT_TIMESTAMP"));
        assertTrue(sql.contains("updated_at DATETIME DEFAULT CURRENT_TIMESTAMP"));
        assertTrue(sql.contains("CREATE TRIGGER users_updated_at")); // trigger for updated_at
    }
}
