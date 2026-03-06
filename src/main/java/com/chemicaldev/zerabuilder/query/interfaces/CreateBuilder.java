package com.chemicaldev.zerabuilder.query.interfaces;


import com.chemicaldev.zerabuilder.query.ColumnBuilder;

public interface CreateBuilder {
    CreateBuilder table(String name);
    ColumnBuilder column(String name);
    CreateBuilder addTimestamp();
}