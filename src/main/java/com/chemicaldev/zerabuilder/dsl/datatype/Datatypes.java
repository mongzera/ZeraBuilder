package com.chemicaldev.zerabuilder.dsl.datatype;

/**
 * Factory for SQL data type instances used in {@code CREATE TABLE} column definitions.
 *
 * <p>Access via {@code ZeraBuilder#type}:
 * <pre>{@code
 * zb.createTable("users")
 *   .column("id").type(Datatypes.integer()).primaryKey().autoIncrement().done()
 *   .column("name").type(Datatypes.string(100)).notNull().done()
 *   .column("email").type(Datatypes.string()).unique().done()
 *   .column("created_at").type(Datatypes.datetime()).done()
 *   .build();
 * }</pre>
 */
public class Datatypes {

    /** A VARCHAR column with the specified length. */
    public static DataType string(int length) {
        return new StringType(length);
    }

    /** A VARCHAR(255) column. */
    public static DataType string() {
        return new StringType(255);
    }

    /** An INTEGER / INT column. */
    public static DataType integer() {
        return new IntegerType();
    }

    /** A DATE column. */
    public static DataType date() {
        return new DateType();
    }

    /** A DATETIME / TIMESTAMP column. */
    public static DataType datetime() {
        return new DateTimeType();
    }
}
