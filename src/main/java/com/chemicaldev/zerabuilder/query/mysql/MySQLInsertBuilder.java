package com.chemicaldev.zerabuilder.query.mysql;

import com.chemicaldev.zerabuilder.query.AbstractInsertBuilder;
import com.chemicaldev.zerabuilder.query.interfaces.InsertBuilder;

import java.util.ArrayList;
import java.util.List;

public class MySQLInsertBuilder extends AbstractInsertBuilder {

    @Override
    public String toString(){
        if(table == null || columns == null || columns.length == 0){
            throw new IllegalStateException("Table and columns must be specified");
        }

        String placeholders = String.join(
                ", ",
                java.util.Collections.nCopies(columns.length, "?")
        );

        return String.format(
                "INSERT INTO %s (%s) VALUES (%s);",
                table,
                String.join(", ", columns),
                placeholders.toString()
        );


    }

}
