package com.chemicaldev.zerabuilder.main;

/**
 * Supported SQL dialects for ZeraBuilder.
 * Pass one of these to {@link ZeraBuilder} to generate dialect-specific SQL.
 */
public enum SQLDialect {
    MYSQL,
    SQLITE,
    POSTGRESQL
}
