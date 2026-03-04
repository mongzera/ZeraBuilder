package com.chemicaldev.zerabuilder.query.sqlite;

import com.chemicaldev.zerabuilder.query.AbstractSelectBuilder;

/**
 * SQLite implementation of SELECT. All logic is inherited from
 * {@link AbstractSelectBuilder} — SQLite supports standard SELECT syntax
 * including LIMIT and OFFSET.
 */
public class SQLiteSelectBuilder extends AbstractSelectBuilder {
    // No SQLite-specific overrides needed.
}
