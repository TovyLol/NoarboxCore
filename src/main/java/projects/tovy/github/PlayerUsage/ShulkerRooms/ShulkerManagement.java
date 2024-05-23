package projects.tovy.github.PlayerUsage.ShulkerRooms;

import org.bukkit.Location;
import projects.tovy.github.DataBase.ShulkerDataBase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ShulkerManagement {

    private final HashMap<Integer, Location> rooms = new HashMap<>();
    private final Set<Integer> occupiedRooms = new HashSet<>();
    private final ShulkerDataBase shulkerDataBase;

    public ShulkerManagement(ShulkerDataBase shulkerDataBase) {
        this.shulkerDataBase = shulkerDataBase;
    }

    public HashMap<Integer, Location> getRooms() {
        return rooms;
    }

    public Location getRoom(int roomId) {
        return rooms.get(roomId);
    }

    public boolean isRoomOccupied(int roomId) {
        return occupiedRooms.contains(roomId);
    }

    public void setRoom(Location location, int roomId) {
        rooms.put(roomId, location);
        shulkerDataBase.insertRoom(roomId, location);
    }

    public void setRoomOccupied(int roomId, boolean occupied) {
        if (occupied) {
            occupiedRooms.add(roomId);
        } else {
            occupiedRooms.remove(roomId);
        }
    }

    public boolean removeRoom(int roomId) {
        if (rooms.containsKey(roomId)) {
            rooms.remove(roomId);
            shulkerDataBase.deleteRoom(roomId);
            return true;
        }
        return false;
    }

    public void removeAllRooms() {
        rooms.clear();
        shulkerDataBase.deleteAllRooms();
    }

    public Set<Integer> getAllRoomIds() {
        return rooms.keySet();
    }

    public void loadRooms() {
        rooms.putAll(shulkerDataBase.loadRooms());
    }

    public void saveRooms() {
        shulkerDataBase.saveRooms(rooms);
    }
}
