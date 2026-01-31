package com.chemicaldev.zerabuilder.query.mysql;

import java.util.ArrayList;
import java.util.List;

public class MySQLUpdateBuilder {

    private String table;
    private final List<String> setClauses = new ArrayList<>();
    private String whereClause;
    private boolean hasWhere = false;

    private final List<Object> parameters = new ArrayList<>();

    /** Set the table to update */
    public MySQLUpdateBuilder update(String table){
        this.table = table;
        return this;
    }

    /**
     * Set column/value pairs.
     * Example: set("name", "Alice"), set("age", 30)
     */
    public MySQLUpdateBuilder set(String column, Object value){
        setClauses.add(column + " = ?");
        parameters.add(value);
        return this;
    }

    /**
     * Initial WHERE clause using a raw string and parameters
     * (called by DSL)
     */
    public MySQLUpdateBuilder where(String condition, Object... params){
        this.whereClause = condition;
        this.hasWhere = true;
        addParams(params);
        return this;
    }

    /** Append AND condition */
    public MySQLUpdateBuilder and(String condition, Object... params){
        if(!hasWhere) throw new IllegalStateException("Cannot call 'and' before 'where'");
        this.whereClause += " AND " + condition;
        addParams(params);
        return this;
    }

    /** Append OR condition */
    public MySQLUpdateBuilder or(String condition, Object... params){
        if(!hasWhere) throw new IllegalStateException("Cannot call 'or' before 'where'");
        this.whereClause += " OR " + condition;
        addParams(params);
        return this;
    }

    private void addParams(Object... params){
        for(Object p : params) parameters.add(p);
    }

    public Object[] getParameters(){
        return parameters.toArray();
    }

    @Override
    public String toString(){
        if(table == null || setClauses.isEmpty() || parameters.isEmpty()){
            throw new IllegalStateException("Table and at least one SET clause must be specified");
        }

        String query = String.format(
                "UPDATE %s SET %s %s",
                table,
                String.join(", ", setClauses),
                hasWhere ? "WHERE " + whereClause : ""
        ).trim();

        return query + ";";
    }
}
