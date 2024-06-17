package projects.tovy.github.Administration.Staff.Mode;

import com.sk89q.worldguard.bukkit.event.entity.DamageEntityEvent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import projects.tovy.github.Main;

import java.util.HashMap;
import java.util.UUID;

public class ModeEvents implements Listener {
    private ModeMain mmain;
    private Main main;

    public ModeEvents(ModeMain mmain, Main main) {
        this.mmain = mmain;
        this.main = main;
    }

    private HashMap<UUID, Boolean> playerRespawnFlag = new HashMap<>();

    public void no(Player p) {
        main.sendTitle(p, ChatColor.RED + "Warning", ChatColor.RED + "This action is not permitted right now!");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        boolean modeEnabled = mmain.getCommands().isEnabled(player);
        if (modeEnabled) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        main.sendToPermission(ChatColor.BOLD.RED + "WARNING" + ChatColor.WHITE + p + " Logged in while in staff mode, Investigate", "noarbox.operator.usage", p);

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (mmain.getCommands().isEnabled(p)) {
            main.sendToPermission(ChatColor.BOLD.RED + "WARNING" + ChatColor.WHITE + p + " Logged in while in staff mode, Investigate", "noarbox.operator.usage", p);
            main.sendTitle(p, ChatColor.BOLD.RED + "Warning", ChatColor.WHITE + "You logged in whilst in staff mode, operators are notified");
            main.playWarningSound(p, p.getLocation());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (mmain.getCommands().isEnabled(p)) {
            playerRespawnFlag.put(event.getEntity().getUniqueId(), true);
        }

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        if (playerRespawnFlag.getOrDefault(playerId, false)) {
            Player p = event.getPlayer();
            main.sendToPermission(ChatColor.BOLD.RED + "WARNING" + ChatColor.WHITE + p + " Died in while in staff mode, Investigate", "noarbox.operator.usage", p);
            main.playWarningSound(p, p.getLocation());
            main.sendTitle(p, ChatColor.RED + "Warning", ChatColor.RED + "You died in Staffmode, Staffmode got turned off");
            playerRespawnFlag.put(playerId, false);
            mmain.getCommands().setEnabled(p, false);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (mmain.getCommands().isEnabled(p)) {
            e.setCancelled(true);
            no(p);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) e;
            Entity damager = entityEvent.getDamager();
            if (damager instanceof Player) {
                Player player = (Player) damager;
                if (mmain.getCommands().isEnabled(player)) {
                    e.setCancelled(true);
                    no(player);
                } else {
                    e.setCancelled(false);
                }
            }
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        Entity f = e.getEntity();
        if (f instanceof Player) {
            Player p = (Player) f;
            if (mmain.getCommands().isEnabled(p)) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Picking up Items is not permitted!");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
        }
    }
}
