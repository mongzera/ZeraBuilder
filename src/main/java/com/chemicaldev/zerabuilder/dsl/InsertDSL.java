package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.InsertBuilder;
import com.chemicaldev.zerabuilder.query.SelectBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLInsertBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLSelectBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteInsertBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteSelectBuilder;

public class InsertDSL implements ExecutableDSL {

    private final ZeraBuilder _instance;
    private final InsertBuilder insertBuilder;

    public InsertDSL(ZeraBuilder _instance){
        this._instance = _instance;
        this.insertBuilder = switch (_instance.getDialect()){
            case MYSQL -> new MySQLInsertBuilder();
            case SQLITE -> new SQLiteInsertBuilder();
            case POSTRESQL -> null;
        };
    }

    public InsertDSL into(String table){
        insertBuilder.into(table);
        return this;
    }

    public InsertDSL columns(String... columns){
        insertBuilder.columns(columns);
        return this;
    }

    public InsertDSL values(Object... values){
        insertBuilder.values(values);
        return this;
    }

    public InsertBuilder build(){
        return insertBuilder;
    }

    @Override
    public String toString(){
        return insertBuilder.toString();
    }

    public Object[] getParameters(){
        return insertBuilder.getParameters();
    }

    @Override
    public ExecutionType type() {
        return ExecutionType.UPDATE;
    }
}
