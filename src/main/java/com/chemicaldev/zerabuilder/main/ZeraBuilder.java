package com.chemicaldev.zerabuilder.main;

import com.chemicaldev.zerabuilder.dsl.SelectDSL;

public class ZeraBuilder {
    public SQLDialect dialect = SQLDialect.MYSQL;
    public SelectDSL selectDSL = new SelectDSL(this);


    public ZeraBuilder(SQLDialect dialect){
        this.dialect = dialect;
    }

    public SelectDSL list (String... cols){
        return selectDSL.list(cols);
    }
}