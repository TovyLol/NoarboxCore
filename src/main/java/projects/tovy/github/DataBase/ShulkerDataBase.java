package projects.tovy.github.DataBase;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerRoom;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ShulkerDataBase {



    private static final String DB_URL = "jdbc:sqlite:rooms.db";
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS shulker_rooms (" +
                "id INTEGER PRIMARY KEY," +
                "world TEXT NOT NULL," +
                "x REAL NOT NULL," +
                "y REAL NOT NULL," +
                "z REAL NOT NULL," +
                "occupied INTEGER NOT NULL" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRoom(int id, Location location) {
        String sql = "INSERT INTO shulker_rooms(id, world, x, y, z, occupied) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, location.getWorld().getName());
            pstmt.setDouble(3, location.getX());
            pstmt.setDouble(4, location.getY());
            pstmt.setDouble(5, location.getZ());
            pstmt.setBoolean(6, false); // Assuming newly created rooms are not occupied
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoom(int id) {
        String sql = "DELETE FROM shulker_rooms WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllRooms() {
        String sql = "DELETE FROM shulker_rooms";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Location> loadRooms() {
        Map<Integer, Location> rooms = new HashMap<>();
        String sql = "SELECT * FROM shulker_rooms";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String world = rs.getString("world");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                boolean occupied = rs.getBoolean("occupied");

                Location location = new Location(Bukkit.getWorld(world), x, y, z);
                rooms.put(id, location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public void saveRooms(Map<Integer, Location> rooms) {
        deleteAllRooms(); // Clear existing data

        for (Map.Entry<Integer, Location> entry : rooms.entrySet()) {
            insertRoom(entry.getKey(), entry.getValue());
        }
    }
}
