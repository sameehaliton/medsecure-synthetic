package com.medsecure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    public Connection getConnection() throws SQLException {
        // CWE-798: hardcoded credential passed as literal directly to JDBC API
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/medsecure",
            "admin",
            "MedSecure2024!"
        );
    }
}
