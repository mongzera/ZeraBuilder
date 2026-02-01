package com.chemicaldev.zerabuilder.main;

public class Main {
    public static void main(String[] args){
        ZeraBuilder builder = new ZeraBuilder(SQLDialect.SQLITE);

        // Add Data Types
        builder.makeTable("users");
    }
}
