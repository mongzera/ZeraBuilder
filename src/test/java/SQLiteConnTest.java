import com.chemicaldev.zerabuilder.db.ZeraConnection;
import com.chemicaldev.zerabuilder.db.ZeraExecutor;
import com.chemicaldev.zerabuilder.dsl.CreateDSL;
import com.chemicaldev.zerabuilder.dsl.InsertDSL;
import com.chemicaldev.zerabuilder.dsl.SelectDSL;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLiteConnTest {

    ZeraBuilder builder = new ZeraBuilder(SQLDialect.SQLITE);

    @Test
    void sqliteIntegrationTest() throws SQLException {

        try (ZeraConnection connection =
                     new ZeraConnection("jdbc:sqlite:test.db", "", "")) {

            ZeraExecutor exec = new ZeraExecutor(connection);

            /* ---------- CREATE ---------- */
            CreateDSL createUserTable = builder.makeTable("users")
                    .column("id INTEGER")
                    .column("email TEXT")
                    .column("password TEXT")
                    .column("age INTEGER")
                    .primaryKey("id")
                    .unique("email");

            exec.update(createUserTable);

            /* ---------- INSERT ---------- */
            InsertDSL insertUser = builder.insertTo("users")
                    .columns("id", "email", "password", "age");

            exec.update(insertUser.values(0, "ennea@gmail.com", "ennea", 21));
            exec.update(insertUser.values(1, "ethangamat@gmail.com", "ethanPho", 21));
            exec.update(insertUser.values(2, "whutttt@gmail.com", "xoxad", 21));

            /* ---------- SELECT ---------- */
            SelectDSL selectAll = builder.list("*").from("users");

            List<User> users = exec.query(selectAll, rs ->
                    new User(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("age")
                    )
            );

            /* ---------- ASSERT ---------- */
            assertEquals(3, users.size());

            users.forEach(u ->
                    System.out.println(u.email() + " " + u.password() + " " + u.age())
            );
        }
    }
}

record User(String email, String password, int age) {}
