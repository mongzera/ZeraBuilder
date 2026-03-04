package com.chemicaldev.zerabuilder.query.interfaces;

/**
 * Interface for building SQL UPDATE queries.
 */
public interface UpdateBuilder extends ParameterizedBuilder {

    /** Specifies the table to update. */
    UpdateBuilder update(String table);

    /**
     * Sets a column to a new value. Can be called multiple times to set multiple columns.
     *
     * @param column the column name
     * @param value  the new value (will be bound as a prepared statement parameter)
     */
    UpdateBuilder set(String column, Object value);

    /** Sets the WHERE condition. */
    UpdateBuilder where(String condition, Object... params);

    /** Appends an AND condition. */
    UpdateBuilder and(String condition, Object... params);

    /** Appends an OR condition. */
    UpdateBuilder or(String condition, Object... params);
}
