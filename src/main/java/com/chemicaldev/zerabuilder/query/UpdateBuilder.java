package com.chemicaldev.zerabuilder.query;

public interface UpdateBuilder {
    UpdateBuilder update(String table);
    UpdateBuilder set(String column, Object value);

    UpdateBuilder where(String condition, Object... params);
    UpdateBuilder and(String condition, Object... params);
    UpdateBuilder or(String condition, Object... params);

    Object[] getParameters();
    String toString();
}
