package projects.tovy.github.ServerUsage.KillEffects;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import projects.tovy.github.DataBase.KEDataBase;
import projects.tovy.github.EasyGuiBorder;
import projects.tovy.github.ItemHandeling;

import java.util.List;

public class GUI implements CommandExecutor, Listener {
    private EasyGuiBorder border;
    private KEDataBase db;

    public GUI(EasyGuiBorder border,  KEDataBase db) {
        this.border = border;
        this.db = db;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            openGui(p);
            return true;
        }
        return false;
    }

    public void openGui(Player p) {
        String renl = "&6Right Click to enable";
        border.openGuiBorder(p, 54, "Kill Effects");
        Inventory inv = p.getOpenInventory().getTopInventory();


        if (p.hasPermission("Noarbox.gui.totem")) {
            ItemStack totemEffectItem = createItem(Material.TOTEM_OF_UNDYING, "&6Totem Effect", List.of("&fA unique Effect upon killing someone!", renl));
            inv.setItem(slot, totemEffectItem);

        } else if (p.hasPermission("Noarbox.gui.bleed")) {
            ItemStack bleedEffectItem = createItem(Material.LAVA_BUCKET, "&cBleed Effect", List.of("&fA unique Effect upon killing someone!" + renl));
            inv.setItem(slot, bleedEffectItem);

        } else if (p.hasPermission("Noarbox.gui.rage")) {
            ItemStack rageEffectItem = createItem(Material.POLAR_BEAR_SPAWN_EGG, "&4Rage Effect", List.of("&fA unique Effect upon killing someone!" + renl));
            inv.setItem(slot, rageEffectItem);

        } else if (p.hasPermission("Noarbox.gui.love")) {
            ItemStack loveEffectItem = createItem(Material.HEART_OF_THE_SEA, "&dLove Effect", List.of("&fA unique Effect upon killing someone!" + renl));
            inv.setItem(slot, loveEffectItem);

        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("Kill Effects")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                String permission = clickedItem.getItemMeta().getDisplayName();
                if (permission != null && p.hasPermission(permission)) {
                    // Handle the click event
                    openGui(p); // No need for try-catch block here
                }
            }
        }
    }




    private ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemHandeling itemHandeling = new ItemHandeling(itemStack.getType());
        itemHandeling.setItemName(name);
        itemHandeling.setItemLore(lore);
        return itemHandeling.getItemStack();
    }
}
