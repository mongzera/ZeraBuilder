package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.DeleteBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLDeleteBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteDeleteBuilder;

public class DeleteDSL {

    private final ZeraBuilder _instance;
    private final DeleteBuilder deleteBuilder;

    public DeleteDSL(ZeraBuilder _instance){
        this._instance = _instance;
        this.deleteBuilder = switch (_instance.getDialect()){
            case MYSQL -> new MySQLDeleteBuilder();
            case SQLITE -> new SQLiteDeleteBuilder();
            case POSTRESQL -> null;
        };
    }

    public DeleteDSL from(String table){
        deleteBuilder.from(table);
        return this;
    }

    public DeleteDSL where(Condition condition){
        deleteBuilder.where(condition.toSql(), condition.getParameters());
        return this;
    }

    public DeleteDSL and(Condition condition){
        deleteBuilder.and(condition.toSql(), condition.getParameters());
        return this;
    }

    public DeleteDSL or(Condition condition){
        deleteBuilder.or(condition.toSql(), condition.getParameters());
        return this;
    }

    public DeleteBuilder build(){
        return deleteBuilder;
    }

    @Override
    public String toString(){
        return deleteBuilder.toString();
    }

    public Object[] getParams(){
        return deleteBuilder.getParameters();
    }
}
