package com.chemicaldev.zerabuilder.dsl.datatype;

import com.chemicaldev.zerabuilder.main.SQLDialect;

public class DateType implements DataType {

    @Override
    public String render(SQLDialect dialect) {
        return switch (dialect) {
            case MYSQL -> "DATE";
            case SQLITE -> "DATE";
            case POSTRESQL -> "DATE";
        };
    }
}