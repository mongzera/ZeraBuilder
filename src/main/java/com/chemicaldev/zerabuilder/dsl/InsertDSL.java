package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.interfaces.InsertBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLInsertBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteInsertBuilder;

/**
 * DSL wrapper for building SQL INSERT queries.
 *
 * <p>Obtain via {@link ZeraBuilder#insertTo(String)} and chain methods fluently:
 *
 * <pre>{@code
 * zb.insertTo("users")
 *   .columns("name", "email", "age")
 *   .values("Alice", "alice@example.com", 30)
 *   .toString();
 * // → INSERT INTO users (name, email, age) VALUES (?, ?, ?);
 *
 * Object[] params = zb.insertTo("users")
 *   .columns("name", "email")
 *   .values("Bob", "bob@example.com")
 *   .getParameters();
 * }</pre>
 */
public class InsertDSL implements ExecutableDSL {

    private final ZeraBuilder _instance;
    private final InsertBuilder insertBuilder;

    public InsertDSL(ZeraBuilder _instance) {
        this._instance = _instance;
        this.insertBuilder = switch (_instance.getDialect()) {
            case MYSQL -> new MySQLInsertBuilder();
            case SQLITE -> new SQLiteInsertBuilder();
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL support not yet implemented");
        };
    }

    /** Specifies the table to insert into. */
    public InsertDSL into(String table) {
        insertBuilder.into(table);
        return this;
    }

    /** Specifies the column names for the insert. */
    public InsertDSL columns(String... columns) {
        insertBuilder.columns(columns);
        return this;
    }

    /**
     * Binds the row values to insert.
     * Must be called after {@link #columns}. The number of values must
     * match the number of columns.
     *
     * @param values the values to insert
     * @return this DSL
     */
    public InsertDSL values(Object... values) {
        insertBuilder.values(values);
        return this;
    }

    @Override
    public String toString() {
        return insertBuilder.toString();
    }

    /** Returns the bound parameter values from {@link #values}. */
    @Override
    public Object[] getParameters() {
        return insertBuilder.getParameters();
    }

    @Override
    public ExecutionType type() {
        return ExecutionType.UPDATE;
    }
}
