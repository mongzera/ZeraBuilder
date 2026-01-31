package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.query.mysql.MySQLCreateBuilder;

public class CreateDSL {

    private final MySQLCreateBuilder builder;

    public CreateDSL(){
        this.builder = new MySQLCreateBuilder();
    }

    public CreateDSL table(String tableName){
        builder.table(tableName);
        return this;
    }

    public CreateDSL column(String name, String type){
        builder.column(name, type);
        return this;
    }

    public CreateDSL primaryKey(String... columns){
        builder.primaryKey(columns);
        return this;
    }

    public CreateDSL unique(String... columns){
        builder.unique(columns);
        return this;
    }

    public MySQLCreateBuilder build(){
        return builder;
    }

    @Override
    public String toString(){
        return builder.toString();
    }
}
