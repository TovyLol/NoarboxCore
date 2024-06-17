package projects.tovy.github.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KEDataBase {
    private static final String DB_URL = "jdbc:sqlite:killeffects.db";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createTable() {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS player_effects (" +
                             "player_uuid TEXT PRIMARY KEY, " +
                             "totem_enabled BOOLEAN, " +
                             "bleed_enabled BOOLEAN, " +
                             "rage_enabled BOOLEAN, " +
                             "love_enabled BOOLEAN, " +
                             "sword_enabled BOOLEAN)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSingleEffect(String playerUUID, String columnName) throws SQLException {
        String disableAllSql = "UPDATE player_effects SET totem_enabled = FALSE, bleed_enabled = FALSE, " +
                "rage_enabled = FALSE, love_enabled = FALSE, sword_enabled = FALSE WHERE player_uuid = ?";
        String enableEffectSql = "UPDATE player_effects SET " + columnName + " = TRUE WHERE player_uuid = ?";
        try (Connection conn = getConnection();
             PreparedStatement disableStmt = conn.prepareStatement(disableAllSql);
             PreparedStatement enableStmt = conn.prepareStatement(enableEffectSql)) {
            disableStmt.setString(1, playerUUID);
            disableStmt.executeUpdate();
            enableStmt.setString(1, playerUUID);
            enableStmt.executeUpdate();
        }
    }

    public boolean isEffectEnabled(String playerUUID, String columnName) throws SQLException {
        String sql = "SELECT " + columnName + " FROM player_effects WHERE player_uuid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerUUID);
            return pstmt.executeQuery().next();
        }
    }
}