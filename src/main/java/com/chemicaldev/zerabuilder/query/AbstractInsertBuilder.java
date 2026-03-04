package com.chemicaldev.zerabuilder.query;

import com.chemicaldev.zerabuilder.query.interfaces.InsertBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractInsertBuilder implements InsertBuilder {

    protected String table;
    protected String[] columns;

    @Override
    public InsertBuilder into(String table) {
        this.table = table;
        return this;
    }

    @Override
    public InsertBuilder columns(String... columns) {
        this.columns = columns;
        return this;
    }
}
