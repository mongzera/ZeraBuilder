package com.chemicaldev.zerabuilder.query.sqlite;

import com.chemicaldev.zerabuilder.query.interfaces.SelectBuilder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteSelectBuilder implements SelectBuilder {

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

    @Override
    public SQLiteSelectBuilder select(String... columns) {
        this.columns = columns;
        return this;
    }

    @Override
    public SQLiteSelectBuilder from(String table) {
        this.table = table;
        return this;
    }

    @Override
    public SQLiteSelectBuilder join(String... joins) {
        this.joins = joins;
        this.hasJoin = true;
        return this;
    }

    @Override
    public SQLiteSelectBuilder where(String condition, Object... params) {
        this.whereClause = condition;
        this.hasWhere = true;
        addParams(params);
        return this;
    }

    @Override
    public SQLiteSelectBuilder and(String condition, Object... params) {
        if (!hasWhere) throw new IllegalStateException("Cannot call 'and' before 'where'");
        this.whereClause = this.whereClause + " AND " + condition;
        addParams(params);
        return this;
    }

    @Override
    public SQLiteSelectBuilder or(String condition, Object... params) {
        if (!hasWhere) throw new IllegalStateException("Cannot call 'or' before 'where'");
        this.whereClause = "(" + this.whereClause + " OR " + condition + ")";
        addParams(params);
        return this;
    }

    @Override
    public SQLiteSelectBuilder groupBy(String... columns) {
        this.groupBy = columns;
        this.hasGroupBy = true;
        return this;
    }

    @Override
    public SQLiteSelectBuilder orderBy(String... columns) {
        this.orderBy = columns;
        this.hasOrderBy = true;
        return this;
    }

    @Override
    public SQLiteSelectBuilder limit(int limit) {
        this.limit = limit;
        this.hasLimit = true;
        return this;
    }

    @Override
    public Object[] getParameters() {
        return parameters.toArray();
    }

    private void addParams(Object... params) {
        for (Object p : params) parameters.add(p);
    }

    @Override
    public String toString() {
        if (table == null) throw new IllegalStateException("Table must be specified");

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

    private String compileColumns() {
        if (columns == null || columns.length == 0) return "*";
        return String.join(", ", columns);
    }

    private String compileJoins() {
        if (hasJoin) return String.join(" ", joins);
        return "";
    }

    private String compileCondition() {
        if (hasWhere) {
            // parentheses already handled in and() / or()
            return "WHERE " + whereClause;
        }
        return "";
    }

    private String compileGroupBy() {
        if (hasGroupBy) return "GROUP BY " + String.join(", ", groupBy);
        return "";
    }

    private String compileOrderBy() {
        if (hasOrderBy) return "ORDER BY " + String.join(", ", orderBy);
        return "";
    }

    private String compileLimit() {
        if (hasLimit) return "LIMIT " + limit;
        return "";
    }
}
