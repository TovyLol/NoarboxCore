package projects.tovy.github.DataBase;

import java.sql.*;

public class DsDataBase {

    private static final String DB_URL = "jdbc:sqlite:warps.db";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS stashes (" +
                "uuid TEXT PRIMARY KEY," +
                "stash BLOB NOT NULL," +
                "timestamp INTEGER NOT NULL" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveStash(String uuid, byte[] stashData, long timestamp) {
        String sql = "INSERT INTO stashes (uuid, stash, timestamp) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uuid);
            pstmt.setBytes(2, stashData);
            pstmt.setLong(3, timestamp);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeStash(String uuid) {
        String sql = "DELETE FROM stashes WHERE uuid = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uuid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
