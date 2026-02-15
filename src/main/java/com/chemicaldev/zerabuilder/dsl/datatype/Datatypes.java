package com.chemicaldev.zerabuilder.dsl.datatype;

import com.chemicaldev.zerabuilder.dsl.datatype.mysql.MySQLType;
import com.chemicaldev.zerabuilder.dsl.datatype.sqlite.SQLiteType;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;

public class Datatypes {
    private SQLDialect dialect;

    public Datatypes(ZeraBuilder builder){
        dialect = builder.getDialect();
    }

    public String string(int len){
        return switch (dialect){
            case SQLDialect.SQLITE -> SQLiteType.text(len);
            case SQLDialect.MYSQL -> MySQLType.text(len);
            case POSTRESQL -> "";
        };
    }

    public String string(){
        return string(255);
    }

    public String integer(){
        return switch (dialect){
            case SQLDialect.SQLITE -> SQLiteType.integer();
            case SQLDialect.MYSQL -> MySQLType.integer();
            case POSTRESQL -> "";
        };
    }

    public String date(){
        return switch (dialect){
            case SQLDialect.SQLITE -> SQLiteType.date();
            case SQLDialect.MYSQL -> MySQLType.date();
            case POSTRESQL -> "";
        };
    }

    public String datetime(){
        return switch (dialect){
            case SQLDialect.SQLITE -> SQLiteType.datetime();
            case SQLDialect.MYSQL -> MySQLType.datetime();
            case POSTRESQL -> "";
        };
    }
}
