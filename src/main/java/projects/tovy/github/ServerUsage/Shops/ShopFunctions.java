package projects.tovy.github.ServerUsage.Shops;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import projects.tovy.github.EasyGuiBorder;
import projects.tovy.github.ItemHandeling;

public class ShopFunctions {
    private ItemHandeling item;
    private EasyGuiBorder border;



    public ShopFunctions(ItemHandeling item, EasyGuiBorder border) {
        this.item = item;
        this.border = border;
    }

    public void funcShulkerShop(Player p) {
        border.openGuiBorder(p, 54, "&5&kShulkers");
        Inventory inv = p.getOpenInventory().getTopInventory();

        ItemStack rs = new ItemStack(Material.RED_SHULKER_BOX);
 

    }


    public void getShulkerShopItems() {
        ItemStack s = item.getItemStack();
        item.setItemMaterial(Material.SHULKER_BOX);
        item.setItemName("&dShulker Box");

        ItemStack ws = item.getItemStack();
        item.setItemMaterial(Material.WHITE_SHULKER_BOX);
        item.setItemName("&fWhite Shulker Box");

        ItemStack gs = item.getItemStack();
        item.setItemMaterial(Material.GRAY_SHULKER_BOX);
        item.setItemName("&fGray Shulker Box");

        ItemStack os = item.getItemStack();
        item.setItemMaterial(Material.ORANGE_SHULKER_BOX);
        item.setItemName("&#FFA500Orange Shulker Box");

        ItemStack rs = item.getItemStack();
        item.setItemMaterial(Material.RED_SHULKER_BOX);
        item.setItemName("&cRed Shulker Box");

        ItemStack lbs = item.getItemStack();
        item.setItemMaterial(Material.LIGHT_BLUE_SHULKER_BOX);
        item.setItemName("&3Light Blue Shulker Box");

        ItemStack ys = item.getItemStack();
        item.setItemMaterial(Material.YELLOW_SHULKER_BOX);
        item.setItemName("&6Yellow Shulker Box");

        ItemStack ls = item.getItemStack();
        item.setItemMaterial(Material.LIME_SHULKER_BOX);
        item.setItemName("&2Lime Shulker Box");

        ItemStack lgs = item.getItemStack();
        item.setItemMaterial(Material.LIGHT_GRAY_SHULKER_BOX);
        item.setItemName("&7Light Gray Shulker Box");
    }



}
