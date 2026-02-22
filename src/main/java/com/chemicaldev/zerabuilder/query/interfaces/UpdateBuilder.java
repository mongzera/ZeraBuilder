package com.chemicaldev.zerabuilder.query.interfaces;

public interface UpdateBuilder extends ParameterizedBuilder{
    UpdateBuilder update(String table);
    UpdateBuilder set(String column, Object value);

    UpdateBuilder where(String condition, Object... params);
    UpdateBuilder and(String condition, Object... params);
    UpdateBuilder or(String condition, Object... params);

}
