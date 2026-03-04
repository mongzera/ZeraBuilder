package com.chemicaldev.zerabuilder.dsl;

/**
 * Represents a single SQL condition that can appear in a WHERE or HAVING clause.
 *
 * <p>Use {@link Conditions} to create instances, or implement this interface
 * directly for custom conditions.
 */
public interface Condition {

    /**
     * Returns the SQL fragment for this condition, with {@code ?} placeholders
     * for any bound parameters.
     *
     * @return the SQL string, e.g. {@code "age >= ?"} or {@code "(a = ? AND b = ?)"}
     */
    String toSql();

    /**
     * Returns the ordered parameter values that correspond to the {@code ?}
     * placeholders in {@link #toSql()}.
     *
     * @return an array of parameter values (never {@code null}; may be empty)
     */
    Object[] getParameters();
}
