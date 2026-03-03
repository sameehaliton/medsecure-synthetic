package com.medsecure.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserRepository {

    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    // CWE-89: SQL injection — user-supplied userId concatenated directly into query
    public String getUserById(String userId) {
        try {
            Statement stmt = connection.createStatement();
            // Vulnerable: userId is unsanitized input from HTTP request parameter
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM users WHERE id = '" + userId + "'"
            );
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
