package com.chemicaldev.zerabuilder.query;

import com.chemicaldev.zerabuilder.query.interfaces.InsertBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base for INSERT builders. Shared logic for all dialects.
 *
 * <p>MySQL and SQLite INSERT syntax is identical, so both implementations
 * simply extend this class with no overrides needed.
 */
public abstract class AbstractInsertBuilder implements InsertBuilder {

    protected String table;
    protected String[] columns;
    protected final List<Object> parameters = new ArrayList<>();

    @Override
    public InsertBuilder into(String table) {
        this.table = table;
        return this;
    }

    @Override
    public InsertBuilder columns(String... columns) {
        this.columns = columns;
        return this;
    }

    /**
     * Binds the row values to insert. Must be called after {@link #columns}.
     * The number of values must match the number of columns.
     *
     * @param values the values for a single row
     * @return this builder
     * @throws IllegalArgumentException if value count does not match column count
     */
    @Override
    public InsertBuilder values(Object... values) {
        if (columns == null) {
            throw new IllegalStateException("Call columns() before values()");
        }
        if (values.length != columns.length) {
            throw new IllegalArgumentException(
                    "Value count (" + values.length + ") does not match column count (" + columns.length + ")");
        }
        Collections.addAll(parameters, values);
        return this;
    }

    @Override
    public Object[] getParameters() {
        return parameters.toArray();
    }

    @Override
    public String toString() {
        if (table == null || columns == null || columns.length == 0) {
            throw new IllegalStateException("Table and columns must be specified");
        }

        String placeholders = String.join(", ",
                Collections.nCopies(columns.length, "?"));

        return String.format(
                "INSERT INTO %s (%s) VALUES (%s);",
                table,
                String.join(", ", columns),
                placeholders
        );
    }
}
