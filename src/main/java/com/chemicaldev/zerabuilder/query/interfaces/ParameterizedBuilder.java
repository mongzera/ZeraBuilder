package com.chemicaldev.zerabuilder.query.interfaces;

/**
 * Extends {@link Builder} with the ability to return bound prepared-statement parameters.
 */
public interface ParameterizedBuilder extends Builder {

    /**
     * Returns the ordered array of parameter values that correspond to the
     * {@code ?} placeholders in the generated SQL.
     *
     * @return bound parameters, never {@code null}
     */
    Object[] getParameters();
}
