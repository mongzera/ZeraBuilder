package com.chemicaldev.zerabuilder.main;

import com.chemicaldev.zerabuilder.dsl.*;
import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;

public class ZeraBuilder {

    private SQLDialect dialect = SQLDialect.MYSQL;

    public ZeraBuilder(SQLDialect dialect) {
        this.setDialect(dialect);
    }

    public void setDialect(SQLDialect dialect) {
        this.dialect = dialect;
        this.type = new Datatypes(); // no longer dialect-dependent
    }

    public SQLDialect getDialect() {
        return dialect;
    }


    public CreateDSL create() {
        return new CreateDSL(this);
    }

    public CreateDSL createTable(String tableName) {
        return new CreateDSL(this).table(tableName);
    }

    /* =========================
       OTHER DSL (unchanged)
       ========================= */

    public SelectDSL list(String... cols) {
        return new SelectDSL(this).list(cols);
    }

    public InsertDSL insertTo(String tableName) {
        return new InsertDSL(this).into(tableName);
    }

    public UpdateDSL updateTable(String tableName) {
        return new UpdateDSL(this).table(tableName);
    }

    public DeleteDSL deleteFrom(String tableName) {
        return new DeleteDSL(this).from(tableName);
    }

    public AlterDSL alterTable(String tableName) {
        return new AlterDSL(this).table(tableName);
    }

    public Datatypes type = new Datatypes(); // unchanged for now
}