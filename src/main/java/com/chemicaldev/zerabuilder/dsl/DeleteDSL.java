package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.query.mysql.DeleteBuilder;

public class DeleteDSL {

    private final DeleteBuilder builder;

    public DeleteDSL(){
        this.builder = new DeleteBuilder();
    }

    public DeleteDSL from(String table){
        builder.from(table);
        return this;
    }

    public DeleteDSL where(Condition condition){
        builder.where(condition.toSql(), condition.getParameters());
        return this;
    }

    public DeleteDSL and(Condition condition){
        builder.and(condition.toSql(), condition.getParameters());
        return this;
    }

    public DeleteDSL or(Condition condition){
        builder.or(condition.toSql(), condition.getParameters());
        return this;
    }

    public DeleteBuilder build(){
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
