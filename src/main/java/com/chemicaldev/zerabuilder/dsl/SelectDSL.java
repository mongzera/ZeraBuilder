package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.interfaces.SelectBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLSelectBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteSelectBuilder;

/**
 * DSL wrapper for building SQL SELECT queries.
 *
 * <p>Obtain via {@link ZeraBuilder#list(String...)} and chain methods fluently:
 *
 * <pre>{@code
 * zb.list("id", "name", "email")
 *   .from("users")
 *   .where(Conditions.eq("active", 1))
 *   .orderBy("name")
 *   .limit(20)
 *   .offset(40)
 *   .toString();
 * }</pre>
 */
public class SelectDSL implements ExecutableDSL {

    private final ZeraBuilder _instance;
    private final SelectBuilder selectBuilder;

    public SelectDSL(ZeraBuilder _instance) {
        this._instance = _instance;
        this.selectBuilder = switch (_instance.getDialect()) {
            case MYSQL -> new MySQLSelectBuilder();
            case SQLITE -> new SQLiteSelectBuilder();
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL support not yet implemented");
        };
    }

    /** Specifies the columns to select. */
    public SelectDSL list(String... columns) {
        selectBuilder.select(columns);
        return this;
    }

    /** Specifies the FROM table. */
    public SelectDSL from(String table) {
        selectBuilder.from(table);
        return this;
    }

    /** Appends a WHERE condition. */
    public SelectDSL where(Condition condition) {
        selectBuilder.where(condition.toSql(), condition.getParameters());
        return this;
    }

    /** Appends an AND condition to the WHERE clause. */
    public SelectDSL and(Condition condition) {
        selectBuilder.and(condition.toSql(), condition.getParameters());
        return this;
    }

    /** Appends an OR condition to the WHERE clause. */
    public SelectDSL or(Condition condition) {
        selectBuilder.or(condition.toSql(), condition.getParameters());
        return this;
    }

    /** Appends one or more JOIN expressions. */
    public SelectDSL join(String joinExpression) {
        selectBuilder.join(joinExpression);
        return this;
    }

    /** Sets GROUP BY columns. */
    public SelectDSL groupBy(String... columns) {
        selectBuilder.groupBy(columns);
        return this;
    }

    /**
     * Sets a HAVING clause. Use after {@link #groupBy}.
     *
     * @param condition the HAVING condition, e.g. {@code "COUNT(*) > 5"}
     */
    public SelectDSL having(String condition) {
        selectBuilder.having(condition);
        return this;
    }

    /**
     * Sets a HAVING clause with bound parameters.
     *
     * @param condition the HAVING condition with {@code ?} placeholders
     * @param params    the parameter values
     */
    public SelectDSL having(String condition, Object... params) {
        selectBuilder.having(condition, params);
        return this;
    }

    /** Sets ORDER BY columns. */
    public SelectDSL orderBy(String... columns) {
        selectBuilder.orderBy(columns);
        return this;
    }

    /** Sets the LIMIT clause. */
    public SelectDSL limit(int limit) {
        selectBuilder.limit(limit);
        return this;
    }

    /**
     * Sets the OFFSET clause (number of rows to skip).
     *
     * @param offset the number of rows to skip
     */
    public SelectDSL offset(int offset) {
        selectBuilder.offset(offset);
        return this;
    }

    @Override
    public String toString() {
        return selectBuilder.toString();
    }

    @Override
    public ExecutionType type() {
        return ExecutionType.QUERY;
    }

    @Override
    public Object[] getParameters() {
        return selectBuilder.getParameters();
    }
}
