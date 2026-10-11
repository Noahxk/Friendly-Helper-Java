package com.noahxk.database;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.sql.*;

public final class SecurityLevelDataGateway {
    private static Connection database = null;

    public static void intialiseGateway(Connection databaseConnection) {
        database = databaseConnection;

        createDatabaseTable();
    }

    private static void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS securitylevels (id STRING PRIMARY KEY UNIQUE, level INT)";

        try(Statement stmt = database.createStatement()) {
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("Error creating table securityLevels: " + e);
        }
    }

    public static void insertRow(String userID, int level) {
        String sql = "INSERT OR IGNORE INTO securitylevels (id, level) VALUES (?, ?)";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setInt(2, level);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting row to securityLevels: " + e);
        }
    }

    public static int getSecurityLevel(String userID) {
        String sql = "SELECT level FROM securitylevels WHERE id = ?";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setString(1, userID);
            ResultSet result = stmt.executeQuery();

            return result.getInt("level");
        } catch(SQLException e) {
            System.out.println("Error fetching security level: " + e);
            return 0;
        }
    }

    public static void setSecurityLevel(String userID, int level) {
        String sql = "UPDATE securitylevels SET level = ? WHERE id = ?";

        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setInt(1, level);
            stmt.setString(2, userID);
            stmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println("Error setting security level: " + e);
        }
    }

    public static boolean isSecurityLevelMet(SlashCommandInteractionEvent event, int requiredLevel) {
        if(getSecurityLevel(event.getMember().getId()) >= requiredLevel) {
            return true;
        } else {
            event.reply("Unable to comply: Insufficient security level").queue();
            return false;
        }
    }
}
