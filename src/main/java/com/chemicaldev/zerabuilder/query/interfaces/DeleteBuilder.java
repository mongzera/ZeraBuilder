package com.chemicaldev.zerabuilder.query.interfaces;

/**
 * Interface for building SQL DELETE queries.
 */
public interface DeleteBuilder extends ParameterizedBuilder {

    /** Specifies the table to delete from. */
    DeleteBuilder from(String table);

    /** Sets the WHERE condition. */
    DeleteBuilder where(String condition, Object... params);

    /** Appends an AND condition. */
    DeleteBuilder and(String condition, Object... params);

    /** Appends an OR condition. */
    DeleteBuilder or(String condition, Object... params);
}
