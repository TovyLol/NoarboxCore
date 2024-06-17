package projects.tovy.github.PlayerUsage.ShulkerRooms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ShulkerGUI {

    public static void openShulkerGUI(Player player, ShulkerManagement shulkerManagement) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Shulker Rooms");

        for (int roomId : shulkerManagement.getAllRoomIds()) {
            ItemStack item = new ItemStack(Material.SHULKER_BOX);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + "Room " + roomId);
            if (shulkerManagement.isRoomOccupied(roomId)) {
                meta.setLore(Collections.singletonList(ChatColor.RED + "Occupied"));
            } else {
                meta.setLore(Collections.singletonList(ChatColor.GREEN + "Available"));
            }
            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);
    }

    public static void handleInventoryClick(InventoryClickEvent e, ShulkerManagement shulkerManagement) {
        if (e.getView().getTitle().equals(ChatColor.BLUE + "Shulker Rooms")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() != Material.SHULKER_BOX) return;
            ItemMeta meta = clickedItem.getItemMeta();
            String displayName = meta.getDisplayName();
            int roomId = Integer.parseInt(displayName.split(" ")[1]);
            if (shulkerManagement.isRoomOccupied(roomId)) {
                p.sendMessage(ChatColor.RED + "Room " + roomId + " is occupied.");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
            } else {
                p.teleport(shulkerManagement.getRoom(roomId));
                p.sendMessage(ChatColor.BLUE + "You have been teleported to shulker room " + roomId);

                shulkerManagement.setRoomOccupied(roomId, true);
                p.closeInventory();
            }
        }
    }
}