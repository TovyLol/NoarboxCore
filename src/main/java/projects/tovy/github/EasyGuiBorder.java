package projects.tovy.github;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EasyGuiBorder {
    public void openGuiBorder(Player player, int size, String title) {
        Inventory gui = Bukkit.createInventory(null, size, title);

        ItemStack grayGlass = createGrayGlassPane();

        for (int i = 0; i < size; i++) {
            if (isOuterSlot(i, size)) {
                gui.setItem(i, grayGlass);
            }
        }

        player.openInventory(gui);
    }

    private ItemStack createGrayGlassPane() {
        ItemStack grayGlass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = grayGlass.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("");
            grayGlass.setItemMeta(meta);
        }
        return grayGlass;
    }

    private boolean isOuterSlot(int slot, int size) {
        int rowCount = size / 9;
        int row = slot / 9;
        int col = slot % 9;
        return row == 0 || row == rowCount - 1 || col == 0 || col == 8;
    }
}
