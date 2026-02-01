package com.chemicaldev.zerabuilder.query.sqlite;

import com.chemicaldev.zerabuilder.query.CreateBuilder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteCreateBuilder implements CreateBuilder {

    private String table;
    private final List<String> columns = new ArrayList<>();
    private final List<String> constraints = new ArrayList<>();

    public SQLiteCreateBuilder table(String table){
        this.table = table;
        return this;
    }

    public SQLiteCreateBuilder column(String definition){
        columns.add(definition);
        return this;
    }

    @Override
    public CreateBuilder primaryKey(String... columns) {
        constraints.add(
                "PRIMARY KEY (" + String.join(", ", columns) + ")"
        );
        return this;
    }

    @Override
    public CreateBuilder unique(String... columns) {
        constraints.add(
                "UNIQUE (" + String.join(", ", columns) + ")"
        );
        return this;
    }

    @Override
    public String toString(){
        List<String> all = new ArrayList<>();
        all.addAll(columns);
        all.addAll(constraints);

        return String.format(
                "CREATE TABLE %s (%s);",
                table,
                String.join(", ", all)
        );
    }
}
