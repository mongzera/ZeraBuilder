package com.chemicaldev.zerabuilder.main;

import com.chemicaldev.zerabuilder.dsl.*;
import com.chemicaldev.zerabuilder.dsl.datatype.Datatypes;

public class ZeraBuilder {
    public SQLDialect dialect = SQLDialect.MYSQL;
    private SelectDSL selectDSL = new SelectDSL(this);
    private CreateDSL createDSL = new CreateDSL(this);
    private UpdateDSL updateDSL = new UpdateDSL(this);
    private InsertDSL insertDSL = new InsertDSL(this);
    private DeleteDSL deleteDSL = new DeleteDSL(this);
    private AlterDSL  alterDSL  = new AlterDSL (this);

    public ZeraBuilder(SQLDialect dialect){
        this.dialect = dialect;
        this.type = new Datatypes(this);
    }

    public SelectDSL list (String... cols){
        return selectDSL.list(cols);
    }

    public CreateDSL makeTable(String tableName){
        return createDSL.table(tableName);
    }

    public InsertDSL insertTo(String tableName){
        return insertDSL.into(tableName);
    }

    public DeleteDSL deleteFrom(String tableName){
        return deleteDSL.from(tableName);
    }

    public AlterDSL alterTable(String tableName){
        return alterDSL.table(tableName);
    }

    public Datatypes type = new Datatypes(this);
}