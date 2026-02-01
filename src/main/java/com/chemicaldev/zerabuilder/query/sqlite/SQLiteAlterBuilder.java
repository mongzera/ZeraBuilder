package com.chemicaldev.zerabuilder.query.sqlite;

import com.chemicaldev.zerabuilder.query.AlterBuilder;
import com.chemicaldev.zerabuilder.query.ZLog;

public class SQLiteAlterBuilder implements AlterBuilder {

    private String table;
    private String action = "";

    public SQLiteAlterBuilder table(String table){
        this.table = table;
        return this;
    }

    @Override
    public SQLiteAlterBuilder addColumn(String name, String type) {
        this.action = "ADD COLUMN " + name + " " + type;
        return this;
    }

    @Override
    public SQLiteAlterBuilder dropColumn(String name) {
        ZLog.warn("DROP COLUMN");
        return this;
    }

    @Override
    public SQLiteAlterBuilder modifyColumn(String name, String type) {
        ZLog.warn("MODIFY COLUMN");
        return this;
    }

    @Override
    public SQLiteAlterBuilder renameColumn(String oldName, String newName) {
        this.action = "RENAME COLUMN " + oldName + " TO " + newName;
        return this;
    }

    @Override
    public SQLiteAlterBuilder renameTable(String newName) {
        this.action = "RENAME TO " + newName;
        return this;
    }

    @Override
    public String toString(){
        if(action.isEmpty()) return "";
        return "ALTER TABLE " + table + " " + action + ";";
    }
}
