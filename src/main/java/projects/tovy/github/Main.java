package projects.tovy.github;

import org.bukkit.plugin.java.JavaPlugin;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerCommands;
import projects.tovy.github.PlayerUsage.ShulkerRooms.ShulkerManagment;

public final class Main extends JavaPlugin {
    //connections
    private final ShulkerManagment shulkerManagment = new ShulkerManagment();
    @Override
    public void onEnable() {
        //config stuff
        getConfig().options().copyDefaults();
        saveConfig();

        //calling functions
        loadCommands();
        loadEvents();

    }
    public void loadCommands() {
        /*
        Never forget to register your commands in plugin.yml
         */

        getCommand("shulker").setExecutor(new ShulkerCommands(shulkerManagment));

    }
    public void loadEvents() {

    }
    @Override
    public void onDisable() {
        System.out.println("'het komt wel goed trust the process' -tovy");
    }
}
