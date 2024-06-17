// File: projects/tovy/github/PlayerUsage/Warps/WarpManager.java
package projects.tovy.github.PlayerUsage.Warps;

import org.bukkit.entity.Player;
import projects.tovy.github.DataBase.WarpDatabase;
import org.bukkit.Location;
import java.util.ArrayList;
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

    public List<Warp> getAllWarps() {
        return warpDatabase.getAllWarps();
    }

    public List<WarpStatus> getWarpsWithStatus(Player player) {
        List<Warp> warps = warpDatabase.getAllWarps();
        List<WarpStatus> warpStatuses = new ArrayList<>();

        for (Warp warp : warps) {
            boolean unlocked = player.hasPermission(warp.getName() + ".noarbox.usage");
            warpStatuses.add(new WarpStatus(warp, unlocked));
        }

        return warpStatuses;
    }

    public boolean teleportToWarp(Player player, String warpName) {
        Warp warp = warpDatabase.getWarpByName(warpName);
        if (warp != null) {
            player.teleport(warp.getLocation());
            return true;
        }
        return false;
    }

    public static class WarpStatus {
        private final Warp warp;
        private final boolean unlocked;

        public WarpStatus(Warp warp, boolean unlocked) {
            this.warp = warp;
            this.unlocked = unlocked;
        }

        public Warp getWarp() {
            return warp;
        }

        public boolean isUnlocked() {
            return unlocked;
        }
    }
}
