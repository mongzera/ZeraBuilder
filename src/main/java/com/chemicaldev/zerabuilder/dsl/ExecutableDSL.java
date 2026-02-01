package com.chemicaldev.zerabuilder.dsl;

public interface ExecutableDSL {
    String toString();        // SQL
    Object[] getParameters();     // parameters
    ExecutionType type();     // QUERY or UPDATE
}
