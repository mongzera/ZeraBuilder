package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.mysql.SelectBuilder;

/**
 * DSL wrapper for SELECT queries.
 * Currently uses MySQL SelectBuilder.
 * Future plan: create SelectBuilder interface + multiple DB implementations.
 */

public class SelectDSL {

    private final ZeraBuilder _instance;
    private final SelectBuilder selectBuilder;

    public SelectDSL(ZeraBuilder _instance){
        this._instance = _instance;
        this.selectBuilder = new SelectBuilder();
    }

    // SELECT columns
    public SelectDSL list(String... columns){
        selectBuilder.select(columns);
        return this;
    }

    // FROM table
    public SelectDSL from(String table){
        selectBuilder.from(table);
        return this;
    }

    // WHERE condition
    public SelectDSL where(Condition condition){
        selectBuilder.where(condition.toSql(), condition.getParameters());
        return this;
    }

    // AND condition (appends to existing WHERE)
    public SelectDSL and(Condition condition){
        selectBuilder.and(condition.toSql(), condition.getParameters());
        return this;
    }

    // OR condition (appends to existing WHERE)
    public SelectDSL or(Condition condition){
        selectBuilder.or(condition.toSql(), condition.getParameters());
        return this;
    }

    // JOINs
    public SelectDSL join(String joinExpression){
        selectBuilder.join(joinExpression);
        return this;
    }

    // GROUP BY
    public SelectDSL groupBy(String... columns){
        selectBuilder.groupBy(columns);
        return this;
    }

    // ORDER BY
    public SelectDSL orderBy(String... columns){
        selectBuilder.orderBy(columns);
        return this;
    }

    // LIMIT
    public SelectDSL limit(int limit){
        selectBuilder.limit(limit);
        return this;
    }

    // Expose SQL string
    @Override
    public String toString(){
        return selectBuilder.toString();
    }

    // Expose parameters array
    public Object[] getParams(){
        return selectBuilder.getParameters();
    }

    // Expose underlying builder for advanced use or compilation
    public SelectBuilder build(){
        return selectBuilder;
    }
}
