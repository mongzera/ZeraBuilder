package com.chemicaldev.zerabuilder.dsl;

public interface Condition {
    String toSql();
    Object[] getParameters();
}
