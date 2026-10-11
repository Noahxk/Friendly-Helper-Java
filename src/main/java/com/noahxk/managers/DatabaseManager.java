package com.noahxk.managers;

import com.noahxk.database.BalancesDataGateway;
import com.noahxk.database.InventoryDataGateway;
import com.noahxk.database.NameDataGateway;
import com.noahxk.database.SecurityLevelDataGateway;

import java.sql.*;

public final class DatabaseManager {
    private static final String databaseConnectionURL = "jdbc:sqlite:database.db";
    private static Connection database = null;

    public static void initialiseDatabase() {
        try {
            System.out.println("Connecting to database...");
            database = DriverManager.getConnection(databaseConnectionURL);
            System.out.println("Connection established");

            initialiseDatabaseTablesAndGateways();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e);
        }
    }

    public static void shutdownDatabase() {
        try {
            if(database != null && !database.isClosed()) {
                System.out.println("Closing database connection...");
                database.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e);
        }
    }

    private static void initialiseDatabaseTablesAndGateways() {
        BalancesDataGateway.initialiseGateway(database);
        SecurityLevelDataGateway.intialiseGateway(database);
        InventoryDataGateway.initialiseGateway(database);
        NameDataGateway.initialiseGateway(database);
    }
}
