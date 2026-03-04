package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.interfaces.AlterBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLAlterBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteAlterBuilder;

/**
 * DSL wrapper for building SQL ALTER TABLE statements.
 *
 * <p>Obtain via {@link ZeraBuilder#alterTable(String)} and chain methods fluently:
 *
 * <pre>{@code
 * zb.alterTable("users")
 *   .addColumn("phone", "VARCHAR(20)")
 *   .toString();
 * // → ALTER TABLE users ADD COLUMN phone VARCHAR(20);
 * }</pre>
 *
 * <p><b>Note:</b> SQLite has limited ALTER TABLE support. DROP COLUMN and
 * MODIFY COLUMN are not supported in SQLite and will log a warning.
 */
public class AlterDSL {

    private final ZeraBuilder _instance;
    private final AlterBuilder builder;

    public AlterDSL(ZeraBuilder _instance) {
        this._instance = _instance;
        this.builder = switch (_instance.getDialect()) {
            case MYSQL -> new MySQLAlterBuilder();
            case SQLITE -> new SQLiteAlterBuilder();
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL support not yet implemented");
        };
    }

    /** Specifies the table to alter. */
    public AlterDSL table(String table) {
        builder.table(table);
        return this;
    }

    /** Adds a new column with the given name and SQL type string. */
    public AlterDSL addColumn(String name, String type) {
        builder.addColumn(name, type);
        return this;
    }

    /**
     * Drops a column.
     *
     * <p><b>Note:</b> Not supported in SQLite — a warning will be logged instead.
     */
    public AlterDSL dropColumn(String name) {
        builder.dropColumn(name);
        return this;
    }

    /**
     * Modifies the type of an existing column.
     *
     * <p><b>Note:</b> Not supported in SQLite — a warning will be logged instead.
     */
    public AlterDSL modifyColumn(String name, String type) {
        builder.modifyColumn(name, type);
        return this;
    }

    /** Renames a column. */
    public AlterDSL renameColumn(String oldName, String newName) {
        builder.renameColumn(oldName, newName);
        return this;
    }

    /** Renames the table. */
    public AlterDSL renameTable(String newName) {
        builder.renameTable(newName);
        return this;
    }

    public AlterBuilder build() {
        return builder;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
