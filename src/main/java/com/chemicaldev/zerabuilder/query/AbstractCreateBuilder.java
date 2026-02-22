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
    public String build() {
        validate();

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(table).append(" (\n");

        for (int i = 0; i < columns.size(); i++) {
            sb.append("  ").append(renderColumn(columns.get(i)));
            if (i < columns.size() - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append(");");

        return sb.toString();
    }
}