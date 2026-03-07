package com.chemicaldev.zerabuilder.dsl.datatype;

import com.chemicaldev.zerabuilder.main.SQLDialect;

public class FloatType implements DataType{
    @Override
    public String render(SQLDialect dialect) {
        return "FLOAT";
    }
}
