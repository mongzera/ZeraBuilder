package com.chemicaldev.zerabuilder.query.sqlite;

import com.chemicaldev.zerabuilder.query.InsertBuilder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteInsertBuilder implements InsertBuilder {

    private String table;
    private String[] columns;
    private final List<Object> parameters = new ArrayList<>();

    public SQLiteInsertBuilder into(String table){
        this.table = table;
        return this;
    }

    public SQLiteInsertBuilder columns(String... columns){
        this.columns = columns;
        return this;
    }

    public SQLiteInsertBuilder values(Object... values){
        parameters.clear(); // IMPORTANT!, clear first!

        for(Object v : values){
            parameters.add(v);
        }
        return this;
    }

    public Object[] getParameters(){
        return parameters.toArray();
    }

    @Override
    public String toString(){
        String placeholders = String.join(
                ", ",
                java.util.Collections.nCopies(columns.length, "?")
        );

        return String.format(
                "INSERT INTO %s (%s) VALUES (%s);",
                table,
                String.join(", ", columns),
                placeholders
        );
    }
}
