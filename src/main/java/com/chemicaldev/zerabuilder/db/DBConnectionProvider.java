package com.chemicaldev.zerabuilder.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnectionProvider {

    // Direct connection for now
    // TODO:: Pooled connection will be implemented in v1.2
    Connection getConnection() throws SQLException;
    void close() throws SQLException;
}
