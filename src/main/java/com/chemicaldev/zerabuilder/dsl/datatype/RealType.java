package com.chemicaldev.zerabuilder.dsl.datatype;

import com.chemicaldev.zerabuilder.main.SQLDialect;

public class RealType implements DataType {
    @Override
    public String render(SQLDialect dialect) {
        return switch (dialect) {
            case MYSQL, SQLITE, POSTRESQL -> "REAL";
        };
    }
}
