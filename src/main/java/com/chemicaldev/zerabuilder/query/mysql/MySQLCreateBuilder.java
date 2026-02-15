package com.chemicaldev.zerabuilder.query.mysql;

import com.chemicaldev.zerabuilder.query.CreateBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class MySQLCreateBuilder implements CreateBuilder {

    private String table;
    private final List<String> columns = new ArrayList<>();
    private final List<String> primaryKeys = new ArrayList<>();
    private final List<String> uniqueKeys = new ArrayList<>();

    // Set table name
    public MySQLCreateBuilder table(String table){
        this.table = table;
        return this;
    }


    public MySQLCreateBuilder column(String name, String type, String constraint){
        return this.column(name + " " + type + " " + constraint);
    }

    // Add a column definition: "name TYPE"
    public MySQLCreateBuilder column(String name, String type){
        return this.column(name + " " + type);
    }



    @Override
    public MySQLCreateBuilder column(String definition){
        columns.add(definition);
        return this;
    }

    // Add primary key(s)
    public MySQLCreateBuilder primaryKey(String... cols){
        for(String c : cols){
            primaryKeys.add(c);
        }
        return this;
    }

    // Add unique key(s)
    public MySQLCreateBuilder unique(String... cols){
        for(String c : cols){
            uniqueKeys.add(c);
        }
        return this;
    }

    @Override
    public String toString(){
        if(table == null || columns.isEmpty()){
            throw new IllegalStateException("Table name and at least one column must be specified");
        }

        StringJoiner sj = new StringJoiner(",\n    ", "(\n    ", "\n)");

        // Add columns
        for(String c : columns){
            sj.add(c);
        }

        // Add primary key
        if(!primaryKeys.isEmpty()){
            sj.add("PRIMARY KEY (" + String.join(", ", primaryKeys) + ")");
        }

        // Add unique keys
        if(!uniqueKeys.isEmpty()){
            sj.add("UNIQUE (" + String.join(", ", uniqueKeys) + ")");
        }

        return String.format("CREATE TABLE %s %s;", table, sj.toString());
    }

    @Override
    public MySQLCreateBuilder timestamps() {
        // created_at
        this.column("created_at", "TIMESTAMP", "DEFAULT CURRENT_TIMESTAMP");
        // updated_at with auto-update
        this.column("updated_at", "TIMESTAMP", "DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP");
        return this;
    }
}
