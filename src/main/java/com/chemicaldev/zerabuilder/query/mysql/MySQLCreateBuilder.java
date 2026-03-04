package com.chemicaldev.zerabuilder.query.mysql;

import com.chemicaldev.zerabuilder.dsl.table.ColumnDefinition;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.query.AbstractCreateBuilder;

public class MySQLCreateBuilder extends AbstractCreateBuilder {

    public MySQLCreateBuilder() {
        super(SQLDialect.MYSQL);
    }

    @Override
    protected String renderColumn(ColumnDefinition col) {

        StringBuilder sb = new StringBuilder();

        sb.append(col.getName())
                .append(" ")
                .append(col.getType().render(dialect));

        if (col.isNotNull()) sb.append(" NOT NULL");
        if (col.isAutoIncrement()) sb.append(" AUTO_INCREMENT");
        if (col.getDefaultValue() != null)
            sb.append(" DEFAULT ").append(col.getDefaultValue());
        if (col.isUnique()) sb.append(" UNIQUE");
        if (col.isPrimaryKey()) sb.append(" PRIMARY KEY");

        return sb.toString();
    }
}