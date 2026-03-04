package com.chemicaldev.zerabuilder.query;

import com.chemicaldev.zerabuilder.query.interfaces.SelectBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base for SELECT builders. MySQL and SQLite implementations extend this
 * and only override the parts that differ by dialect (e.g. OFFSET syntax if needed).
 */
public abstract class AbstractSelectBuilder implements SelectBuilder {

    protected String[] columns;
    protected String table;
    protected final List<String> joins = new ArrayList<>();
    protected String whereClause;
    protected String[] groupBy;
    protected String havingClause;
    protected String[] orderBy;
    protected Integer limit;
    protected Integer offset;

    protected boolean hasWhere = false;
    protected boolean hasGroupBy = false;
    protected boolean hasHaving = false;
    protected boolean hasOrderBy = false;
    protected boolean hasLimit = false;
    protected boolean hasOffset = false;

    protected final List<Object> parameters = new ArrayList<>();

    @Override
    public AbstractSelectBuilder select(String... columns) {
        this.columns = columns;
        return this;
    }

    @Override
    public AbstractSelectBuilder from(String table) {
        this.table = table;
        return this;
    }

    @Override
    public AbstractSelectBuilder join(String... joinExpressions) {
        Collections.addAll(this.joins, joinExpressions);
        return this;
    }

    @Override
    public AbstractSelectBuilder where(String condition, Object... params) {
        this.whereClause = condition;
        this.hasWhere = true;
        addParams(params);
        return this;
    }

    @Override
    public AbstractSelectBuilder and(String condition, Object... params) {
        if (!hasWhere) throw new IllegalStateException("Cannot call 'and' before 'where'");
        this.whereClause += " AND " + condition;
        addParams(params);
        return this;
    }

    @Override
    public AbstractSelectBuilder or(String condition, Object... params) {
        if (!hasWhere) throw new IllegalStateException("Cannot call 'or' before 'where'");
        this.whereClause += " OR " + condition;
        addParams(params);
        return this;
    }

    @Override
    public AbstractSelectBuilder groupBy(String... columns) {
        this.groupBy = columns;
        this.hasGroupBy = true;
        return this;
    }

    @Override
    public AbstractSelectBuilder having(String condition, Object... params) {
        this.havingClause = condition;
        this.hasHaving = true;
        addParams(params);
        return this;
    }

    @Override
    public AbstractSelectBuilder orderBy(String... columns) {
        this.orderBy = columns;
        this.hasOrderBy = true;
        return this;
    }

    @Override
    public AbstractSelectBuilder limit(int limit) {
        this.limit = limit;
        this.hasLimit = true;
        return this;
    }

    @Override
    public AbstractSelectBuilder offset(int offset) {
        this.offset = offset;
        this.hasOffset = true;
        return this;
    }

    @Override
    public Object[] getParameters() {
        return parameters.toArray();
    }

    protected void addParams(Object... params) {
        for (Object p : params) parameters.add(p);
    }

    protected String compileColumns() {
        if (columns == null || columns.length == 0) return "*";
        return String.join(", ", columns);
    }

    protected String compileJoins() {
        if (joins.isEmpty()) return "";
        return String.join(" ", joins);
    }

    protected String compileCondition() {
        if (!hasWhere) return "";
        if (whereClause.contains(" AND ") || whereClause.contains(" OR ")) {
            return "WHERE (" + whereClause + ")";
        }
        return "WHERE " + whereClause;
    }

    protected String compileGroupBy() {
        if (hasGroupBy) return "GROUP BY " + String.join(", ", groupBy);
        return "";
    }

    protected String compileHaving() {
        if (hasHaving) return "HAVING " + havingClause;
        return "";
    }

    protected String compileOrderBy() {
        if (hasOrderBy) return "ORDER BY " + String.join(", ", orderBy);
        return "";
    }

    protected String compileLimit() {
        if (hasLimit) return "LIMIT " + limit;
        return "";
    }

    protected String compileOffset() {
        if (hasOffset) return "OFFSET " + offset;
        return "";
    }

    @Override
    public String toString() {
        if (table == null) throw new IllegalStateException("Table must be specified");

        return String.format(
                "SELECT %s FROM %s %s %s %s %s %s %s %s",
                compileColumns(),
                table,
                compileJoins(),
                compileCondition(),
                compileGroupBy(),
                compileHaving(),
                compileOrderBy(),
                compileLimit(),
                compileOffset()
        ).replaceAll("\\s+", " ").trim() + ";";
    }
}
