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
public class TransactionDSLMySQLTest {

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/transaction_test?useSSL=false&serverTimezone=UTC";
    private static final ZeraBuilder builder = new ZeraBuilder(SQLDialect.MYSQL);


    @Test
    @Order(1)
    void createTable() throws SQLException {
        CreateDSL create = builder.makeTable("users")
                .column("id", builder.type.integer())
                .column("email", builder.type.string(100))
                .column("age", builder.type.integer())
                .primaryKey("id")
                .unique("email");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "root", "");
             ZeraExecutor exec = new ZeraExecutor(conn)) {
            exec.update(create);
        }
    }


    @Test
    @Order(2)
    void transactionCommit() throws SQLException {
        InsertDSL insert = builder.insertTo("users")
                .columns("id", "email", "age");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "root", "");
             ZeraExecutor exec = new ZeraExecutor(conn)) {

            exec.transaction(e -> {
                for (int i = 10001; i <= 10010; i++) {
                    insert.values(i, "commit_user" + i + "@test.com", 20 + (i % 50));
                    e.update(insert);
                }
            });

            // Verify committed rows
            SelectDSL select = builder.list("*").from("users");
            List<Integer> ids = exec.query(select, rs -> rs.getInt("id"));

            // We expect at least the 10 inserted in the transaction
            Assertions.assertTrue(ids.contains(10001));
            Assertions.assertTrue(ids.contains(10010));
        }
    }

    @Test
    @Order(3)
    void transactionRollback() throws SQLException {
        InsertDSL insert = builder.insertTo("users")
                .columns("id", "email", "age");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "root", "");
             ZeraExecutor exec = new ZeraExecutor(conn)) {

            try {
                exec.transaction(e -> {
                    for (int i = 20001; i <= 20010; i++) {
                        insert.values(i, "rollback_user" + i + "@test.com", 25 + (i % 50));
                        e.update(insert);
                    }
                    // Force a runtime exception to trigger rollback
                    throw new RuntimeException("Force rollback");
                });
            } catch (RuntimeException ignored) {}

            // Verify that rows were not inserted
            SelectDSL select = builder.list("*").from("users");
            List<Integer> ids = exec.query(select, rs -> rs.getInt("id"));

            for (int i = 20001; i <= 20010; i++) {
                Assertions.assertFalse(ids.contains(i), "Row " + i + " should have been rolled back");
            }
        }
    }

    @Test
    @Order(4)
    void transactionRollbackOnConstraintViolation() throws SQLException {
        InsertDSL insert = builder.insertTo("users")
                .columns("id", "email", "age");

        try (ZeraConnection conn = new ZeraConnection(JDBC_URL, "root", "");
             ZeraExecutor exec = new ZeraExecutor(conn)) {

            try {
                exec.transaction(e -> {
                    insert.values(100, "dup@test.com", 20);
                    e.update(insert);

                    // duplicate PRIMARY KEY → should fail
                    insert.values(100, "dup2@test.com", 25);
                    e.update(insert);
                });
            } catch (Exception ignored) {}

            SelectDSL select = builder.list("*").from("users");
            List<Integer> ids = exec.query(select, rs -> rs.getInt("id"));

            Assertions.assertFalse(ids.contains(100),
                    "Duplicate insert should rollback entire transaction");
        }
    }

}
