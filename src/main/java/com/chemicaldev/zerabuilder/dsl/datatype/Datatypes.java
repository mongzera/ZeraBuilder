package com.chemicaldev.zerabuilder.dsl.datatype;

public class Datatypes {

    public static DataType string(int length) {
        return new StringType(length);
    }

    public static DataType string() {
        return new StringType(255);
    }

    public static DataType integer() {
        return new IntegerType();
    }

    public static DataType date() {
        return new DateType();
    }

    public static DataType datetime() {
        return new DateTimeType();
    }

    public static DataType bool() { return new BooleanType(); }
}