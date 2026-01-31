package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.query.mysql.InsertBuilder;

public class InsertDSL {

    private final InsertBuilder builder;

    public InsertDSL(){
        this.builder = new InsertBuilder();
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

    public InsertBuilder build(){
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
