package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.interfaces.DeleteBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLDeleteBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteDeleteBuilder;

/**
 * DSL wrapper for building SQL DELETE queries.
 *
 * <p>Obtain via {@link ZeraBuilder#deleteFrom(String)} and chain methods fluently:
 *
 * <pre>{@code
 * zb.deleteFrom("users")
 *   .where(Conditions.eq("id", 42))
 *   .toString();
 * // → DELETE FROM users WHERE id = ?;
 * }</pre>
 */
public class DeleteDSL implements ExecutableDSL {

    private final ZeraBuilder _instance;
    private final DeleteBuilder deleteBuilder;

    public DeleteDSL(ZeraBuilder _instance) {
        this._instance = _instance;
        this.deleteBuilder = switch (_instance.getDialect()) {
            case MYSQL -> new MySQLDeleteBuilder();
            case SQLITE -> new SQLiteDeleteBuilder();
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL support not yet implemented");
        };
    }

    /** Specifies the table to delete from. */
    public DeleteDSL from(String table) {
        deleteBuilder.from(table);
        return this;
    }

    /** Sets the WHERE condition. */
    public DeleteDSL where(Condition condition) {
        deleteBuilder.where(condition.toSql(), condition.getParameters());
        return this;
    }

    /** Appends an AND condition to the WHERE clause. */
    public DeleteDSL and(Condition condition) {
        deleteBuilder.and(condition.toSql(), condition.getParameters());
        return this;
    }

    /** Appends an OR condition to the WHERE clause. */
    public DeleteDSL or(Condition condition) {
        deleteBuilder.or(condition.toSql(), condition.getParameters());
        return this;
    }

    public DeleteBuilder build() {
        return deleteBuilder;
    }

    @Override
    public String toString() {
        return deleteBuilder.toString();
    }

    @Override
    public Object[] getParameters() {
        return deleteBuilder.getParameters();
    }

    @Override
    public ExecutionType type() {
        return ExecutionType.UPDATE;
    }
}
