package com.noahxk.database;

import java.sql.*;

public final class NameDataGateway {
    private static Connection database = null;

    public static void initialiseGateway(Connection databaseConnection) {
        database = databaseConnection;

        createDatabaseTable();
    }

    private static void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS names (id STRING PRIMARY KEY UNIQUE, name STRING)";

        try(Statement stmt = database.createStatement()) {
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("Error creating table names: " + e);
        }
    }

    public static void insertRow(String userID, String name) {
        String sql = "INSERT OR IGNORE INTO names (id, name) VALUES (?, ?)";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setString(2, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting row to names: " + e);
        }
    }

    public static String getName(String userID) {
        String sql = "SELECT name FROM names WHERE id = ?";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setString(1, userID);
            ResultSet result = stmt.executeQuery();

            return result.getString("name");
        } catch(SQLException e) {
            System.out.println("Error fetching name: " + e);
            return null;
        }
    }
}
