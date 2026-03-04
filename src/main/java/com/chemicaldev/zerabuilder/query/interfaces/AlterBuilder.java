package com.chemicaldev.zerabuilder.query.interfaces;

public interface AlterBuilder extends Builder{

    AlterBuilder table(String table);
    AlterBuilder addColumn(String name, String type);
    AlterBuilder dropColumn(String name);
    AlterBuilder modifyColumn(String name, String type);
    AlterBuilder renameColumn(String oldName, String newName);
    AlterBuilder renameTable(String newName);

}
