import com.chemicaldev.zerabuilder.dsl.CreateDSL;
import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteCreateAdvancedTest {

    @Test
    void sqlite_create_with_unique_and_multiple_columns() {

        CreateDSL table = new ZeraBuilder(SQLDialect.SQLITE)
                .createTable("products");

        table.column("id")
                .type(Datatypes.integer())
                .primaryKey()
                .autoIncrement()
                .done();

        table.column("name")
                .type(Datatypes.string(100))
                .notNull()
                .unique()
                .done();

        table.column("price")
                .type(Datatypes.integer())
                .notNull()
                .done();


        String sql = table.build();

        System.out.println(sql);

        assertTrue(sql.startsWith("CREATE TABLE products"));
        assertTrue(sql.contains("PRIMARY KEY"));
        assertTrue(sql.contains("AUTOINCREMENT"));
        assertTrue(sql.contains("NOT NULL"));
        assertTrue(sql.contains("UNIQUE"));
    }
}