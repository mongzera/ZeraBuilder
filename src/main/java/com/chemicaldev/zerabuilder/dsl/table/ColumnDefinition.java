package com.chemicaldev.zerabuilder.dsl.table;

import com.chemicaldev.zerabuilder.dsl.datatype.DataType;

public class ColumnDefinition {

    private final String name;
    private DataType type;
    private boolean notNull;
    private boolean primaryKey;
    private boolean unique;
    private boolean autoIncrement;
    private String defaultValue;
    private boolean isForeignKey;
    private String reference = "";

    public ColumnDefinition(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public DataType getType() { return type; }
    public boolean isNotNull() { return notNull; }
    public boolean isPrimaryKey() { return primaryKey; }
    public boolean isUnique() { return unique; }
    public boolean isAutoIncrement() { return autoIncrement; }
    public boolean isForeignKey() { return isForeignKey; }
    public String getDefaultValue() { return defaultValue; }
    public String getReference() { return reference; }


    public ColumnDefinition type(DataType type) {
        this.type = type;
        return this;
    }

    public ColumnDefinition notNull() {
        this.notNull = true;
        return this;
    }

    public ColumnDefinition primaryKey() {
        this.primaryKey = true;
        return this;
    }

    public ColumnDefinition unique() {
        this.unique = true;
        return this;
    }

    public ColumnDefinition autoIncrement() {
        this.autoIncrement = true;
        return this;
    }

    public ColumnDefinition defaultValue(String value) {
        this.defaultValue = value;
        return this;
    }

    public ColumnDefinition setReference(String reference){
        this.isForeignKey = true;
        this.reference = reference;
        return this;
    }
}