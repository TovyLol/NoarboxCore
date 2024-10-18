package projects.tovy.github.ServerUsage.Chat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
                    return ChatColor.GOLD + player.getName() + ChatColor.RED + " is a high tier 1 wall!(skill issue)";
                case KINETIC_ENERGY_ESCAPE:
                    String attackerName = player.getKiller() != null ? player.getKiller().getName() : "Unknown";
                    return ChatColor.GOLD + player.getName() + ChatColor.RED + " didn't see the wall coming whilst trying to kill " + ChatColor.GOLD + attackerName;
                case SLAIN_BY:
                    Player killer = player.getKiller();
                    if (killer != null) {
                        ItemStack usedItem = killer.getInventory().getItemInMainHand();
                        ItemMeta itemMeta = usedItem.getItemMeta();
                        String itemName;
                        if (itemMeta != null && itemMeta.hasDisplayName()) {
                            itemName = itemMeta.getDisplayName();
                        } else {
                            itemName = usedItem.getType().toString();
                        }
                        return ChatColor.GOLD + player.getName() + ChatColor.RED + " lost to " + ChatColor.GOLD + killer.getName() + ChatColor.RED + " using " + ChatColor.GOLD + itemName;
                    } else {
                        return ChatColor.GOLD + player.getName() + ChatColor.RED + " was slain by an unknown entity";
                    }
                case UNKNOWN:
                default:
                    return ChatColor.GOLD + player.getName() + ChatColor.RED + "'s death reason was lost in time help us to find it";
            }
        }
    }
}
