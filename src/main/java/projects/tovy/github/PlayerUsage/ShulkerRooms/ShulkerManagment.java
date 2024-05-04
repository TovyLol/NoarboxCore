package projects.tovy.github.PlayerUsage.ShulkerRooms;



import org.bukkit.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ShulkerManagment {
    private final HashMap<Integer, Location> rooms = new HashMap<>();
    private final Set<Integer> occupiedRooms = new HashSet<>();

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
            return true;
        }
        return false;
    }
    public void removeAllRooms() {
        rooms.clear();
    }
}

