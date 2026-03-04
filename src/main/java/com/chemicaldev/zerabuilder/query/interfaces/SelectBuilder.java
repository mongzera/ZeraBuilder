package com.chemicaldev.zerabuilder.query.interfaces;

/**
 * Interface for building SQL SELECT queries.
 *
 * <p>All methods return {@code SelectBuilder} to support method chaining.
 */
public interface SelectBuilder extends ParameterizedBuilder {

    /** Specifies the columns to select. Pass no arguments to select {@code *}. */
    SelectBuilder select(String... columns);

    /** Specifies the table to select from. */
    SelectBuilder from(String table);

    /** Appends one or more JOIN expressions (e.g. {@code "INNER JOIN orders ON users.id = orders.user_id"}). */
    SelectBuilder join(String... joins);

    /** Sets the WHERE clause condition. */
    SelectBuilder where(String condition, Object... params);

    /** Appends an AND condition to the existing WHERE clause. */
    SelectBuilder and(String condition, Object... params);

    /** Appends an OR condition to the existing WHERE clause. */
    SelectBuilder or(String condition, Object... params);

    /** Sets the GROUP BY columns. */
    SelectBuilder groupBy(String... columns);

    /**
     * Sets a HAVING clause (for use after GROUP BY).
     *
     * @param condition the HAVING condition, e.g. {@code "COUNT(*) > ?"}
     * @param params    the bound parameter values
     */
    SelectBuilder having(String condition, Object... params);

    /** Sets the ORDER BY columns. */
    SelectBuilder orderBy(String... columns);

    /** Sets the maximum number of rows to return. */
    SelectBuilder limit(int limit);

    /** Sets the number of rows to skip before returning results. */
    SelectBuilder offset(int offset);
}
