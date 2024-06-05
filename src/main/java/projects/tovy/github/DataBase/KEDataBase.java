package projects.tovy.github.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KEDataBase {
    private static final String DB_URL = "jdbc:sqlite:killeffects.db";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createTable() {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement("CREATE TABLE IF NOT EXISTS player_effects (player_uuid TEXT PRIMARY KEY, totem_enabled BOOLEAN, bleed_enabled BOOLEAN, rage_enabled BOOLEAN, love_enabled BOOLEAN)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleEffect(String playerUUID, String columnName) throws SQLException {
        String sql = "UPDATE player_effects SET " + columnName + " = NOT " + columnName + " WHERE player_uuid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerUUID);
            pstmt.executeUpdate();
        }
    }

    public boolean isEffectEnabled(String playerUUID, String columnName) throws SQLException {
        String sql = "SELECT " + columnName + " FROM player_effects WHERE player_uuid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerUUID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(columnName);
            }
        }
        return false;
    }
}
