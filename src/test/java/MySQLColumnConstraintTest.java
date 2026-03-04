import com.chemicaldev.zerabuilder.dsl.CreateDSL;
import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySQLColumnConstraintTest {

    @Test
    void mysql_column_should_render_all_constraints() {

        CreateDSL table = new ZeraBuilder(SQLDialect.MYSQL)
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
                .defaultValue("'unknown@mail.com'")
                .done();

        String sql = table.build();

        System.out.println(sql);

        assertTrue(sql.contains("CREATE TABLE users"));
        assertTrue(sql.contains("id INT"));
        assertTrue(sql.contains("PRIMARY KEY"));
        assertTrue(sql.contains("AUTO_INCREMENT"));

        assertTrue(sql.contains("email VARCHAR(255)"));
        assertTrue(sql.contains("NOT NULL"));
        assertTrue(sql.contains("UNIQUE"));
        assertTrue(sql.contains("DEFAULT 'unknown@mail.com'"));
    }
}