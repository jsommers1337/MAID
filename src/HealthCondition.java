/**
 * File Name: HealthCondition.java
 * Author: John Sommers
 * Purpose: This file contains the clas information for HealthCondition.
 * Due Date: 11/22/2023
 */

import java.sql.*;
import java.time.LocalDateTime;

public class HealthCondition {
    private LocalDateTime onset;
    private String name;
    private boolean isChronic;
    private LocalDateTime dateFirstReported;
    private boolean isMedicated;
    private String medicineName;

    // Constructor for HealthCondition class
    public HealthCondition(LocalDateTime onset, String name, boolean isChronic, LocalDateTime dateFirstReported, boolean isMedicated, String medicineName) {
        this.onset = onset;
        this.name = name;
        this.isChronic = isChronic;
        this.dateFirstReported = dateFirstReported;
        this.isMedicated = isMedicated;
        this.medicineName = medicineName;
    }

    // Getter methods for instance variables (no setters to keep HealthCondition immutable)
    public LocalDateTime getOnset() {
        return onset;
    }

    public String getName() {
        return name;
    }

    public boolean isChronic() {
        return isChronic;
    }

    public LocalDateTime getDateFirstReported() {
        return dateFirstReported;
    }

    public boolean isMedicated() {
        return isMedicated;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void commitToDatabase(int userID) {
        try {
            String sql = "INSERT INTO HealthRecords VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(SQL.JDBC_URL, SQL.USERNAME, SQL.PASSWORD);
                 PreparedStatement prepStmt = conn.prepareStatement(sql)) {
                prepStmt.setInt(1, userID);
                prepStmt.setTimestamp(2, Timestamp.valueOf(onset));
                prepStmt.setString(3, name);
                prepStmt.setBoolean(4, isChronic);
                prepStmt.setTimestamp(5, Timestamp.valueOf(dateFirstReported));
                prepStmt.setBoolean(6, isMedicated);
                prepStmt.setString(7, medicineName);
                prepStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveFromDatabase(int userID) {
        try {
            String sql = "SELECT * FROM HealthRecords WHERE UserID = ?";
            try (Connection conn = DriverManager.getConnection(SQL.JDBC_URL, SQL.USERNAME, SQL.PASSWORD);
                 PreparedStatement prepStmt = conn.prepareStatement(sql)) {
                prepStmt.setInt(1, userID);
                ResultSet resultSet = prepStmt.executeQuery();

                if (resultSet.next()) {
                    // Update HealthCondition attributes based on the database values
                    this.onset = resultSet.getTimestamp("Onset").toLocalDateTime();
                    this.name = resultSet.getString("DiseaseName");
                    this.isChronic = resultSet.getBoolean("IsChronic");
                    this.dateFirstReported = resultSet.getTimestamp("DateReported").toLocalDateTime();
                    this.isMedicated = resultSet.getBoolean("IsMedicated");
                    this.medicineName = resultSet.getString("MedicineName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
