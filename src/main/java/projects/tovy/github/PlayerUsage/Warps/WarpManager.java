package projects.tovy.github.PlayerUsage.Warps;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import projects.tovy.github.Main;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class WarpManager {
    private final Main main;
    private final File warpFile;
    private final FileConfiguration warpConfig;

    public WarpManager(Main main) {
        this.main = main;
        this.warpFile = new File(main.getDataFolder(), "warps.yml");
        this.warpConfig = YamlConfiguration.loadConfiguration(warpFile);
        createWarpFile();
    }

    private void createWarpFile() {
        if (!warpFile.exists()) {
            main.saveResource("warps.yml", false);
        }
    }

    public void setWarp(String warpName, Location location) {
        warpConfig.set("warps." + warpName + ".world", location.getWorld().getName());
        warpConfig.set("warps." + warpName + ".x", location.getX());
        warpConfig.set("warps." + warpName + ".y", location.getY());
        warpConfig.set("warps." + warpName + ".z", location.getZ());
        warpConfig.set("warps." + warpName + ".yaw", location.getYaw());
        warpConfig.set("warps." + warpName + ".pitch", location.getPitch());

        saveWarpFile();
    }

    public Location getWarp(String warpName) {
        if (!warpConfig.contains("warps." + warpName)) {
            return null;
        }

        String worldName = warpConfig.getString("warps." + warpName + ".world");
        double x = warpConfig.getDouble("warps." + warpName + ".x");
        double y = warpConfig.getDouble("warps." + warpName + ".y");
        double z = warpConfig.getDouble("warps." + warpName + ".z");
        float yaw = (float) warpConfig.getDouble("warps." + warpName + ".yaw");
        float pitch = (float) warpConfig.getDouble("warps." + warpName + ".pitch");

        return new Location(main.getServer().getWorld(worldName), x, y, z, yaw, pitch);
    }

    public boolean warpExists(String warpName) {
        return warpConfig.contains("warps." + warpName);
    }

    public void deleteWarp(String warpName) {
        warpConfig.set("warps." + warpName, null);
        saveWarpFile();
    }

    public void listWarps(Player player) {
        Set<String> warpNames = warpConfig.getConfigurationSection("warps").getKeys(false);
        if (warpNames.isEmpty()) {
            player.sendMessage("No warp points available.");
        } else {
            player.sendMessage("Available warp points:");
            for (String warpName : warpNames) {
                player.sendMessage("- " + warpName);
            }
        }
    }

    private void saveWarpFile() {
        try {
            warpConfig.save(warpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
