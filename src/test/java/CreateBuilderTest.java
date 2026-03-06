
import com.chemicaldev.zerabuilder.dsl.CreateDSL;
import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateBuilderTest {

    @Test
    void mysql_create_table_generates_valid_sql() {

//        String sql = new ZeraBuilder(SQLDialect.MYSQL)
////                .createTable("users")
////                .column("id")
////                .type(Datatypes.integer())
////                .primaryKey()
////                .autoIncrement()
////                .done()
////                .column("email")
////                .type(Datatypes.string(255))
////                .notNull()
////                .unique()
////                .done()
////                .build();

        CreateDSL users = new ZeraBuilder(SQLDialect.SQLITE).createTable("users");
        users.column("email").type(Datatypes.string(255)).notNull().unique().done();
        users.timestamps();
        String usersSql = users.build();

        System.out.println(usersSql);

        CreateDSL posts = new ZeraBuilder(SQLDialect.SQLITE).createTable("posts");
        posts.column("caption").type(Datatypes.string(255)).notNull().unique().done();
        posts.column("user_id").type(Datatypes.integer()).notNull().setReference("users").done();
        posts.timestamps();
        String postsSql = posts.build();

        System.out.println(postsSql);

        CreateDSL comments = new ZeraBuilder(SQLDialect.MYSQL).createTable("comments");
        comments.column("comment").type(Datatypes.string(255)).notNull().unique().done();
        comments.column("from_user").type(Datatypes.integer()).notNull().setReference("users").done();
        comments.column("post_id").type(Datatypes.integer()).notNull().setReference("posts").done();
        comments.timestamps();
        String commentsSql = comments.build();

        System.out.println(commentsSql);

//        // Assertions
//        assertTrue(sql.contains("CREATE TABLE users"));
//        assertTrue(sql.contains("id INT"));
//        assertTrue(sql.contains("PRIMARY KEY"));
//        assertTrue(sql.contains("AUTO_INCREMENT"));
//        assertTrue(sql.contains("email VARCHAR(255)"));
//        assertTrue(sql.contains("NOT NULL"));
//        assertTrue(sql.contains("UNIQUE"));
    }

    @Test
    void sqlite_create_table_generates_valid_sql() {

        CreateDSL dsl = new ZeraBuilder(SQLDialect.SQLITE).createTable("_collection_metadata");
        dsl.column("table_name").type(Datatypes.string()).notNull().done();

        System.out.println(dsl.build());

//        // SQLite handles primary key differently
//        assertTrue(sql.contains("CREATE TABLE users"));
//        assertTrue(sql.contains("INTEGER"));
//        assertTrue(sql.contains("PRIMARY KEY"));
//        assertTrue(sql.contains("AUTOINCREMENT"));
//        assertTrue(sql.contains("TEXT") || sql.contains("VARCHAR") == false);
//        assertTrue(sql.contains("email"));
    }

    @Test
    void table_without_columns_should_fail() {

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                () -> new ZeraBuilder(SQLDialect.MYSQL)
                        .createTable("invalid")
                        .build()
        );

        //assertEquals("Table name and at least one column required", exception.getMessage());
    }
}