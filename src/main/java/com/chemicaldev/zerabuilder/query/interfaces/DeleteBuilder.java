package com.chemicaldev.zerabuilder.query.interfaces;

public interface DeleteBuilder extends ParameterizedBuilder{
    DeleteBuilder from(String table);

    DeleteBuilder where(String condition, Object... params);
    DeleteBuilder and(String condition, Object... params);
    DeleteBuilder or(String condition, Object... params);

}
