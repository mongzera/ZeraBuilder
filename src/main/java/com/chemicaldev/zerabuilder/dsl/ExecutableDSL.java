package com.chemicaldev.zerabuilder.dsl;

/**
 * Marker interface for DSL objects that can be directly executed via {@code ZeraExecutor}.
 *
 * <p>Provides the SQL string, the bound parameters, and the execution type
 * ({@link ExecutionType#QUERY} for SELECT, {@link ExecutionType#UPDATE} for DML/DDL).
 */
public interface ExecutableDSL {

    /** Returns the SQL string with {@code ?} placeholders. */
    String toString();

    /** Returns the bound parameter values in order. */
    Object[] getParameters();

    /** Returns whether this DSL produces a result set (QUERY) or row count (UPDATE). */
    ExecutionType type();
}
