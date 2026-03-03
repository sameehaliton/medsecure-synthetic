package com.medsecure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    // CWE-798: hardcoded database password in Java source
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/medsecure";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "MedSecure2024!";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
