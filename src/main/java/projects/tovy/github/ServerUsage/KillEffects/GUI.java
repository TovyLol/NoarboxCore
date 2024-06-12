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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;
import projects.tovy.github.DataBase.KEDataBase;
import projects.tovy.github.EasyGuiBorder;
import projects.tovy.github.ItemHandeling;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class GUI implements CommandExecutor, Listener {
    private final EasyGuiBorder border;
    private final KEDataBase db;

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

        ChatColor gold = ChatColor.GOLD;
        ChatColor red = ChatColor.RED;
        ChatColor darkRed = ChatColor.DARK_RED;
        ChatColor blue = ChatColor.BLUE;
        ChatColor white = ChatColor.WHITE;

        String renl = gold + "Right Click to enable";
        Inventory inv = border.openGuiBorder(p, 54, "Kill Effects");

        if (canAccessAll || p.hasPermission("Noarbox.gui.totem")) {
            ItemStack totemEffectItem = createItem(Material.TOTEM_OF_UNDYING, gold + "Totem Effect", Arrays.asList(white + "A unique Effect upon killing someone!", renl));
            inv.setItem(10, totemEffectItem);
        } else {
            notUnlocked(p, 10, gold + "Totem Effect");
        }
        if (canAccessAll || p.hasPermission("Noarbox.gui.bleed")) {
            ItemStack bleedEffectItem = createItem(Material.NETHERITE_SWORD, red + "Bleed Effect", Arrays.asList(white + "A unique Effect upon killing someone!", renl));
            inv.setItem(11, bleedEffectItem);
        } else {
            notUnlocked(p, 11, red + "Bleed Effect");
        }
        if (canAccessAll || p.hasPermission("Noarbox.gui.rage")) {
            ItemStack rageEffectItem = createItem(Material.CRIMSON_ROOTS, darkRed + "Rage Effect", Arrays.asList(white + "A unique Effect upon killing someone!", renl));
            inv.setItem(12, rageEffectItem);
        } else {
            notUnlocked(p, 12, darkRed + "Rage Effect");
        }
        if (canAccessAll || p.hasPermission("Noarbox.gui.love")) {
            ItemStack loveEffectItem = createItem(Material.HEART_OF_THE_SEA, blue + "Love Effect", Arrays.asList(white + "A unique Effect upon killing someone!", renl));
            inv.setItem(13, loveEffectItem);
        } else {
            notUnlocked(p, 13, blue + "Love Effect");
        }
        if (canAccessAll || p.hasPermission("Noarbox.gui.sword")) {
            ItemStack swordEffectItem = createItem(Material.IRON_SWORD, white + "Sword Effect", Arrays.asList(white + "A unique Effect upon killing someone!", renl));
            inv.setItem(14, swordEffectItem);
        } else {
            notUnlocked(p, 14, white + "Sword Effect");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("Kill Effects")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() != Material.AIR && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                p.sendMessage(ChatColor.GREEN + "Clicked item: " + displayName);
                String effectColumn = getEffectColumnByName(displayName);
                if (effectColumn != null) {
                    try {
                        db.setSingleEffect(p.getUniqueId().toString(), effectColumn);
                        p.sendMessage("Effect " + ChatColor.stripColor(displayName) + " enabled!");
                        openGui(p);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        p.sendMessage(ChatColor.RED + "An error occurred while enabling the effect!");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Effect column not found!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Invalid item clicked!");
            }
        }
    }


    private String getEffectColumnByName(String name) {
        switch (ChatColor.stripColor(name)) {
            case "Totem Effect":
                return "totem_enabled";
            case "Bleed Effect":
                return "bleed_enabled";
            case "Rage Effect":
                return "rage_enabled";
            case "Love Effect":
                return "love_enabled";
            case "Sword Effect":
                return "sword_enabled";
            default:
                return null;
        }
    }

    private void notUnlocked(Player p, int slot, String name) {
        Inventory inv = p.getOpenInventory().getTopInventory();
        ItemStack notunlocked = createItem(Material.RED_DYE, name, Arrays.asList(ChatColor.RED + "LOCKED"));
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
