package com.chemicaldev.zerabuilder.query;


import com.chemicaldev.zerabuilder.dsl.table.ColumnDefinition;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.query.interfaces.CreateBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCreateBuilder implements CreateBuilder {

    protected final SQLDialect dialect;
    protected String table;
    protected final List<ColumnDefinition> columns = new ArrayList<>();

    protected AbstractCreateBuilder(SQLDialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public CreateBuilder table(String name) {
        this.table = name;
        return this;
    }

    @Override
    public ColumnBuilder column(String name) {
        return new ColumnBuilder(name, this);
    }

    protected void addColumn(ColumnDefinition column) {
        columns.add(column);
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
        sb.append("CREATE TABLE ").append(table).append(" (\n");


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

            String[] reference = foreignKeyColumn.getReference().split("\\.");
            String refTableName = reference[0];
            String refColumnName = reference[1];

            sb.append("  ").append(String.format("FOREIGN KEY (%s) REFERENCES %s(%s)", foreignKeyColumn.getName(), refTableName, refColumnName));
            if (i < foreignKeys.size() - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append(");");

        return sb.toString();
    }
}