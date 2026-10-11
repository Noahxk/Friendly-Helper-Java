package com.noahxk.database;

import java.sql.*;

public final class BalancesDataGateway {
    private static Connection database = null;

    public static void initialiseGateway(Connection databaseConnection) {
        database = databaseConnection;

        createDatabaseTable();
    }

    private static void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS balances (id STRING PRIMARY KEY UNIQUE, balance BIGINT)";

        try(Statement stmt = database.createStatement()) {
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("Error creating table balances: " + e);
        }
    }

    public static void insertRow(String userID, long amount) {
        String sql = "INSERT OR IGNORE INTO balances (id, balance) VALUES (?, ?)";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setLong(2, amount);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting row to balances: " + e);
        }
    }

    public static long getBalance(String userID) {
        String sql = "SELECT balance FROM balances WHERE id = ?";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setString(1, userID);
            ResultSet result = stmt.executeQuery();

            return result.getLong("balance");
        } catch(SQLException e) {
            System.out.println("Error fetching balance: " + e);
            return 0;
        }
    }

    public static void increaseBalance(String userID, long amount) {
        String sql = "UPDATE balances SET balance = balance + ? WHERE id = ?";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setLong(1, amount);
            stmt.setString(2, userID);
            stmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println("Error increasing balance: " + e);
        }
    }

    public static void decreaseBalance(String userID, long amount) {
        String sql = "UPDATE balances SET balance = balance - ? WHERE id = ?";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setLong(1, amount);
            stmt.setString(2, userID);
            stmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println("Error decreasing balance: " + e);
        }
    }

    public static void setBalance(String userID, long amount) {
        String sql = "UPDATE balances SET balance = ? WHERE id = ?";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setLong(1, amount);
            stmt.setString(2, userID);
            stmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println("Error setting balance: " + e);
        }
    }
}
