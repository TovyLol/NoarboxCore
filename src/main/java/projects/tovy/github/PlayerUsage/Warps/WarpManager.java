package projects.tovy.github.PlayerUsage.Warps;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import projects.tovy.github.DataBase.WarpDatabase;


import java.util.List;

public class WarpManager {

    private final WarpDatabase warpDatabase;

    public WarpManager(WarpDatabase warpDatabase) {
        this.warpDatabase = warpDatabase;
    }

    public void setWarp(String name, Location location) {
        Warp warp = new Warp(
                name,
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getPitch(),
                location.getYaw()
        );
        warpDatabase.insertWarp(warp);
    }

    public boolean warpExists(String name) {
        return warpDatabase.warpExists(name);
    }

    public void deleteWarp(String name) {
        warpDatabase.deleteWarp(name);
    }

    public void listWarps(Player player) {
        List<Warp> warps = warpDatabase.getAllWarps();
        player.sendMessage("Warps:");
        for (Warp warp : warps) {
            player.sendMessage("- " + warp.getName());
        }
    }

    public boolean teleportToWarp(Player player, String warpName) {
        Warp warp = warpDatabase.getWarpByName(warpName);
        if (warp != null) {
            player.teleport(warp.getLocation());
            return true;
        }
        return false;
    }
}
