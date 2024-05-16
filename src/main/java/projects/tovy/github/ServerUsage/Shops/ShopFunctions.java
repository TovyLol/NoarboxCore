package projects.tovy.github.ServerUsage.Shops;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopFunctions {

    public void funcShop1() {

    }

    public void openGui(Player p) {
        Inventory gui = Bukkit.createInventory(null, 54, "Custom GUI");


        ItemStack grayGlass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = grayGlass.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("");
            grayGlass.setItemMeta(meta);
        }

       
        for (int i = 0; i < 54; i++) {
            if (isOuterSlot(i)) {
                gui.setItem(i, grayGlass);
            }
        }

        p.openInventory(gui);
    }
    private boolean isOuterSlot(int slot) {
        int row = slot / 9;
        int col = slot % 9;
        return row == 0 || row == 5 || col == 0 || col == 8;
    }

}
