package projects.tovy.github.DataBase;


import projects.tovy.github.PlayerUsage.Warps.Warp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarpDatabase {

    private static final String DB_URL = "jdbc:sqlite:warps.db"; //path

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS warps ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "world TEXT NOT NULL, "
                + "x DOUBLE NOT NULL, "
                + "y DOUBLE NOT NULL, "
                + "z DOUBLE NOT NULL, "
                + "pitch FLOAT NOT NULL, "
                + "yaw FLOAT NOT NULL"
                + ");";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Table 'warps' created successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertWarp(Warp warp) {
        String sql = "INSERT INTO warps(name, world, x, y, z, pitch, yaw) VALUES(?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, warp.getName());
            pstmt.setString(2, warp.getWorld());
            pstmt.setDouble(3, warp.getX());
            pstmt.setDouble(4, warp.getY());
            pstmt.setDouble(5, warp.getZ());
            pstmt.setFloat(6, warp.getPitch());
            pstmt.setFloat(7, warp.getYaw());
            pstmt.executeUpdate();
            System.out.println("Warp inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Warp getWarpByName(String warpName) {
        String sql = "SELECT name, world, x, y, z, pitch, yaw FROM warps WHERE name = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, warpName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String world = rs.getString("world");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                float pitch = rs.getFloat("pitch");
                float yaw = rs.getFloat("yaw");
                return new Warp(name, world, x, y, z, pitch, yaw);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Warp> getAllWarps() {
        List<Warp> warps = new ArrayList<>();
        String sql = "SELECT name, world, x, y, z, pitch, yaw FROM warps;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                String world = rs.getString("world");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                float pitch = rs.getFloat("pitch");
                float yaw = rs.getFloat("yaw");
                warps.add(new Warp(name, world, x, y, z, pitch, yaw));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return warps;
    }

    public boolean warpExists(String warpName) {
        String sql = "SELECT 1 FROM warps WHERE name = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, warpName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public void deleteWarp(String warpName) {
        String sql = "DELETE FROM warps WHERE name = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, warpName);
            pstmt.executeUpdate();
            System.out.println("Warp deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
