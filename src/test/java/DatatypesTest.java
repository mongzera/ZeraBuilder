import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatatypesTest {

    private Datatypes dt(SQLDialect dialect) {
        ZeraBuilder builder = new ZeraBuilder(dialect);
        builder.dialect = dialect;
        return new Datatypes(builder);
    }

    // ---------- STRING ----------

    @Test
    void sqlite_string_is_text() {
        assertEquals("TEXT", dt(SQLDialect.SQLITE).string(100));
    }

    @Test
    void mysql_string_is_varchar() {
        assertEquals("VARCHAR(100)", dt(SQLDialect.MYSQL).string(100));
    }

    // ---------- INTEGER ----------

    @Test
    void sqlite_integer_is_integer() {
        assertEquals("INTEGER", dt(SQLDialect.SQLITE).integer());
    }

    @Test
    void mysql_integer_is_int() {
        assertEquals("INT", dt(SQLDialect.MYSQL).integer());
    }

    // ---------- DATE ----------

    @Test
    void sqlite_date_is_text() {
        assertEquals("TEXT", dt(SQLDialect.SQLITE).date());
    }

    @Test
    void mysql_date_is_date() {
        assertEquals("DATE", dt(SQLDialect.MYSQL).date());
    }

    // ---------- DATETIME ----------

    @Test
    void sqlite_datetime_is_text() {
        assertEquals("TEXT", dt(SQLDialect.SQLITE).datetime());
    }

    @Test
    void mysql_datetime_is_datetime() {
        assertEquals("DATETIME", dt(SQLDialect.MYSQL).datetime());
    }
}
