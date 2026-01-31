package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.query.mysql.AlterBuilder;

public class AlterDSL {

    private final AlterBuilder builder;

    public AlterDSL(String table){
        this.builder = new AlterBuilder(table);
    }

    public AlterDSL addColumn(String name, String type){
        builder.addColumn(name, type);
        return this;
    }

    public AlterDSL dropColumn(String name){
        builder.dropColumn(name);
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
