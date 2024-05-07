package projects.tovy.github.PlayerUsage.Warps;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class WarpManager {
    private Map<String, Location> warps;

    public WarpManager() {
        this.warps = new HashMap<>();
    }

    public void setWarp(String warpName, Location location) {
        warps.put(warpName.toLowerCase(), location);
    }

    public void deleteWarp(String warpName) {
        warps.remove(warpName.toLowerCase());
    }

    public void listWarps(Player player) {
        player.sendMessage(ChatColor.GREEN + "Available Warps:");
        for (String warpName : warps.keySet()) {
            player.sendMessage(ChatColor.YELLOW + "- " + warpName);
        }
    }

    public boolean warpExists(String warpName) {
        return warps.containsKey(warpName.toLowerCase());
    }

    public Location getWarpLocation(String warpName) {
        return warps.get(warpName.toLowerCase());
    }
}
