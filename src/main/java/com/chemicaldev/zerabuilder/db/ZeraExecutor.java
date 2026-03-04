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

        try (PreparedStatement ps = connection.prepareStatement(dsl);
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

    public int update(PreparedStatement ps) {

        try{
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* -----------------------------
       Transactions
     ----------------------------- */

    private void begin() throws SQLException {
        connection.setAutoCommit(false);
    }

    private void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(autoCommit);
    }

    private void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(autoCommit);
    }

    public void transaction(TransactionBlock block) {
        try {
            begin(); // disable auto-commit
            block.run(this); // run user code
            commit(); // commit if no exception
        } catch (Exception e) {
            try {
                rollback(); // rollback if anything failed
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed", ex);
            }
            throw new RuntimeException("Transaction failed", e);
        }
    }

    /* -----------------------------
       Internal helpers
     ----------------------------- */


    public void bind(PreparedStatement ps, Object[] params) throws SQLException {
        if (params == null || params.length == 0) return;
        for(int i = 0; i < params.length; i++){
            ps.setObject(i + 1, params[i]);
        }
    }


    public void setBatch(PreparedStatement ps, List<Object[]> batches) throws SQLException {

        this.transaction(batch -> {
            for(Object[] params : batches){
                this.bind(ps, params);
                ps.addBatch();
            }
            ps.executeBatch();
        });

    }
    @Override
    public void close() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

    @FunctionalInterface
    public interface TransactionBlock {
        void run(ZeraExecutor executor) throws Exception;
    }

}
