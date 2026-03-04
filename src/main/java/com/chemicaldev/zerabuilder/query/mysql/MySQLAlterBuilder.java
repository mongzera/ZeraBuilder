package com.chemicaldev.zerabuilder.query.mysql;

import com.chemicaldev.zerabuilder.query.interfaces.AlterBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of ALTER TABLE.
 *
 * <p>Supports: ADD COLUMN, DROP COLUMN, MODIFY COLUMN, RENAME COLUMN, RENAME TABLE.
 * Multiple actions can be chained and will be emitted as a single ALTER TABLE statement.
 */
public class MySQLAlterBuilder implements AlterBuilder {

    private String table;
    private final List<String> actions = new ArrayList<>();

    @Override
    public MySQLAlterBuilder table(String table) {
        this.table = table;
        return this;
    }

    /** Adds a new column. e.g. {@code addColumn("email", "VARCHAR(255)")} */
    @Override
    public MySQLAlterBuilder addColumn(String name, String type) {
        actions.add("ADD COLUMN " + name + " " + type);
        return this;
    }

    /** Drops an existing column. */
    @Override
    public MySQLAlterBuilder dropColumn(String name) {
        actions.add("DROP COLUMN " + name);
        return this;
    }

    /**
     * Modifies the type of an existing column.
     * Uses MySQL's {@code MODIFY COLUMN} syntax.
     */
    @Override
    public MySQLAlterBuilder modifyColumn(String name, String type) {
        actions.add("MODIFY COLUMN " + name + " " + type);
        return this;
    }

    /** Renames a column. Requires MySQL 8.0+. */
    @Override
    public MySQLAlterBuilder renameColumn(String oldName, String newName) {
        actions.add("RENAME COLUMN " + oldName + " TO " + newName);
        return this;
    }

    /** Renames the table. */
    @Override
    public MySQLAlterBuilder renameTable(String newName) {
        actions.add("RENAME TO " + newName);
        return this;
    }

    @Override
    public String toString() {
        if (table == null || actions.isEmpty()) {
            throw new IllegalStateException("Table and at least one action must be specified");
        }
        return String.format("ALTER TABLE %s %s;", table, String.join(", ", actions));
    }
}
