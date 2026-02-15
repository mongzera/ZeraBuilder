package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.AlterBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLAlterBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLDeleteBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteAlterBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteDeleteBuilder;

public class AlterDSL {
    private final ZeraBuilder _instance;
    private final AlterBuilder builder;

    public AlterDSL(ZeraBuilder _instance){
        this._instance = _instance;
        this.builder = switch (_instance.getDialect()){
            case MYSQL -> new MySQLAlterBuilder();
            case SQLITE -> new SQLiteAlterBuilder();
            case POSTRESQL -> null;
        };
    }

    public AlterDSL table(String table){
        builder.table(table);
        return this;
    }

    public AlterDSL addColumn(String name, String type){
        builder.addColumn(name, type);
        return this;
    }

    public AlterDSL dropColumn(String name){
        builder.dropColumn(name);
        return this;
    }

    public AlterDSL modifyColumn(String name, String type){
        builder.modifyColumn(name, type);
        return this;
    }

    public AlterDSL renameColumn(String oldName, String newName){
        builder.renameColumn(oldName, newName);
        return this;
    }

    public AlterDSL renameTable(String newName){
        builder.renameTable(newName);
        return this;
    }

    public AlterBuilder build(){
        return builder;
    }

    @Override
    public String toString(){
        return builder.toString();
    }
}
