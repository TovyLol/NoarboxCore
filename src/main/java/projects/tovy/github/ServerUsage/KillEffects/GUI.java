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

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class GUI implements CommandExecutor, Listener {
    private EasyGuiBorder border;
    private KEDataBase db;

    public GUI(EasyGuiBorder border, KEDataBase db) {
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
        boolean canAccessAll = p.hasPermission("Noarbox.gui.all") || p.getName().equalsIgnoreCase("2b2t base alt");

        String renl = "&6Right Click to enable";
        Inventory inv = border.openGuiBorder(p, 54, "Kill Effects");

        if (canAccessAll || p.hasPermission("Noarbox.gui.totem")) {
            ItemStack totemEffectItem = createItem(Material.TOTEM_OF_UNDYING, "&6Totem Effect", Arrays.asList("&fA unique Effect upon killing someone!", renl));
            inv.setItem(1, totemEffectItem);
        } else {
            notUnlocked(p, 1, "&6Totem Effect");
        }
        if (canAccessAll || p.hasPermission("Noarbox.gui.bleed")) {
            ItemStack bleedEffectItem = createItem(Material.LAVA_BUCKET, "&cBleed Effect", Arrays.asList("&fA unique Effect upon killing someone!", renl));
            inv.setItem(2, bleedEffectItem);
        } else {
            notUnlocked(p, 2, "&cBleed Effect");
        }
        if (canAccessAll || p.hasPermission("Noarbox.gui.rage")) {
            ItemStack rageEffectItem = createItem(Material.POLAR_BEAR_SPAWN_EGG, "&4Rage Effect", Arrays.asList("&fA unique Effect upon killing someone!", renl));
            inv.setItem(3, rageEffectItem);
        } else {
            notUnlocked(p, 3, "&4Rage Effect");
        }
        if (canAccessAll || p.hasPermission("Noarbox.gui.love")) {
            ItemStack loveEffectItem = createItem(Material.HEART_OF_THE_SEA, "&dLove Effect", Arrays.asList("&fA unique Effect upon killing someone!", renl));
            inv.setItem(4, loveEffectItem);
        } else {
            notUnlocked(p, 4, "&dLove Effect");
        }
        if (canAccessAll || p.hasPermission("Noarbox.gui.sword")) {
            ItemStack swordEffectItem = createItem(Material.IRON_SWORD, "&fSword Effect", Arrays.asList("&fA unique Effect upon killing someone!", renl));
            inv.setItem(5, swordEffectItem);
        } else {
            notUnlocked(p, 5, "&fSword Effect");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("Kill Effects")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                String effectColumn = getEffectColumnByName(displayName);
                if (effectColumn != null) {
                    try {
                        db.setSingleEffect(p.getUniqueId().toString(), effectColumn);
                        p.sendMessage("Effect " + displayName + " enabled!");
                        openGui(p);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        p.sendMessage("An error occurred while updating the effect.");
                    }
                }
            }
        }
    }

    private String getEffectColumnByName(String name) {
        switch (name) {
            case "&6Totem Effect":
                return "totem_enabled";
            case "&cBleed Effect":
                return "bleed_enabled";
            case "&4Rage Effect":
                return "rage_enabled";
            case "&dLove Effect":
                return "love_enabled";
            case "&fSword Effect":
                return "sword_enabled";
            default:
                return null;
        }
    }

    private void notUnlocked(Player p, int slot, String name) {
        Inventory inv = p.getOpenInventory().getTopInventory();
        ItemStack notunlocked = createItem(Material.RED_DYE, name, Arrays.asList("&cLOCKED"));
        inv.setItem(slot, notunlocked);
    }

    private ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemHandeling itemHandeling = new ItemHandeling(itemStack.getType());
        itemHandeling.setItemName(name);
        itemHandeling.setItemLore(lore);
        return itemHandeling.getItemStack();
    }
}
