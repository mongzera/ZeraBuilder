package com.chemicaldev.zerabuilder.dsl;

public class SimpleCondition implements Condition {

    private final String sql;
    private final Object[] parameters;

    public SimpleCondition(String sql, Object... parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    @Override
    public String toSql() {
        return sql;
    }

    @Override
    public Object[] getParameters() {
        return parameters;
    }
}