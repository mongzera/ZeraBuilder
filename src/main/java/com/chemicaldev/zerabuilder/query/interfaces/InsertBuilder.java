package com.chemicaldev.zerabuilder.query.interfaces;

public interface InsertBuilder extends ParameterizedBuilder{
    InsertBuilder into(String table);
    InsertBuilder columns(String... columns);
    InsertBuilder values(Object... values);
}
