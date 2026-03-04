package com.chemicaldev.zerabuilder.query;

import com.chemicaldev.zerabuilder.query.interfaces.DeleteBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base for DELETE builders. Shared logic across all dialects.
 */
public abstract class AbstractDeleteBuilder implements DeleteBuilder {

    protected String table;
    protected String whereClause;
    protected boolean hasWhere = false;

    protected final List<Object> parameters = new ArrayList<>();

    @Override
    public AbstractDeleteBuilder from(String table) {
        this.table = table;
        return this;
    }

    @Override
    public AbstractDeleteBuilder where(String condition, Object... params) {
        this.whereClause = condition;
        this.hasWhere = true;
        addParams(params);
        return this;
    }

    @Override
    public AbstractDeleteBuilder and(String condition, Object... params) {
        if (!hasWhere) throw new IllegalStateException("Cannot call 'and' before 'where'");
        this.whereClause += " AND " + condition;
        addParams(params);
        return this;
    }

    @Override
    public AbstractDeleteBuilder or(String condition, Object... params) {
        if (!hasWhere) throw new IllegalStateException("Cannot call 'or' before 'where'");
        this.whereClause += " OR " + condition;
        addParams(params);
        return this;
    }

    @Override
    public Object[] getParameters() {
        return parameters.toArray();
    }

    protected void addParams(Object... params) {
        for (Object p : params) parameters.add(p);
    }

    protected String compileCondition() {
        if (!hasWhere) return "";
        if (whereClause.contains(" AND ") || whereClause.contains(" OR ")) {
            return "WHERE (" + whereClause + ")";
        }
        return "WHERE " + whereClause;
    }

    @Override
    public String toString() {
        if (table == null) throw new IllegalStateException("Table must be specified");
        return String.format("DELETE FROM %s %s", table, compileCondition()).trim() + ";";
    }
}
