package projects.tovy.github.ServerUsage.Shops;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import projects.tovy.github.ItemHandeling;

public class ShopTemplate {
    private Material material;
    private String name;
    private int quantity;
    private int slot;

    public ShopTemplate(Material material, String name, int quantity, int slot) {
        this.material = material;
        this.name = name;
        this.quantity = quantity;
        this.slot = slot;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack createItemStack(ItemHandeling itemHandler) {
        itemHandler.setItemMaterial(this.material);
        itemHandler.setItemName(this.name);
        itemHandler.setShopItem(this.quantity);
        return itemHandler.getItemStack();
    }
}
