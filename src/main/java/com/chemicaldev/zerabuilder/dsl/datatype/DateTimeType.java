package com.chemicaldev.zerabuilder.dsl.datatype;
import com.chemicaldev.zerabuilder.main.SQLDialect;


public class DateTimeType implements DataType {

    @Override
    public String render(SQLDialect dialect) {
        return switch (dialect) {
            case MYSQL -> "DATETIME";
            case SQLITE -> "DATETIME";
            case POSTRESQL -> "TIMESTAMP";
        };
    }
}