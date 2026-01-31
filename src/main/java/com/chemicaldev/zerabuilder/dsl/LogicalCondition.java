package com.chemicaldev.zerabuilder.dsl;

import java.util.*;

public class LogicalCondition implements Condition {
    private final String operator;
    private final List<Condition> conditions = new ArrayList<>();

    public LogicalCondition(String operator, Condition... conditions) {
        this.operator = operator;
        this.conditions.addAll(Arrays.asList(conditions));
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < conditions.size(); i++) {
            sb.append(conditions.get(i).toSql());
            if (i < conditions.size() - 1) sb.append(" ").append(operator).append(" ");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Object[] getParameters() {
        List<Object> params = new ArrayList<>();
        for (Condition c : conditions) params.addAll(Arrays.asList(c.getParameters()));
        return params.toArray();
    }
}
