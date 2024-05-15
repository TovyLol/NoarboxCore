package projects.tovy.github.ServerUsage.Chat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;


public class DeathMessages implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String deathMessage = event.getDeathMessage();
        DeathCause cause = DeathCause.detectCause(deathMessage);
        event.setDeathMessage(cause.formatDeathMessage(player));
    }
    private enum DeathCause {
        KINETIC_ENERGY("experienced kinetic energy"),
        KINETIC_ENERGY_ESCAPE("experienced kinetic energy while trying to escape"),
        SLAIN_BY("was slain by"),
        UNKNOWN("lost in time");
        private final String message;

        DeathCause(String message) {
            this.message = message;
        }

        public static DeathCause detectCause(String deathMessage) {
            for (DeathCause cause : values()) {
                if (deathMessage.contains(cause.message)) {
                    return cause;
                }
            }
            return UNKNOWN;
        }

        public String formatDeathMessage(Player player) {
            switch (this) {
                case KINETIC_ENERGY:
                    return ChatColor.GOLD + player.getName() + ChatColor.RED + " didn't see the wall coming";
                case KINETIC_ENERGY_ESCAPE:
                    String attackerName = player.getKiller() != null ? player.getKiller().getName() : "Unknown";
                    return ChatColor.GOLD + player.getName() + ChatColor.RED + " didn't see the wall coming whilst trying to kill " + ChatColor.GOLD + attackerName;
                case SLAIN_BY:
                    Player killer = player.getKiller();
                    ItemStack usedItem = killer != null ? killer.getInventory().getItemInMainHand() : new ItemStack(Material.AIR);
                    String itemName = usedItem.getType().toString();
                    return ChatColor.GOLD + player.getName() + ChatColor.RED + " lost to " + ChatColor.GOLD + killer.getName() + ChatColor.RED + " using " + ChatColor.GOLD + itemName;
                case UNKNOWN:
                default:
                    return ChatColor.GOLD + player.getName() + ChatColor.RED + "'s death reason was lost in time help us to find it";
            }
            //shit needs to be updated tho

        }
    }

}
