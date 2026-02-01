package com.chemicaldev.zerabuilder.query;

public interface DeleteBuilder {
    DeleteBuilder from(String table);

    DeleteBuilder where(String condition, Object... params);
    DeleteBuilder and(String condition, Object... params);
    DeleteBuilder or(String condition, Object... params);

    Object[] getParameters();
    String toString();
}
