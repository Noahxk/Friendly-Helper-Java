package com.noahxk.database;

import java.sql.*;

public final class InventoryDataGateway {
    private static Connection database = null;

    public static void initialiseGateway(Connection databaseConnection) {
        database = databaseConnection;

        createDatabaseTable();
    }

    private static void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS inventories (id STRING, itemType STRING, quantity TINYINT)";

        try(Statement stmt = database.createStatement()) {
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("Error creating table inventories: " + e);
        }
    }

    public static void insertRow(String userID, String itemType, int quantity) {
        String sql = "INSERT OR IGNORE INTO inventories (id, itemType, quantity) VALUES (?, ?, ?)";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setString(2, itemType);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println("Error inserting row to inventories: " + e);
        }
    }

    public static boolean rowExists(String userID, String itemType) {
        String sql = "SELECT 1 FROM inventories WHERE id = ? AND itemType = ? LIMIT 1";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setString(2, itemType);

            ResultSet result = stmt.executeQuery();

            return result.next();
        } catch(SQLException e) {
            System.out.println("Error selecting inventory: " + e);
            return false;
        }
    }
}
