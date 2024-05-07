package projects.tovy.github.ServerUsage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinMessages implements Listener {

    @EventHandler
    public void onJoinandLeave(PlayerJoinEvent e, PlayerQuitEvent event) {
        Player p = e.getPlayer();
        Player player = e.getPlayer();
        if (p.getName().equalsIgnoreCase("2b2tbase_alt")) {
            e.setJoinMessage("");
        } else {
            e.setJoinMessage(ChatColor.BOLD.BLUE + "+" + p);
        }
        if (p.getName().equalsIgnoreCase("2b2tbase_alt")) {
            event.setQuitMessage("");
        } else {
            event.setQuitMessage((ChatColor.BOLD.BLUE + "-" + player);
        }
    }
}
