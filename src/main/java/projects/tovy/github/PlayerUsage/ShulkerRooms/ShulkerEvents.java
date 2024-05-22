package projects.tovy.github.PlayerUsage.ShulkerRooms;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import projects.tovy.github.Main;


public class ShulkerEvents implements Listener {
    public Main main;
    public FileConfiguration config;

    public ShulkerEvents(Main main, FileConfiguration config) {
        this.main = main;
        this.config = config;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

    }



}
