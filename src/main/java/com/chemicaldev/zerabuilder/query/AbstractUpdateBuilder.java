package com.chemicaldev.zerabuilder.query;

import com.chemicaldev.zerabuilder.query.interfaces.UpdateBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base for UPDATE builders. Shared logic across all dialects.
 */
public abstract class AbstractUpdateBuilder implements UpdateBuilder {

    protected String table;
    protected final List<String> setClauses = new ArrayList<>();
    protected String whereClause;
    protected boolean hasWhere = false;

    // SET params come first, WHERE params come after — maintained in two lists
    // then merged in getParameters()
    protected final List<Object> setParams = new ArrayList<>();
    protected final List<Object> whereParams = new ArrayList<>();

    @Override
    public AbstractUpdateBuilder update(String table) {
        this.table = table;
        return this;
    }

    @Override
    public AbstractUpdateBuilder set(String column, Object value) {
        setClauses.add(column + " = ?");
        setParams.add(value);
        return this;
    }

    @Override
    public AbstractUpdateBuilder where(String condition, Object... params) {
        this.whereClause = condition;
        this.hasWhere = true;
        addWhereParams(params);
        return this;
    }

    @Override
    public AbstractUpdateBuilder and(String condition, Object... params) {
        if (!hasWhere) throw new IllegalStateException("Cannot call 'and' before 'where'");
        this.whereClause += " AND " + condition;
        addWhereParams(params);
        return this;
    }

    @Override
    public AbstractUpdateBuilder or(String condition, Object... params) {
        if (!hasWhere) throw new IllegalStateException("Cannot call 'or' before 'where'");
        this.whereClause += " OR " + condition;
        addWhereParams(params);
        return this;
    }

    private void addWhereParams(Object... params) {
        for (Object p : params) whereParams.add(p);
    }

    @Override
    public Object[] getParameters() {
        List<Object> all = new ArrayList<>(setParams);
        all.addAll(whereParams);
        return all.toArray();
    }

    @Override
    public String toString() {
        if (table == null || setClauses.isEmpty()) {
            throw new IllegalStateException("Table and at least one SET clause must be specified");
        }
        String where = hasWhere ? " WHERE " + whereClause : "";
        return String.format("UPDATE %s SET %s%s;",
                table, String.join(", ", setClauses), where);
    }
}
