package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.CreateBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLCreateBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteCreateBuilder;

public class CreateDSL {

    private final ZeraBuilder _instance;
    private final CreateBuilder createBuilder;

    public CreateDSL(ZeraBuilder _instance){
        this._instance = _instance;
        this.createBuilder = switch (_instance.dialect){
            case MYSQL -> new MySQLCreateBuilder();
            case SQLITE -> new SQLiteCreateBuilder();
            case POSTRESQL -> null;
        };
    }

    public CreateDSL table(String tableName){
        createBuilder.table(tableName);
        return this;
    }

    public CreateDSL column(String name, String type){
        if(this._instance.dialect == SQLDialect.MYSQL) ((MySQLCreateBuilder) createBuilder).column(name, type);
        else createBuilder.column(name + " " + type); // Replace this!
        return this;
    }

    public CreateDSL column(String definition){
        createBuilder.column(definition);
        return this;
    }

    public CreateDSL primaryKey(String... columns){
        createBuilder.primaryKey(columns);
        return this;
    }

    public CreateDSL unique(String... columns){
        createBuilder.unique(columns);
        return this;
    }

    public CreateBuilder build(){
        return createBuilder;
    }

    @Override
    public String toString(){
        return createBuilder.toString();
    }
}
