package com.chemicaldev.zerabuilder.query.sqlite;

import com.chemicaldev.zerabuilder.query.UpdateBuilder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUpdateBuilder implements UpdateBuilder {

    private String table;
    private final List<String> setClauses = new ArrayList<>();
    private String whereClause;
    private boolean hasWhere = false;

    private final List<Object> parameters = new ArrayList<>();

    public SQLiteUpdateBuilder update(String table){
        this.table = table;
        return this;
    }

    public SQLiteUpdateBuilder set(String column, Object value){
        setClauses.add(column + " = ?");
        parameters.add(value);
        return this;
    }

    public SQLiteUpdateBuilder where(String condition, Object... params){
        this.whereClause = condition;
        this.hasWhere = true;
        addParams(params);
        return this;
    }

    public SQLiteUpdateBuilder and(String condition, Object... params){
        this.whereClause += " AND " + condition;
        addParams(params);
        return this;
    }

    public SQLiteUpdateBuilder or(String condition, Object... params){
        this.whereClause += " OR " + condition;
        addParams(params);
        return this;
    }

    public Object[] getParameters(){
        return parameters.toArray();
    }

    @Override
    public String toString(){
        String where = hasWhere
                ? " WHERE " + whereClause
                : "";

        return String.format(
                "UPDATE %s SET %s%s;",
                table,
                String.join(", ", setClauses),
                where
        );
    }

    private void addParams(Object... params){
        for(Object p : params){
            parameters.add(p);
        }
    }
}
