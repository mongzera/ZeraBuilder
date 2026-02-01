package com.chemicaldev.zerabuilder.query.sqlite;

import com.chemicaldev.zerabuilder.query.DeleteBuilder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDeleteBuilder implements DeleteBuilder {

    private String table;
    private String whereClause;
    private boolean hasWhere = false;

    private final List<Object> parameters = new ArrayList<>();

    public SQLiteDeleteBuilder from(String table){
        this.table = table;
        return this;
    }

    public SQLiteDeleteBuilder where(String condition, Object... params){
        this.whereClause = condition;
        this.hasWhere = true;
        addParams(params);
        return this;
    }

    public SQLiteDeleteBuilder and(String condition, Object... params){
        this.whereClause += " AND " + condition;
        addParams(params);
        return this;
    }

    public SQLiteDeleteBuilder or(String condition, Object... params){
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
                "DELETE FROM %s%s;",
                table,
                where
        );
    }

    private void addParams(Object... params){
        for(Object p : params){
            parameters.add(p);
        }
    }
}
