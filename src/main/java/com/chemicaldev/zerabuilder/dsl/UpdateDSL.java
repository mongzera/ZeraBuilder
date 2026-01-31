package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.query.mysql.UpdateBuilder;

public class UpdateDSL {

    private final UpdateBuilder builder;

    public UpdateDSL(){
        this.builder = new UpdateBuilder();
    }

    public UpdateDSL table(String table){
        builder.update(table);
        return this;
    }

    public UpdateDSL set(String[] columns, Object... values){
        for(int i = 0; i < columns.length; i++){
            builder.set(columns[i], values[i]);
        }
        return this;
    }

    public UpdateDSL set(String column, Object value){
        builder.set(column, value);

        return this;
    }

    public UpdateDSL where(Condition condition){
        builder.where(condition.toSql(), condition.getParameters());
        return this;
    }

    public UpdateDSL and(Condition condition){
        builder.and(condition.toSql(), condition.getParameters());
        return this;
    }

    public UpdateDSL or(Condition condition){
        builder.or(condition.toSql(), condition.getParameters());
        return this;
    }

    public UpdateBuilder build(){
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
