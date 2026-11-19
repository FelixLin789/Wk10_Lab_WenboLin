package com.example.wk10_lab_wenbolin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class Database {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/comp228_wk10_lab";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "070809";

    private Database() {
    }

    public static Connection getDBConnection() throws SQLException {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL JDBC driver not found.", ex);
        }
        return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
    }
}
