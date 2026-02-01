package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.UpdateBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLInsertBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLUpdateBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteInsertBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteUpdateBuilder;

public class UpdateDSL {

    private final ZeraBuilder _instance;
    private final UpdateBuilder updateBuilder;

    public UpdateDSL(ZeraBuilder _instance){
        this._instance = _instance;
        this.updateBuilder = switch (_instance.dialect){
            case MYSQL -> new MySQLUpdateBuilder();
            case SQLITE -> new SQLiteUpdateBuilder();
            case POSTRESQL -> null;
        };
    }

    public UpdateDSL table(String table){
        updateBuilder.update(table);
        return this;
    }

    public UpdateDSL set(String[] columns, Object... values){
        for(int i = 0; i < columns.length; i++){
            updateBuilder.set(columns[i], values[i]);
        }
        return this;
    }

    public UpdateDSL set(String column, Object value){
        updateBuilder.set(column, value);

        return this;
    }

    public UpdateDSL where(Condition condition){
        updateBuilder.where(condition.toSql(), condition.getParameters());
        return this;
    }

    public UpdateDSL and(Condition condition){
        updateBuilder.and(condition.toSql(), condition.getParameters());
        return this;
    }

    public UpdateDSL or(Condition condition){
        updateBuilder.or(condition.toSql(), condition.getParameters());
        return this;
    }

    public UpdateBuilder build(){
        return updateBuilder;
    }

    @Override
    public String toString(){
        return updateBuilder.toString();
    }

    public Object[] getParams(){
        return updateBuilder.getParameters();
    }
}
