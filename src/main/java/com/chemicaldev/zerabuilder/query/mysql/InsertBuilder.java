package com.chemicaldev.zerabuilder.query.mysql;

import java.util.ArrayList;
import java.util.List;

public class InsertBuilder {

    private String table;
    private String[] columns;
    private final List<Object> parameters = new ArrayList<>();

    public InsertBuilder into(String table){
        this.table = table;
        return this;
    }

    public InsertBuilder columns(String... columns){
        this.columns = columns;
        return this;
    }

    /**
     * Values to insert.
     * Stores them in parameters list and uses ? placeholders in SQL.
     */
    public InsertBuilder values(Object... values){
        if(columns == null || columns.length != values.length){
            throw new IllegalArgumentException("Number of values must match number of columns");
        }
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
        if(table == null || columns == null || columns.length == 0){
            throw new IllegalStateException("Table and columns must be specified");
        }

        StringBuilder placeholders = new StringBuilder();
        for(int i = 0; i < columns.length; i++){
            placeholders.append("?");
            if(i < columns.length - 1){
                placeholders.append(", ");
            }
        }

        String query = String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                table,
                String.join(", ", columns),
                placeholders.toString()
        );

        return query + ";";
    }
}
