package projects.tovy.github;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import projects.tovy.github.Administration.Staff.ScreenShare.ScreenShareCommands;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerCommands;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerManagment;
import projects.tovy.github.PlayerUsage.Warps.WarpCMD;
import projects.tovy.github.PlayerUsage.Warps.WarpManager;
import projects.tovy.github.ServerUsage.Chat.ChatFilter;
import projects.tovy.github.ServerUsage.Chat.DeathMessages;
import projects.tovy.github.ServerUsage.Chat.JoinMessages;
import org.bukkit.entity.Player;
import projects.tovy.github.ServerUsage.Shops.ShopsMain;

public final class Main extends JavaPlugin {
    // Connections
    private final ShulkerManagment shulkerManagment = new ShulkerManagment();

    private static Main instance;

    @Override
    public void onEnable() {
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

        WarpManager warpManager = new WarpManager(this);
        getCommand("shulker").setExecutor(new ShulkerCommands(shulkerManagment));
        getCommand("warp").setExecutor(new WarpCMD(this, warpManager));
        getCommand("ss").setExecutor(new ScreenShareCommands(this));
    }

    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new DeathMessages(), this);
        getServer().getPluginManager().registerEvents(new JoinMessages(), this);
        getServer().getPluginManager().registerEvents(new ChatFilter(this), this);
        getServer().getPluginManager().registerEvents(new ShopsMain(), this);

    }
    public void noPermission(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage("You don't have permission to use this command.");
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
    }





    @Override
    public void onDisable() {
        System.out.println("'het komt wel goed trust the process' -tovy");
    }

}
