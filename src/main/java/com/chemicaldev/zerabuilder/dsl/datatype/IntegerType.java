package com.chemicaldev.zerabuilder.dsl.datatype;

import com.chemicaldev.zerabuilder.main.SQLDialect;

public class IntegerType implements DataType {

    @Override
    public String render(SQLDialect dialect) {
        return switch (dialect) {
            case MYSQL -> "INT";
            case SQLITE -> "INTEGER";
            case POSTRESQL -> "INTEGER";
        };
    }
}