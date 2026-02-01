package com.chemicaldev.zerabuilder.db;

import com.chemicaldev.zerabuilder.dsl.ExecutableDSL;
import com.chemicaldev.zerabuilder.dsl.ExecutionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZeraExecutor implements AutoCloseable {

    private final ZeraConnection connection;
    private boolean autoCommit = true;

    public ZeraExecutor(ZeraConnection connection) throws SQLException {
        this.connection = connection;
        this.autoCommit = connection.getAutoCommit();
    }

    /* -----------------------------
       SELECT
     ----------------------------- */

    public <T> List<T> query(ExecutableDSL dsl, RowMapper<T> mapper)
            throws SQLException {

        if (dsl.type() != ExecutionType.QUERY) {
            throw new IllegalStateException("DSL is not a query");
        }

        try (PreparedStatement ps = prepare(dsl);
             ResultSet rs = ps.executeQuery()) {

            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapper.map(rs));
            }
            return result;
        }
    }

    /* -----------------------------
       INSERT / UPDATE / DELETE / DDL
     ----------------------------- */

    public int update(ExecutableDSL dsl) throws SQLException {

        if (dsl.type() != ExecutionType.UPDATE) {
            throw new IllegalStateException("DSL is not an update");
        }

        try (PreparedStatement ps = prepare(dsl)) {
            return ps.executeUpdate();
        }
    }

    /* -----------------------------
       Transactions
     ----------------------------- */

    public void begin() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(autoCommit);
    }

    public void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(autoCommit);
    }

    /* -----------------------------
       Internal helpers
     ----------------------------- */

    private PreparedStatement prepare(ExecutableDSL dsl) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(dsl);
        bind(ps, dsl.getParameters());
        return ps;
    }

    private void bind(PreparedStatement ps, Object[] params) throws SQLException {
        if (params == null || params.length == 0) return;
        System.out.println(params.length);
        for(int i = 0; i < params.length; i++){
            ps.setObject(i + 1, params[i]);
        }
    }

    @Override
    public void close() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
        }
    }
}
