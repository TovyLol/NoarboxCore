package projects.tovy.github.PlayerUsage.ShulkerRooms;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ShulkerRoom {

    private final int id;
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private boolean occupied;

    public ShulkerRoom(int id, String world, double x, double y, double z, boolean occupied) {
        this.id = id;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.occupied = occupied;
    }

    public int getId() {
        return id;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}
