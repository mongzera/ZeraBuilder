package com.chemicaldev.zerabuilder.main;

import com.chemicaldev.zerabuilder.dsl.SelectDSL;
import com.chemicaldev.zerabuilder.query.mysql.SelectBuilder;

public class ZeraBuilder {
    public SQLDialect dialect = SQLDialect.MYSQL;
    public SelectDSL selectDSL = new SelectDSL(this);

    public SelectDSL list (String... cols){
        return selectDSL.list(cols);
    }
}