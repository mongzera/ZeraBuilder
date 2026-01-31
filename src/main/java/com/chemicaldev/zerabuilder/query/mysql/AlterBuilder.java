package com.chemicaldev.zerabuilder.query.mysql;

import java.util.ArrayList;
import java.util.List;

public class AlterBuilder {

    private String table;
    private final List<String> actions = new ArrayList<>();

    // Constructor sets table
    public AlterBuilder(String table){
        this.table = table;
    }

    // Add column
    public AlterBuilder addColumn(String name, String type){
        actions.add("ADD COLUMN " + name + " " + type);
        return this;
    }

    // Drop column
    public AlterBuilder dropColumn(String name){
        actions.add("DROP COLUMN " + name);
        return this;
    }

    // Rename column
    public AlterBuilder renameColumn(String oldName, String newName){
        actions.add("RENAME COLUMN " + oldName + " TO " + newName);
        return this;
    }

    // Rename table
    public AlterBuilder renameTable(String newName){
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
