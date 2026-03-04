package com.chemicaldev.zerabuilder.query.interfaces;

/**
 * Interface for building SQL INSERT queries.
 */
public interface InsertBuilder extends ParameterizedBuilder {

    /** Specifies the table to insert into. */
    InsertBuilder into(String table);

    /** Specifies the column names for the insert. */
    InsertBuilder columns(String... columns);

    /**
     * Binds a single row of values to insert.
     * The number of values must match the number of columns specified in {@link #columns}.
     *
     * @param values the row values to insert
     * @return this builder
     */
    InsertBuilder values(Object... values);
}
