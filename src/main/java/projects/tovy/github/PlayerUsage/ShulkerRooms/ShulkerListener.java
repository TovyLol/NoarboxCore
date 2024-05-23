package projects.tovy.github.PlayerUsage.ShulkerRooms;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import projects.tovy.github.Main;

public class ShulkerListener implements Listener {
    public Main main;
    public FileConfiguration config;
    public ShulkerManagment manager;
    public ShulkerListener(Main main, FileConfiguration config, ShulkerManagment manager) {
        this.main = main;
        this.config = config;
        this.manager = manager;
    }

    public void jukeboxClick(PlayerInteractEvent e) {
        Player p =e.getPlayer();
        Action action = e.getAction();
        Block clickedBlock = e.getClickedBlock();
        if (main.isPlayerInsideRegion(p, config.getString("shulkerregion"))) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (clickedBlock.getType() == Material.JUKEBOX) {
                    p.performCommand("/shulker");
                }
            }
        } else if (main.isPlayerInsideRegion(p, config.getString("spawnregion"))) {
            p.performCommand("/shulker");
        } else if (main.isPlayerInsideRegion(p, "shulkerjukebox")) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (clickedBlock.getType() == Material.JUKEBOX) {
                    p.performCommand("/shulker");
                }
            }
        } else {
            main.errorMessage(p);
        }
    }

}
