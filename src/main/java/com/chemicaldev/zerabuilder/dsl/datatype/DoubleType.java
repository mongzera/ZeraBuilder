package com.chemicaldev.zerabuilder.dsl.datatype;

import com.chemicaldev.zerabuilder.main.SQLDialect;

public class DoubleType implements DataType{
    @Override
    public String render(SQLDialect dialect) {
        return "DOUBLE";
    }
}
