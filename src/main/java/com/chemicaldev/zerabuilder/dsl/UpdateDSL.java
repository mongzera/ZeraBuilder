package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.interfaces.UpdateBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLUpdateBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteUpdateBuilder;

/**
 * DSL wrapper for building SQL UPDATE queries.
 *
 * <p>Obtain via {@link ZeraBuilder#updateTable(String)} and chain methods fluently:
 *
 * <pre>{@code
 * zb.updateTable("users")
 *   .set("name", "Alice")
 *   .set("email", "alice@example.com")
 *   .where(Conditions.eq("id", 1))
 *   .toString();
 * // → UPDATE users SET name = ?, email = ? WHERE id = ?;
 * }</pre>
 */
public class UpdateDSL implements ExecutableDSL {

    private final ZeraBuilder _instance;
    private final UpdateBuilder updateBuilder;

    public UpdateDSL(ZeraBuilder _instance) {
        this._instance = _instance;
        this.updateBuilder = switch (_instance.getDialect()) {
            case MYSQL -> new MySQLUpdateBuilder();
            case SQLITE -> new SQLiteUpdateBuilder();
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL support not yet implemented");
        };
    }

    /** Specifies the table to update. */
    public UpdateDSL table(String table) {
        updateBuilder.update(table);
        return this;
    }

    /**
     * Sets multiple columns at once, pairing each column name with its value.
     *
     * @param columns array of column names
     * @param values  corresponding values (must match length of columns)
     */
    public UpdateDSL set(String[] columns, Object... values) {
        for (int i = 0; i < columns.length; i++) {
            updateBuilder.set(columns[i], values[i]);
        }
        return this;
    }

    /**
     * Sets a single column to a value.
     *
     * @param column the column name
     * @param value  the new value
     */
    public UpdateDSL set(String column, Object value) {
        updateBuilder.set(column, value);
        return this;
    }

    /** Sets the WHERE condition. */
    public UpdateDSL where(Condition condition) {
        updateBuilder.where(condition.toSql(), condition.getParameters());
        return this;
    }

    /** Appends an AND condition to the WHERE clause. */
    public UpdateDSL and(Condition condition) {
        updateBuilder.and(condition.toSql(), condition.getParameters());
        return this;
    }

    /** Appends an OR condition to the WHERE clause. */
    public UpdateDSL or(Condition condition) {
        updateBuilder.or(condition.toSql(), condition.getParameters());
        return this;
    }

    public UpdateBuilder build() {
        return updateBuilder;
    }

    @Override
    public String toString() {
        return updateBuilder.toString();
    }

    @Override
    public Object[] getParameters() {
        return updateBuilder.getParameters();
    }

    @Override
    public ExecutionType type() {
        return ExecutionType.UPDATE;
    }
}
