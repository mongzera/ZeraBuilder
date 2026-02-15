package com.chemicaldev.zerabuilder.query.sqlite;

import com.chemicaldev.zerabuilder.query.CreateBuilder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteCreateBuilder implements CreateBuilder {

    private String table;
    private final List<String> columns = new ArrayList<>();
    private final List<String> constraints = new ArrayList<>();
    private final List<String> triggers = new ArrayList<>();

    public SQLiteCreateBuilder table(String table){
        this.table = table;
        return this;
    }

    public SQLiteCreateBuilder column(String definition){
        columns.add(definition);
        return this;
    }

    @Override
    public CreateBuilder primaryKey(String... columns) {
        constraints.add(
                "PRIMARY KEY (" + String.join(", ", columns) + ")"
        );
        return this;
    }

    @Override
    public CreateBuilder unique(String... columns) {
        constraints.add(
                "UNIQUE (" + String.join(", ", columns) + ")"
        );
        return this;
    }

    @Override
    public SQLiteCreateBuilder timestamps() {
        // Add columns with default
        this.column("created_at DATETIME DEFAULT CURRENT_TIMESTAMP");
        this.column("updated_at DATETIME DEFAULT CURRENT_TIMESTAMP");

        // Add trigger for auto-update
        String triggerSql = String.format(
                "CREATE TRIGGER %s_updated_at AFTER UPDATE ON %s " +
                        "FOR EACH ROW BEGIN " +
                        "UPDATE %s SET updated_at = CURRENT_TIMESTAMP WHERE rowid = OLD.rowid; " +
                        "END;",
                table, table, table
        );

        this.addTrigger(triggerSql);

        return this;
    }

    public SQLiteCreateBuilder addTrigger(String sql){
        triggers.add(sql);
        return this;
    }

    @Override
    public String toString() {
        List<String> all = new ArrayList<>();
        all.addAll(columns);
        all.addAll(constraints);

        // Build CREATE TABLE part
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CREATE TABLE %s (%s);", table, String.join(", ", all)));

        // Append triggers if any
        for (String triggerSql : triggers) {
            sb.append("\n").append(triggerSql);
        }

        return sb.toString();
    }

}
