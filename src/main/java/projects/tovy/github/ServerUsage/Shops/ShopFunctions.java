package projects.tovy.github.ServerUsage.Shops;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import projects.tovy.github.EasyGuiBorder;
import projects.tovy.github.ItemHandeling;

import java.util.List;

public class ShopFunctions {
    private ItemHandeling item;
    private EasyGuiBorder border;

    public ShopFunctions(ItemHandeling item, EasyGuiBorder border) {
        this.item = item;
        this.border = border;
    }

    public void openShop(Player p, String title, List<ShopTemplate> shopTemplates) {
        border.openGuiBorder(p, 54, title);
        Inventory inv = p.getOpenInventory().getTopInventory();

        for (ShopTemplate template : shopTemplates) {
            ItemStack itemStack = template.createItemStack(item);
            inv.setItem(template.getSlot(), itemStack);
        }
    }

    public List<ShopTemplate> getShulkerShopItems() {
        return List.of(
                new ShopTemplate(Material.SHULKER_BOX, "&dShulker Box", 64, 10),
                new ShopTemplate(Material.WHITE_SHULKER_BOX, "&fWhite Shulker Box", 64, 11),
                new ShopTemplate(Material.GRAY_SHULKER_BOX, "&fGray Shulker Box", 64, 12),
                new ShopTemplate(Material.ORANGE_SHULKER_BOX, "&#FFA500Orange Shulker Box", 64, 13),
                new ShopTemplate(Material.RED_SHULKER_BOX, "&cRed Shulker Box", 64, 14),
                new ShopTemplate(Material.LIGHT_BLUE_SHULKER_BOX, "&3Light Blue Shulker Box", 64, 15),
                new ShopTemplate(Material.YELLOW_SHULKER_BOX, "&6Yellow Shulker Box", 64, 16),
                new ShopTemplate(Material.LIME_SHULKER_BOX, "&2Lime Shulker Box", 64, 17),
                new ShopTemplate(Material.LIGHT_GRAY_SHULKER_BOX, "&7Light Gray Shulker Box", 64, 18)
        );
    }


    public void openShulkerShop(Player p) {
        List<ShopTemplate> shulkerShopItems = getShulkerShopItems();
        openShop(p, "&5&kShulkers", shulkerShopItems);
    }
}
