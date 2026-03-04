package com.chemicaldev.zerabuilder.query.interfaces;

import java.util.List;

public interface InsertBuilder {
    InsertBuilder into(String table);
    InsertBuilder columns(String... columns);
}
