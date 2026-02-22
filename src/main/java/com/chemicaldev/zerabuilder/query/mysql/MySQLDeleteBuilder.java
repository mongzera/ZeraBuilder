package com.chemicaldev.zerabuilder.query.mysql;

import com.chemicaldev.zerabuilder.query.interfaces.DeleteBuilder;

import java.util.ArrayList;
import java.util.List;

public class MySQLDeleteBuilder implements DeleteBuilder {

    private String table;
    private String whereClause;
    private boolean hasWhere = false;

    private final List<Object> parameters = new ArrayList<>();

    public MySQLDeleteBuilder from(String table){
        this.table = table;
        return this;
    }

    // Initial WHERE
    public MySQLDeleteBuilder where(String condition, Object... params){
        this.whereClause = condition;
        this.hasWhere = true;
        addParams(params);
        return this;
    }

    // Append AND condition
    public MySQLDeleteBuilder and(String condition, Object... params){
        if (!hasWhere) throw new IllegalStateException("Cannot call 'and' before 'where'");
        this.whereClause += " AND " + condition;
        addParams(params);
        return this;
    }

    // Append OR condition
    public MySQLDeleteBuilder or(String condition, Object... params){
        if (!hasWhere) throw new IllegalStateException("Cannot call 'or' before 'where'");
        this.whereClause += " OR " + condition;
        addParams(params);
        return this;
    }

    public Object[] getParameters(){
        return parameters.toArray();
    }

    public String toString(){
        if(table == null){
            throw new IllegalStateException("Table must be specified");
        }

        String query = String.format(
                "DELETE FROM %s %s",
                table,
                compileCondition()
        ).trim();

        return query + ";";
    }


    private String compileCondition(){
        if(hasWhere){
            if(whereClause.contains(" AND ") || whereClause.contains(" OR ")){
                return "WHERE (" + whereClause + ")";
            } else {
                return "WHERE " + whereClause;
            }
        }
        return "";
    }


    private void addParams(Object... params){
        for(Object p : params){
            this.parameters.add(p);
        }
    }
}
