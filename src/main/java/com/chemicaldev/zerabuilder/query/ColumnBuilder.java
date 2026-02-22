package com.chemicaldev.zerabuilder.query;


import com.chemicaldev.zerabuilder.dsl.datatype.DataType;
import com.chemicaldev.zerabuilder.dsl.table.ColumnDefinition;

public class ColumnBuilder {

    private final ColumnDefinition column;
    private final AbstractCreateBuilder parent;

    public ColumnBuilder(String name, AbstractCreateBuilder parent) {
        this.column = new ColumnDefinition(name);
        this.parent = parent;
    }

    public ColumnBuilder type(DataType type) {
        column.type(type);
        return this;
    }

    public ColumnBuilder notNull() {
        column.notNull();
        return this;
    }

    public ColumnBuilder primaryKey() {
        column.primaryKey();
        return this;
    }

    public ColumnBuilder unique() {
        column.unique();
        return this;
    }

    public ColumnBuilder autoIncrement() {
        column.autoIncrement();
        return this;
    }

    public ColumnBuilder defaultValue(String value) {
        column.defaultValue(value);
        return this;
    }

    public AbstractCreateBuilder done() {
        parent.addColumn(column);
        return parent;
    }
}