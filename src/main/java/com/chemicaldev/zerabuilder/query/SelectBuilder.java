package com.chemicaldev.zerabuilder.query;

import com.chemicaldev.zerabuilder.query.mysql.MySQLSelectBuilder;

public interface SelectBuilder {
    SelectBuilder select(String... columns);

    SelectBuilder from(String table);

    SelectBuilder join(String... joins);

    SelectBuilder where(String condition, Object... params);

    SelectBuilder and(String condition, Object... params);

    SelectBuilder or(String condition, Object... params);

    SelectBuilder groupBy(String... columns);

    SelectBuilder orderBy(String... columns);

    SelectBuilder limit(int limit);

    Object[] getParameters();
}
