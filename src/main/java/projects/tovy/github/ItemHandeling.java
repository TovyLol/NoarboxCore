package projects.tovy.github;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemHandeling {
    private final ItemStack itemStack;
    private final ItemMeta meta;

    public ItemHandeling(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.meta = itemStack.getItemMeta();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getItemName() {
        if (meta != null && meta.hasDisplayName()) {
            return meta.getDisplayName();
        }
        return "";
    }

    public void setItemName(String name) {
        if (meta != null) {
            meta.setDisplayName(name);
            itemStack.setItemMeta(meta);
        }
    }

    public List<String> getItemLore() {
        if (meta != null && meta.hasLore()) {
            return meta.getLore();
        }
        return null;
    }

    public void setItemLore(List<String> lore) {
        if (meta != null) {
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
    }

    public Material getItemMaterial() {
        return itemStack.getType();
    }

    public void setItemMaterial(Material material) {
        itemStack.setType(material);
    }

    public int getItemDurability() {
        return itemStack.getDurability();
    }

    public void setItemDurability(int durability) {
        itemStack.setDurability((short) durability);
    }

    public void showEnchantments(boolean show) {
        if (meta != null) {
            if (show) {
                meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(meta);
        }
    }

    public void showGlow(boolean show) {
        if (meta != null) {
            if (show) {
                meta.addEnchant(Enchantment.DURABILITY, 0, true);
                showEnchantments(false);
            } else {
                meta.removeEnchant(Enchantment.DURABILITY);
                showEnchantments(true);
            }
            itemStack.setItemMeta(meta);
        }
    }

    public void hideEssentialsArmor(boolean show) {
        if (meta != null) {
            if (show) {
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE);
            } else {
                meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE);
            }
            itemStack.setItemMeta(meta);
        }
    }

    public void hideEssentialsItems(boolean show) {
        if (meta != null) {
            if (show) {
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            } else {
                meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            }
            itemStack.setItemMeta(meta);
        }
    }
}
