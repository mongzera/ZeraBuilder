package com.chemicaldev.zerabuilder.dsl.table;

import com.chemicaldev.zerabuilder.dsl.datatype.DataType;

public class ColumnDefinition {

    private final String name;
    private DataType type;
    private boolean notNull;
    private boolean primaryKey;
    private boolean unique;
    private boolean autoIncrement;
    private Object defaultValue;
    private boolean isForeignKey;
    private String reference = "";
    private String onUpdateValue;

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

    public Object getDefaultValue() {
        if (defaultValue == null) return "NULL";

        if (defaultValue instanceof String) {
            String val = (String) defaultValue;
            // Check for common SQL keywords that shouldn't be quoted
            if (val.toUpperCase().matches("CURRENT_TIMESTAMP|CURRENT_DATE|CURRENT_TIME|NULL")) {
                return val.toUpperCase();
            }
            // Quote literals and escape single quotes
            return "'" + val.replace("'", "''") + "'";
        }

        if (defaultValue instanceof Boolean) {
            return (Boolean) defaultValue ? 1 : 0;
        }

        return defaultValue;
    }

    public String getOnUpdateValue() { return onUpdateValue; }
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

    public ColumnDefinition defaultValue(Object value) {
        this.defaultValue = value;
        return this;
    }

    public ColumnDefinition onUpdate(String value){
        this.onUpdateValue = value;
        return this;
    }

    public ColumnDefinition setReference(String reference){
        this.isForeignKey = true;
        this.reference = reference.trim();
        return this;
    }
}