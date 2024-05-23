package projects.tovy.github;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import projects.tovy.github.Administration.Staff.ScreenShare.ScreenShareCommands;
import projects.tovy.github.DataBase.DatabaseManager;
import projects.tovy.github.DataBase.ShulkerDataBase;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerCommands;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerListener;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerManagement;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerManagment;
import projects.tovy.github.PlayerUsage.Warps.WarpCMD;
import projects.tovy.github.PlayerUsage.Warps.WarpManager;
import projects.tovy.github.ServerUsage.Chat.ChatFilter;
import projects.tovy.github.ServerUsage.Chat.DeathMessages;
import projects.tovy.github.ServerUsage.Chat.JoinMessages;
import projects.tovy.github.ServerUsage.Shops.GetShops;
import projects.tovy.github.ServerUsage.Shops.ShopsMain;


public final class Main extends JavaPlugin {
    // Connections
    private final ShulkerManagement shulkerManagment = new ShulkerManagement(new ShulkerDataBase("shulker_rooms.db"));
    private static Main instance;
    private GetShops get;
    public Main (GetShops get) {
        this.get = get;
    }
    private DatabaseManager dbManager;
    private FileConfiguration cnfg;


    public Main (FileConfiguration cnfg) {
        this.cnfg = cnfg;
    }

    @Override
    public void onEnable() {
        dbManager = new DatabaseManager();
        dbManager.getWarpDatabase().createTable();
        ShulkerDataBase shulkerDatabase = new ShulkerDataBase("shulker_rooms.db");
        shulkerDatabase.createTable();


        shulkerManagment = new ShulkerManagment(shulkerDatabase);
        shulkerManagment.loadRooms();

        instance = this;
        getConfig().options().copyDefaults();
        saveConfig();
        loadCommands();
        loadEvents();
    }

    public static Main getInstance() {
        return instance;
    }
    public static FileConfiguration getPluginConfig() {
        return getInstance().getConfig();
    }
// FileConfiguration config = Main.getPluginConfig();

    public void loadCommands() {
        /*
        Never forget to register your commands in plugin.yml
         */

        WarpManager warpManager = new WarpManager(dbManager.getWarpDatabase());
        getCommand("shulker").setExecutor(new ShulkerCommands(shulkerManagement, this, cnfg));
        this.getCommand("warp").setExecutor(new WarpCMD(this, warpManager));
        getCommand("ss").setExecutor(new ScreenShareCommands(this));
    }

    public void loadEvents() {
        this.getServer().getPluginManager().registerEvents(new ShulkerListener(this, cnfg, shulkerManagement), this);
        getServer().getPluginManager().registerEvents(new DeathMessages(), this);
        getServer().getPluginManager().registerEvents(new JoinMessages(), this);
        getServer().getPluginManager().registerEvents(new ChatFilter(this), this);
        getServer().getPluginManager().registerEvents(new ShopsMain(get), this);

    }
    public void noPermission(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage("You don't have permission to use this command.");
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
    }
    public void errorMessage(CommandSender p) {
        //use this only when something goes wrong or they cannot use that command
        //else use no permission
        p.sendMessage("&cA Error Occurred");
    }

    public void sendtoPermission(String message, String perms, Player p) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(perms)) {
                p.sendMessage(cnfg.getString("prefix") + message);
            }
        }
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
                player.sendMessage(cnfg.getString("prefix") + "  &fYou Got Teleported To Spawn");
            } else {
                getLogger().warning("Spawn coordinates are invalid.");
            }
        } else {
            getLogger().warning("Spawn coordinates are not configured.");
        }
    }

    public boolean isPlayerInsideRegion(Player p, String rgn) {
        for (ProtectedRegion region : WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(p.getLocation()))) {
            if (!region.getId().equalsIgnoreCase(rgn)) {
                //player outside
                return false;
            }
            //in specific region
            return true;
        }
        //not even in a fucking region smh
        return false;
    }
    @Override
    public void onDisable() {
        System.out.println("'het komt wel goed trust the process' -tovy");
    }
}
