import com.chemicaldev.zerabuilder.dsl.*;
import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DSLSqliteTest2 {

    ZeraBuilder builder = new ZeraBuilder(SQLDialect.SQLITE);

    @Test
    void testSQLiteCreateTable() {
        String sql = new CreateDSL(builder)
                .table("users")
                .column("id", builder.type.integer())
                .column("email", builder.type.string())
                .primaryKey("id")
                .unique("email")
                .toString();

        assertEquals(
                "CREATE TABLE users (id INTEGER, email TEXT, PRIMARY KEY (id), UNIQUE (email));",
                sql
        );
    }
}
