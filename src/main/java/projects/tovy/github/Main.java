package projects.tovy.github;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import projects.tovy.github.Administration.Staff.ScreenShare.ScreenShareCommands;
import projects.tovy.github.DataBase.DatabaseManager;
import projects.tovy.github.PlayerUsage.DeathStashes.DsEvents;
import projects.tovy.github.PlayerUsage.DeathStashes.DsMain;
import projects.tovy.github.PlayerUsage.DeathStashes.StashCommand;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerCommands;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerListener;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerManagement;
import projects.tovy.github.PlayerUsage.Warps.WarpCMD;
import projects.tovy.github.PlayerUsage.Warps.WarpManager;
import projects.tovy.github.ServerUsage.Chat.ChatFilter;
import projects.tovy.github.ServerUsage.Chat.DeathMessages;
import projects.tovy.github.ServerUsage.Chat.JoinMessages;
import projects.tovy.github.ServerUsage.Shops.GetShops;
import projects.tovy.github.ServerUsage.Shops.ShopsMain;

public final class Main extends JavaPlugin {
    private static Main instance;
    private GetShops get;
    private ShulkerManagement sm;
    private DsMain dsMain;
    private DatabaseManager dbManager;
    private FileConfiguration cnfg;
    private ItemHandeling item;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        cnfg = getConfig();

        dbManager = new DatabaseManager();
        dbManager.getWarpDatabase().createTable();
        dbManager.getShulkerDataBase().createTable();
        dbManager.getDsDataBase().createTable();

        dsMain = new DsMain();

        loadCommands();
        loadEvents();

        getLogger().info("Plugin has been enabled");
    }

    public static Main getInstance() {
        return instance;
    }

    public static FileConfiguration getPluginConfig() {
        return getInstance().getConfig();
    }

    private void loadCommands() {
        WarpManager warpManager = new WarpManager(dbManager.getWarpDatabase());
        getCommand("shulker").setExecutor(new ShulkerCommands(sm, this, cnfg));
        getCommand("warp").setExecutor(new WarpCMD(this, warpManager));
        getCommand("ss").setExecutor(new ScreenShareCommands(this));
        getCommand("togglestash").setExecutor(new StashCommand(dsMain, cnfg, this));
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new ShulkerListener(this, cnfg, sm), this);
        getServer().getPluginManager().registerEvents(new DeathMessages(), this);
        getServer().getPluginManager().registerEvents(new JoinMessages(), this);
        getServer().getPluginManager().registerEvents(new ChatFilter(this), this);
        getServer().getPluginManager().registerEvents(new ShopsMain(get), this);
        getServer().getPluginManager().registerEvents(new DsEvents(this, dsMain, dbManager.getDsDataBase(), item), this);
    }

    public void noPermission(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage("You don't have permission to use this command.");
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
    }

    public void errorMessage(CommandSender sender) {
        sender.sendMessage("&cAn error occurred");
    }

    public void sendToPermission(String message, String perms, Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(perms)) {
                player.sendMessage(cnfg.getString("prefix") + message);
            }
        }
    }
    public void sendTitle(Player p, String title, String subtitle) {
        p.sendTitle(title, subtitle, 20 ,20, 20);
    }

    public void tpToSpawn(Player player) {
        String spawnCoordsString = cnfg.getString("Spawncoords");
        if (spawnCoordsString != null) {
            String[] parts = spawnCoordsString.split(", ");
            if (parts.length == 3) {
                int spawnx = Integer.parseInt(parts[0]);
                int spawny = Integer.parseInt(parts[1]);
                int spawnz = Integer.parseInt(parts[2]);

                Location spawnLocation = new Location(player.getWorld(), spawnx, spawny, spawnz);
                player.teleport(spawnLocation);
                player.sendMessage(cnfg.getString("prefix") + "  &fYou got teleported to spawn");
            } else {
                getLogger().warning("Spawn coordinates are invalid.");
            }
        } else {
            getLogger().warning("Spawn coordinates are not configured.");
        }
    }

    public boolean isPlayerInsideRegion(Player player, String regionName) {
        for (ProtectedRegion region : WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(player.getLocation()))) {
            if (region.getId().equalsIgnoreCase(regionName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onDisable() {
        getLogger().info("'het komt wel goed trust the process' -tovy");
    }
}
