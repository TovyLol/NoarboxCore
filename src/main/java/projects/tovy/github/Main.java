package projects.tovy.github;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import projects.tovy.github.Administration.Staff.Logging.CommandLogging;
import projects.tovy.github.Administration.Staff.Mode.ModeCommands;
import projects.tovy.github.Administration.Staff.Mode.ModeEvents;
import projects.tovy.github.Administration.Staff.Mode.ModeMain;
import projects.tovy.github.DataBase.DatabaseManager;
import projects.tovy.github.PlayerUsage.DeathStashes.DsEvents;
import projects.tovy.github.PlayerUsage.DeathStashes.DsMain;
import projects.tovy.github.PlayerUsage.DeathStashes.StashCommand;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerCommands;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerEvents;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerListener;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerManagement;
import projects.tovy.github.PlayerUsage.Warps.WarpCMD;
import projects.tovy.github.PlayerUsage.Warps.WarpManager;
import projects.tovy.github.ServerUsage.Chat.ChatFilter;
import projects.tovy.github.ServerUsage.Chat.DeathMessages;
import projects.tovy.github.ServerUsage.Chat.JoinMessages;
import projects.tovy.github.ServerUsage.KillEffects.Effects;
import projects.tovy.github.ServerUsage.KillEffects.GUI;
import projects.tovy.github.ServerUsage.KillEffects.KeEvents;
import projects.tovy.github.ServerUsage.KillEffects.KeMain;
import projects.tovy.github.ServerUsage.Shops.GetShops;
import projects.tovy.github.ServerUsage.Shops.ShopsMain;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class Main extends JavaPlugin {
    /*
    to do list:
    - finish security in Administration/Staff/Logging
    - test Killeffects
    - test DeathStashes
    - Fix Join and Leave message issue
    - Fix ShulkerRoom Command (PRIO)
    - StaffMode
    - NameTags
    */

    private static Main instance;
    private GetShops get;
    private ShulkerManagement sm;
    private DsMain dsMain;
    private DatabaseManager dbManager;
    private FileConfiguration cnfg;
    private ItemHandeling item;
    private EasyGuiBorder border;
    private KeMain killeffects;
    private ModeMain modeMain;
    private Effects effects;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        cnfg = getConfig();
        dbManager = new DatabaseManager();
        dbManager.getWarpDatabase().createTable();
        sm = new ShulkerManagement(dbManager.getShulkerDataBase()); // Initialize sm
        sm.loadRooms(); // Move this line after initializing sm
        dbManager.getShulkerDataBase().createTable();
        dbManager.getDsDataBase().createTable();
        dbManager.getKEDatabase().createTable();
        dbManager.getKEDatabase().addSwordEnabledColumnIfMissing(); // Ensure column exists
        dsMain = new DsMain();
        border = new EasyGuiBorder();
        modeMain = new ModeMain(this); // Pass the instance of Main to ModeMain
        loadCommands();
        loadEvents();
        getLogger().info("T komt goed core is enabled");
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
        getCommand("togglestash").setExecutor(new StashCommand(dsMain, cnfg, this));
        getCommand("killeffects").setExecutor(new GUI(border, dbManager.getKEDatabase()));
        getCommand("staffmode").setExecutor(new ModeCommands());
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new ShulkerListener(this, cnfg, sm), this);
        getServer().getPluginManager().registerEvents(new ShulkerEvents(this, cnfg, sm), this);
        getServer().getPluginManager().registerEvents(new DeathMessages(), this);
        getServer().getPluginManager().registerEvents(new JoinMessages(), this);
        getServer().getPluginManager().registerEvents(new ChatFilter(this), this);
        getServer().getPluginManager().registerEvents(new ShopsMain(get), this);
        getServer().getPluginManager().registerEvents(new DsEvents(this, dbManager.getDsDataBase(), cnfg, item), this);
        getServer().getPluginManager().registerEvents(new GUI(border, dbManager.getKEDatabase()), this);
        getServer().getPluginManager().registerEvents(new KeEvents(dbManager.getKEDatabase(), effects), this);

        // Create an instance of ModeEvents and register its events
        ModeEvents modeEvents = new ModeEvents(modeMain, this);
        getServer().getPluginManager().registerEvents(modeEvents, this);

        getServer().getPluginManager().registerEvents(new CommandLogging(this, modeMain), this);
    }

    public void noPermission(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage(cnfg.getString("prefix") + ChatColor.WHITE + "You don't have permission to use this command.");
        } else {
            sender.sendMessage(cnfg.getString("prefix") + ChatColor.WHITE + "This command can only be used by players.");
        }
    }

    public void errorMessage(CommandSender sender) {
        sender.sendMessage(cnfg.getString("prefix") + ChatColor.RED + "An error occured.");
    }

    public void sendToPermission(String message, String perms, Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(perms)) {
                player.sendMessage(cnfg.getString("prefix") + message);
                player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 1);
            }
        }
    }

    public void sendTitle(Player p, String title, String subtitle) {
        p.sendTitle(title, subtitle, 20, 20, 20);

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
                player.sendMessage(cnfg.getString("prefix") + " &fYou got teleported to spawn");
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

    public void sendWebHook(String URL, String msg, String nameofwebhook, String title, String error) {
        try {
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String payload = "{\"username\":\"" + title + "\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + msg + "}]}";
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(payload);
            wr.flush();
            wr.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                getLogger().info(error);
            } else {
                getLogger().info("Failed to send webhook. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playWarningSound(Player p, Location location) {
        p.playSound(location, Sound.ENTITY_WITHER_DEATH, 1, 1);
    }

    @Override
    public void onDisable() {
        sm.saveRooms();
        getLogger().info("'het komt wel goed trust the process' -tovy");
    }
}