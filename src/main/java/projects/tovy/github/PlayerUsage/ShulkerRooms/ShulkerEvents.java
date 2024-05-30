package projects.tovy.github.PlayerUsage.ShulkerRooms;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import projects.tovy.github.Main;


public class ShulkerEvents implements Listener {
    public Main main;
    public FileConfiguration config;
    public ShulkerManagement managment;

    public ShulkerEvents(Main main, FileConfiguration config, ShulkerManagement managment) {
        this.main = main;
        this.config = config;
        this.managment = managment;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for(ProtectedRegion region : WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(p.getLocation()))) {

            if(!region.getId().equalsIgnoreCase(config.getString("shulkerregion"))) {

                //outside region
                continue;
            }
            //inside region
            main.tpToSpawn(p);
            p.sendMessage("");

            break;
        }
    }

    public void commandinShulker(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage().toLowerCase();
        if (main.isPlayerInsideRegion(p, config.getString("shulkerregion"))) {
            if (msg.contains("/shulker")) {
                main.tpToSpawn(p);
            } else {
                e.setCancelled(true);
                main.errorMessage(p);
            }
        }
    }
}
