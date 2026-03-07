package com.chemicaldev.zerabuilder.dsl.datatype;

import com.chemicaldev.zerabuilder.main.SQLDialect;

public class BooleanType implements DataType {

    @Override
    public String render(SQLDialect dialect) {
        return switch (dialect) {
            case MYSQL -> "BOOL";
            case SQLITE -> "INTEGER";
            case POSTRESQL -> "BOOL";
        };
    }
}