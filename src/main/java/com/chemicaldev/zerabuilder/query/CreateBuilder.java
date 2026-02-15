package com.chemicaldev.zerabuilder.query;

public interface CreateBuilder extends Builder{
    CreateBuilder table(String table);
    CreateBuilder column(String definition);
    CreateBuilder primaryKey(String... columns);
    CreateBuilder unique(String... columns);
    CreateBuilder timestamps();
}
