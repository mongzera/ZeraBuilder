package com.chemicaldev.zerabuilder.dsl;

/**
 * Factory class for building SQL {@link Condition} instances.
 *
 * <p>Use these static methods to compose WHERE clauses in a type-safe way:
 *
 * <pre>{@code
 * Conditions.eq("status", "active")
 * Conditions.and(Conditions.gte("age", 18), Conditions.like("name", "A%"))
 * Conditions.isNull("deleted_at")
 * Conditions.in("role", "admin", "moderator")
 * }</pre>
 */
public class Conditions {

    // -----------------------------------------------------------------------
    // Equality / Comparison
    // -----------------------------------------------------------------------

    /**
     * Creates a {@code column = ?} condition.
     *
     * @param col the column name
     * @param val the value to compare against
     * @return a {@link Condition} for equality
     */
    public static Condition eq(String col, Object val) {
        return new SimpleCondition(col + " = ?", val);
    }

    /**
     * Creates a {@code column != ?} condition.
     *
     * @param col the column name
     * @param val the value to compare against
     * @return a {@link Condition} for inequality
     */
    public static Condition neq(String col, Object val) {
        return new SimpleCondition(col + " != ?", val);
    }

    /**
     * Creates a {@code column > ?} condition.
     *
     * @param col the column name
     * @param val the value to compare against
     * @return a {@link Condition} for greater-than
     */
    public static Condition gt(String col, Object val) {
        return new SimpleCondition(col + " > ?", val);
    }

    /**
     * Creates a {@code column >= ?} condition.
     *
     * @param col the column name
     * @param val the value to compare against
     * @return a {@link Condition} for greater-than-or-equal
     */
    public static Condition gte(String col, Object val) {
        return new SimpleCondition(col + " >= ?", val);
    }

    /**
     * Creates a {@code column < ?} condition.
     *
     * @param col the column name
     * @param val the value to compare against
     * @return a {@link Condition} for less-than
     */
    public static Condition lt(String col, Object val) {
        return new SimpleCondition(col + " < ?", val);
    }

    /**
     * Creates a {@code column <= ?} condition.
     *
     * @param col the column name
     * @param val the value to compare against
     * @return a {@link Condition} for less-than-or-equal
     */
    public static Condition lte(String col, Object val) {
        return new SimpleCondition(col + " <= ?", val);
    }

    // -----------------------------------------------------------------------
    // String matching
    // -----------------------------------------------------------------------

    /**
     * Creates a {@code column LIKE ?} condition.
     * Use {@code %} wildcards in the value, e.g. {@code "A%"} or {@code "%smith%"}.
     *
     * @param col     the column name
     * @param pattern the LIKE pattern
     * @return a {@link Condition} for LIKE matching
     */
    public static Condition like(String col, String pattern) {
        return new SimpleCondition(col + " LIKE ?", pattern);
    }

    /**
     * Creates a {@code column NOT LIKE ?} condition.
     *
     * @param col     the column name
     * @param pattern the LIKE pattern to exclude
     * @return a {@link Condition} for NOT LIKE matching
     */
    public static Condition notLike(String col, String pattern) {
        return new SimpleCondition(col + " NOT LIKE ?", pattern);
    }

    // -----------------------------------------------------------------------
    // NULL checks
    // -----------------------------------------------------------------------

    /**
     * Creates a {@code column IS NULL} condition (no parameter).
     *
     * @param col the column name
     * @return a {@link Condition} for IS NULL
     */
    public static Condition isNull(String col) {
        return new SimpleCondition(col + " IS NULL");
    }

    /**
     * Creates a {@code column IS NOT NULL} condition (no parameter).
     *
     * @param col the column name
     * @return a {@link Condition} for IS NOT NULL
     */
    public static Condition isNotNull(String col) {
        return new SimpleCondition(col + " IS NOT NULL");
    }

    // -----------------------------------------------------------------------
    // IN / NOT IN
    // -----------------------------------------------------------------------

    /**
     * Creates a {@code column IN (?, ?, ...)} condition.
     *
     * @param col    the column name
     * @param values the values to match against
     * @return a {@link Condition} for IN
     */
    public static Condition in(String col, Object... values) {
        String placeholders = String.join(", ",
                java.util.Collections.nCopies(values.length, "?"));
        return new SimpleCondition(col + " IN (" + placeholders + ")", values);
    }

    /**
     * Creates a {@code column NOT IN (?, ?, ...)} condition.
     *
     * @param col    the column name
     * @param values the values to exclude
     * @return a {@link Condition} for NOT IN
     */
    public static Condition notIn(String col, Object... values) {
        String placeholders = String.join(", ",
                java.util.Collections.nCopies(values.length, "?"));
        return new SimpleCondition(col + " NOT IN (" + placeholders + ")", values);
    }

    // -----------------------------------------------------------------------
    // BETWEEN
    // -----------------------------------------------------------------------

    /**
     * Creates a {@code column BETWEEN ? AND ?} condition.
     *
     * @param col  the column name
     * @param from the lower bound (inclusive)
     * @param to   the upper bound (inclusive)
     * @return a {@link Condition} for BETWEEN
     */
    public static Condition between(String col, Object from, Object to) {
        return new SimpleCondition(col + " BETWEEN ? AND ?", from, to);
    }

    // -----------------------------------------------------------------------
    // Logical combinators
    // -----------------------------------------------------------------------

    /**
     * Combines multiple conditions with {@code AND}.
     *
     * @param conds the conditions to combine
     * @return a single {@link Condition} representing {@code (c1 AND c2 AND ...)}
     */
    public static Condition and(Condition... conds) {
        return new LogicalCondition("AND", conds);
    }

    /**
     * Combines multiple conditions with {@code OR}.
     *
     * @param conds the conditions to combine
     * @return a single {@link Condition} representing {@code (c1 OR c2 OR ...)}
     */
    public static Condition or(Condition... conds) {
        return new LogicalCondition("OR", conds);
    }

    /**
     * Negates a condition with {@code NOT}.
     *
     * @param cond the condition to negate
     * @return a {@link Condition} representing {@code NOT (cond)}
     */
    public static Condition not(Condition cond) {
        return new SimpleCondition("NOT (" + cond.toSql() + ")", cond.getParameters());
    }
}
