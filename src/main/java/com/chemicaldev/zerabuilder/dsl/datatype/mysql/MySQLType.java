package com.chemicaldev.zerabuilder.dsl.datatype.mysql;

public class MySQLType {
    public static String text(int len){
        if (len == 0) len = 1;
        return String.format("VARCHAR(%s)", len);
    }

    public static String integer(){
        return "INT";
    }

    public static String date(){
        return "DATE";
    }

    public static String datetime(){
        return "DATETIME";
    }
}
