package com.chemicaldev.zerabuilder.query;


import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;
import com.chemicaldev.zerabuilder.dsl.table.ColumnDefinition;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.query.interfaces.CreateBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCreateBuilder implements CreateBuilder {

    protected final SQLDialect dialect;
    protected String table;
    protected final List<ColumnDefinition> columns = new ArrayList<>();
    protected boolean hasTimeStamp = false;

    protected AbstractCreateBuilder(SQLDialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public CreateBuilder table(String name) {
        this.table = name;
        this.column("uuid").type(Datatypes.string(36)).primaryKey().notNull().unique().done();
        return this;
    }

    @Override
    public ColumnBuilder column(String name) {
        return new ColumnBuilder(name, this);
    }

    protected void addColumn(ColumnDefinition column) {
        columns.add(column);
    }

    @Override
    public CreateBuilder addTimestamp(){
        if(hasTimeStamp) return this;
        hasTimeStamp = true;
        this.column("created_at").type(Datatypes.datetime()).defaultValue("CURRENT_TIMESTAMP").done();
        this.column("updated_at").type(Datatypes.datetime()).defaultValue("CURRENT_TIMESTAMP").onUpdate("CURRENT_TIMESTAMP").done();
        return this;
    }

    protected void validate() {
        if (table == null || columns.isEmpty()) {
            throw new IllegalStateException("Table name and at least one column required");
        }
    }

    protected abstract String renderColumn(ColumnDefinition column);

    @Override
    public String toString() {
        validate();

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(table).append(" (\n");


        ArrayList<ColumnDefinition> foreignKeys = new ArrayList<>();
        // Column Definitions
        for (int i = 0; i < columns.size(); i++) {
            if(columns.get(i).isForeignKey()) foreignKeys.add(columns.get(i));
            sb.append("  ").append(renderColumn(columns.get(i)));
            if (i < columns.size() - 1 || !foreignKeys.isEmpty()) sb.append(",");
            sb.append("\n");
        }

        //Foreign Keys
        for (int i = 0; i < foreignKeys.size(); i++) {
            ColumnDefinition foreignKeyColumn = foreignKeys.get(i);

            String reference = foreignKeyColumn.getReference();

            sb.append("  ").append(String.format("FOREIGN KEY (%s) REFERENCES %s(uuid)", foreignKeyColumn.getName(), reference));
            if (i < foreignKeys.size() - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append(");");

        /// Add Triggers for SQLite ON UPDATE ///

        if(dialect == SQLDialect.SQLITE && hasTimeStamp) sb.append(String.format("\nCREATE TRIGGER %s_set_updated_at\n" +
                "AFTER UPDATE ON %s\n" +
                "FOR EACH ROW\n" +
                "WHEN NEW.updated_at = OLD.updated_at\n" +
                "BEGIN\n" +
                "    UPDATE %s\n" +
                "    SET updated_at = CURRENT_TIMESTAMP\n" +
                "    WHERE uuid = OLD.uuid;\n" +
                "END;", this.table, this.table, this.table));

        return sb.toString();
    }
}