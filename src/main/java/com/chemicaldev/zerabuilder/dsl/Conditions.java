package com.chemicaldev.zerabuilder.dsl;

public class Conditions {
    public static Condition eq(String col, Object val) { return new SimpleCondition(col + " = ?", val); }
    public static Condition gt(String col, Object val) { return new SimpleCondition(col + " > ?", val); }
    public static Condition lt(String col, Object val) { return new SimpleCondition(col + " < ?", val); }
    public static Condition and(Condition... conds) { return new LogicalCondition("AND", conds); }
    public static Condition or(Condition... conds) { return new LogicalCondition("OR", conds); }
}