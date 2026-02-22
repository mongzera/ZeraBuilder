package com.chemicaldev.zerabuilder.query.mysql;

import com.chemicaldev.zerabuilder.query.interfaces.SelectBuilder;

import java.util.ArrayList;
import java.util.List;

public class MySQLSelectBuilder implements SelectBuilder {

    private String[] columns;
    private String table;
    private String[] joins;
    private String whereClause;
    private String[] groupBy;
    private String[] orderBy;
    private Integer limit;

    private boolean hasWhere = false;
    private boolean hasJoin = false;
    private boolean hasGroupBy = false;
    private boolean hasOrderBy = false;
    private boolean hasLimit = false;

    private final List<Object> parameters = new ArrayList<>();

    public MySQLSelectBuilder select(String... columns){
        this.columns = columns;
        return this;
    }

    public MySQLSelectBuilder from(String table){
        this.table = table;
        return this;
    }

    public MySQLSelectBuilder join(String... joins){
        this.joins = joins;
        this.hasJoin = true;
        return this;
    }

    public MySQLSelectBuilder where(String condition, Object... params){
        this.whereClause = condition;
        this.hasWhere = true;
        addParams(params);
        return this;
    }

    public MySQLSelectBuilder and(String condition, Object... params){
        this.whereClause += " AND " + condition;
        addParams(params);
        return this;
    }

    public MySQLSelectBuilder or(String condition, Object... params){
        this.whereClause += " OR " + condition;
        addParams(params);
        return this;
    }

    public MySQLSelectBuilder groupBy(String... columns){
        this.groupBy = columns;
        this.hasGroupBy = true;
        return this;
    }

    public MySQLSelectBuilder orderBy(String... columns){
        this.orderBy = columns;
        this.hasOrderBy = true;
        return this;
    }

    public MySQLSelectBuilder limit(int limit){
        this.limit = limit;
        this.hasLimit = true;
        return this;
    }

    public Object[] getParameters(){
        return parameters.toArray();
    }

    public String toString(){
        String query = String.format(
                "SELECT %s FROM %s %s %s %s %s %s",
                compileColumns(),
                table,
                compileJoins(),
                compileCondition(),
                compileGroupBy(),
                compileOrderBy(),
                compileLimit()
        ).replaceAll("\\s+", " ").trim();

        return query + ";";
    }

    private void addParams(Object... params){
        for(Object p : params){
            parameters.add(p);
        }
    }

    private String compileColumns(){
        if(columns == null || columns.length == 0) return "*";
        return String.join(", ", columns);
    }

    private String compileJoins(){
        if(hasJoin) return String.join(" ", joins);
        return "";
    }

    private String compileCondition(){
        if(hasWhere){
            // Wrap the entire WHERE clause in parentheses if it contains AND/OR
            if(whereClause.contains(" AND ") || whereClause.contains(" OR ")){
                return "WHERE (" + whereClause + ")";
            } else {
                return "WHERE " + whereClause;
            }
        }
        return "";
    }


    private String compileGroupBy(){
        if(hasGroupBy) return "GROUP BY " + String.join(", ", groupBy);
        return "";
    }

    private String compileOrderBy(){
        if(hasOrderBy) return "ORDER BY " + String.join(", ", orderBy);
        return "";
    }

    private String compileLimit(){
        if(hasLimit) return "LIMIT " + limit;
        return "";
    }
}
