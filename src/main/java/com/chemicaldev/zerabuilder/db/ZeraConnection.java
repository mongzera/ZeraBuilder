package com.chemicaldev.zerabuilder.db;

import com.chemicaldev.zerabuilder.dsl.ExecutableDSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ZeraConnection implements DBConnectionProvider, AutoCloseable{
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public ZeraConnection(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.getConnection();
    }


    @Override
    public Connection getConnection() throws SQLException {
        if(connection != null && !connection.isClosed()) return connection;

        if (username != null && password != null) {
            connection =  DriverManager.getConnection(url, username, password);
        } else {
            connection = DriverManager.getConnection(url);
        }

        return connection;
    }

    public PreparedStatement prepareStatement(ExecutableDSL dsl) throws SQLException {
       return connection.prepareStatement(dsl.toString());
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public boolean isClosed() throws SQLException {
        return connection.isClosed();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
