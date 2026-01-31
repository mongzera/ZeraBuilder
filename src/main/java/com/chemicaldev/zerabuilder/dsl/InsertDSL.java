package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.query.mysql.MySQLInsertBuilder;

public class InsertDSL {

    private final MySQLInsertBuilder builder;

    public InsertDSL(){
        this.builder = new MySQLInsertBuilder();
    }

    public InsertDSL into(String table){
        builder.into(table);
        return this;
    }

    public InsertDSL columns(String... columns){
        builder.columns(columns);
        return this;
    }

    public InsertDSL values(Object... values){
        builder.values(values);
        return this;
    }

    public MySQLInsertBuilder build(){
        return builder;
    }

    @Override
    public String toString(){
        return builder.toString();
    }

    public Object[] getParams(){
        return builder.getParameters();
    }
}
