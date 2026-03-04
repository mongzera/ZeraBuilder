import com.chemicaldev.zerabuilder.dsl.CreateDSL;
import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteColumnConstraintTest {

    @Test
    void sqlite_column_should_render_all_constraints() {

        CreateDSL table = new ZeraBuilder(SQLDialect.SQLITE)
                .createTable("users");

        table.column("id")
                .type(Datatypes.integer())
                .primaryKey()
                .autoIncrement()
                .done();

        table.column("email")
                .type(Datatypes.string(255))
                .notNull()
                .unique()
                .defaultValue("'default@mail.com'")
                .done();

        String sql = table.build();

        System.out.println(sql);

        assertTrue(sql.contains("CREATE TABLE users"));
        assertTrue(sql.contains("INTEGER"));
        assertTrue(sql.contains("PRIMARY KEY"));
        assertTrue(sql.contains("AUTOINCREMENT"));

        assertTrue(sql.contains("email"));
        assertTrue(sql.contains("NOT NULL"));
        assertTrue(sql.contains("UNIQUE"));
        assertTrue(sql.contains("DEFAULT 'default@mail.com'"));
    }
}