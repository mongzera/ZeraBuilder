package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.interfaces.SelectBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLSelectBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteSelectBuilder;

/**
 * DSL wrapper for SELECT queries.
 * Currently uses MySQL MySQLSelectBuilder.
 * Future plan: create MySQLSelectBuilder interface + multiple DB implementations.
 */

public class SelectDSL implements ExecutableDSL{

    private final ZeraBuilder _instance;
    private final SelectBuilder selectBuilder;

    public SelectDSL(ZeraBuilder _instance){
        this._instance = _instance;
        this.selectBuilder = switch (_instance.getDialect()){
            case MYSQL -> new MySQLSelectBuilder();
            case SQLITE -> new SQLiteSelectBuilder();
            case POSTRESQL -> null;
        };
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


    @Override
    public ExecutionType type() {
        return ExecutionType.QUERY;
    }

    // Expose parameters array
    @Override
    public Object[] getParameters(){
        return selectBuilder.getParameters();
    }
    
}
