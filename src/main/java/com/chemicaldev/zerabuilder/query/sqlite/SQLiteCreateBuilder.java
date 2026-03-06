package com.chemicaldev.zerabuilder.query.sqlite;

import com.chemicaldev.zerabuilder.dsl.table.ColumnDefinition;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.query.AbstractCreateBuilder;

public class SQLiteCreateBuilder extends AbstractCreateBuilder {

    public SQLiteCreateBuilder() {
        super(SQLDialect.SQLITE);
    }

    @Override
    protected String renderColumn(ColumnDefinition col) {

        StringBuilder sb = new StringBuilder();

        sb.append(col.getName())
                .append(" ")
                .append(col.getType().render(dialect));

        if (col.isPrimaryKey()) {
            sb.append(" PRIMARY KEY");
            if (col.isAutoIncrement()) {
                sb.append(" AUTOINCREMENT");
            }
        }

        if (col.isNotNull()) sb.append(" NOT NULL");

        if (col.getDefaultValue() != null)
            sb.append(" DEFAULT ").append(col.getDefaultValue());

        if (col.isUnique()) sb.append(" UNIQUE");

        if(col.getOnUpdateValue() != null){
            sb.append(" ON UPDATE ").append(col.getOnUpdateValue());
        }

        return sb.toString();
    }
}