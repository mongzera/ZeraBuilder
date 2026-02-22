package com.chemicaldev.zerabuilder.query.mysql;

import com.chemicaldev.zerabuilder.query.interfaces.AlterBuilder;

import java.util.ArrayList;
import java.util.List;

public class MySQLAlterBuilder implements AlterBuilder {

    private String table;
    private final List<String> actions = new ArrayList<>();

    public MySQLAlterBuilder table(String table){
        this.table = table;
        return this;
    }

    // Add column
    public MySQLAlterBuilder addColumn(String name, String type){
        actions.add("ADD COLUMN " + name + " " + type);
        return this;
    }

    // Drop column
    public MySQLAlterBuilder dropColumn(String name){
        actions.add("DROP COLUMN " + name);
        return this;
    }

    @Override
    public AlterBuilder modifyColumn(String name, String type) {
        return null;
    }

    // Rename column
    public MySQLAlterBuilder renameColumn(String oldName, String newName){
        actions.add("RENAME COLUMN " + oldName + " TO " + newName);
        return this;
    }

    // Rename table
    public MySQLAlterBuilder renameTable(String newName){
        actions.add("RENAME TO " + newName);
        return this;
    }

    // Generate SQL
    @Override
    public String toString(){
        if(table == null || actions.isEmpty()){
            throw new IllegalStateException("Table and at least one action must be specified");
        }

        return String.format(
                "ALTER TABLE %s %s;",
                table,
                String.join(", ", actions)
        );
    }
}
