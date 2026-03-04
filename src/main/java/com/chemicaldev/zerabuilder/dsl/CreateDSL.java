package com.chemicaldev.zerabuilder.dsl;

import com.chemicaldev.zerabuilder.dsl.datatype.DataType;
import com.chemicaldev.zerabuilder.main.SQLDialect;
import com.chemicaldev.zerabuilder.main.ZeraBuilder;
import com.chemicaldev.zerabuilder.query.AbstractCreateBuilder;
import com.chemicaldev.zerabuilder.query.ColumnBuilder;
import com.chemicaldev.zerabuilder.query.mysql.MySQLCreateBuilder;
import com.chemicaldev.zerabuilder.query.sqlite.SQLiteCreateBuilder;

public class CreateDSL implements ExecutableDSL {

    private final AbstractCreateBuilder builder;

    public CreateDSL(ZeraBuilder instance) {
        this.builder = switch (instance.getDialect()) {
            case MYSQL -> new MySQLCreateBuilder();
            case SQLITE -> new SQLiteCreateBuilder();
            case POSTRESQL -> throw new UnsupportedOperationException("PostgreSQL not implemented yet");
        };
    }

    public CreateDSL table(String tableName) {
        builder.table(tableName);
        return this;
    }

    public ColumnBuilder column(String name) {
        return builder.column(name);
    }

    public String build() {
        return builder.toString();
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }

    @Override
    public ExecutionType type() {
        return ExecutionType.UPDATE;
    }
}