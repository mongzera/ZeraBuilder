import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

class SQLiteInsertBenchmarkTest {

    @Test
    void benchmark_sqlite_insert_builder() {

        int iterations = 200_000;

        long start = System.nanoTime();

        for (int i = 0; i < iterations; i++) {
            new ZeraBuilder(SQLDialect.SQLITE)
                    .insertTo("users")
                    .columns("email", "age")
                    .values("user" + i + "@mail.com", i)
                    .toString();
        }

        long end = System.nanoTime();

        long durationMs = (end - start) / 1_000_000;

        System.out.println("Iterations: " + iterations);
        System.out.println("Time (ms): " + durationMs);
    }
}