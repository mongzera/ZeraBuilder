package com.chemicaldev.zerabuilder.query;

public interface CreateBuilder {
    CreateBuilder table(String table);
    CreateBuilder column(String definition);
    CreateBuilder primaryKey(String... columns);
    CreateBuilder unique(String... columns);
    String toString();
}
