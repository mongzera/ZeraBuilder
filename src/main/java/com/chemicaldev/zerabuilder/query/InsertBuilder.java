package com.chemicaldev.zerabuilder.query;

public interface InsertBuilder {
    InsertBuilder into(String table);
    InsertBuilder columns(String... columns);
    InsertBuilder values(Object... values);

    Object[] getParameters();
    String toString();
}
