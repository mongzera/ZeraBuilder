package com.chemicaldev.zerabuilder.dsl.datatype;

import com.chemicaldev.zerabuilder.main.SQLDialect;

public class StringType implements DataType {

    private final Integer length;

    public StringType(Integer length) {
        this.length = length;
    }

    @Override
    public String render(SQLDialect dialect) {
        return switch (dialect) {
            case MYSQL, POSTGRESQL -> "VARCHAR(" + (length != null ? length : 255) + ")";
            case SQLITE -> "TEXT";
        };
    }
}