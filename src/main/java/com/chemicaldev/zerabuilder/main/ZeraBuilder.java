package com.chemicaldev.zerabuilder.main;

import com.chemicaldev.zerabuilder.dsl.*;
import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;

/**
 * Entry point for ZeraBuilder — a fluent SQL query builder.
 *
 * <p>Create a {@code ZeraBuilder} with a {@link SQLDialect} and chain DSL methods
 * to build type-safe, parameterized SQL queries.
 *
 * <pre>{@code
 * ZeraBuilder zb = new ZeraBuilder(SQLDialect.MYSQL);
 *
 * // SELECT
 * String sql = zb.list("id", "name").from("users")
 *                .where(Conditions.eq("active", 1))
 *                .limit(10)
 *                .toString();
 *
 * // INSERT
 * String insert = zb.insertTo("users")
 *                   .columns("name", "email")
 *                   .values("Alice", "alice@example.com")
 *                   .toString();
 * }</pre>
 */
public class ZeraBuilder {

    private SQLDialect dialect = SQLDialect.MYSQL;

    /**
     * Constructs a ZeraBuilder with the given SQL dialect.
     *
     * @param dialect the SQL dialect to use for query generation
     */
    public ZeraBuilder(SQLDialect dialect) {
        this.setDialect(dialect);
    }

    /**
     * Changes the SQL dialect. Also resets the {@link Datatypes} helper.
     *
     * @param dialect the new SQL dialect
     */
    public void setDialect(SQLDialect dialect) {
        this.dialect = dialect;
        this.type = new Datatypes();
    }

    /**
     * Returns the current SQL dialect.
     *
     * @return the active {@link SQLDialect}
     */
    public SQLDialect getDialect() {
        return dialect;
    }

    /**
     * Starts a {@code CREATE TABLE} DSL chain.
     *
     * @return a new {@link CreateDSL} instance
     */
    public CreateDSL create() {
        return new CreateDSL(this);
    }

    /**
     * Starts a {@code CREATE TABLE} DSL chain with the table name pre-set.
     *
     * @param tableName the name of the table to create
     * @return a {@link CreateDSL} with the table name already set
     */
    public CreateDSL createTable(String tableName) {
        return new CreateDSL(this).table(tableName);
    }

    /**
     * Starts a {@code SELECT} DSL chain with the given columns.
     *
     * @param cols the columns to select; pass no args or {@code "*"} for all columns
     * @return a new {@link SelectDSL} instance
     */
    public SelectDSL list(String... cols) {
        return new SelectDSL(this).list(cols);
    }

    /**
     * Starts an {@code INSERT INTO} DSL chain for the given table.
     *
     * @param tableName the table to insert into
     * @return a new {@link InsertDSL} instance
     */
    public InsertDSL insertTo(String tableName) {
        return new InsertDSL(this).into(tableName);
    }

    /**
     * Starts an {@code UPDATE} DSL chain for the given table.
     *
     * @param tableName the table to update
     * @return a new {@link UpdateDSL} instance
     */
    public UpdateDSL updateTable(String tableName) {
        return new UpdateDSL(this).table(tableName);
    }

    /**
     * Starts a {@code DELETE FROM} DSL chain for the given table.
     *
     * @param tableName the table to delete from
     * @return a new {@link DeleteDSL} instance
     */
    public DeleteDSL deleteFrom(String tableName) {
        return new DeleteDSL(this).from(tableName);
    }

    /**
     * Starts an {@code ALTER TABLE} DSL chain for the given table.
     *
     * @param tableName the table to alter
     * @return a new {@link AlterDSL} instance
     */
    public AlterDSL alterTable(String tableName) {
        return new AlterDSL(this).table(tableName);
    }

    /**
     * Provides access to SQL data type factories (e.g. {@code type.string()}, {@code type.integer()}).
     */
    public Datatypes type = new Datatypes();
}
