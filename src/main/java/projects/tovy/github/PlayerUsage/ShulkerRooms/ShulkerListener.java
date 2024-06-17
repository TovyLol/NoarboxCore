package projects.tovy.github.PlayerUsage.ShulkerRooms;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import projects.tovy.github.Main;

public class ShulkerListener implements Listener {
    private final Main main;
    private final FileConfiguration cnfg;
    private final ShulkerManagement shulkerManagement;

    public ShulkerListener(Main main, FileConfiguration cnfg, ShulkerManagement shulkerManagement) {
        this.main = main;
        this.cnfg = cnfg;
        this.shulkerManagement = shulkerManagement;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        Block clickedBlock = e.getClickedBlock();
        if (main.isPlayerInsideRegion(p, cnfg.getString("shulkerregion"))) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock.getType() == Material.JUKEBOX) {
                p.performCommand("/shulker");
            }
        } else if (main.isPlayerInsideRegion(p, cnfg.getString("spawnregion"))) {
            p.performCommand("/shulker");
        } else if (main.isPlayerInsideRegion(p, "shulkerjukebox")) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock.getType() == Material.JUKEBOX) {
                p.performCommand("/shulker");
            }
        } else {
            main.errorMessage(p);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ShulkerGUI.handleInventoryClick(event, shulkerManagement);
    }
}