package projects.tovy.github;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiTemplate {

    public ItemHandeling item;
    private Material material;
    private String name;
    private int quantity;
    private int slot;

    public GuiTemplate(ItemHandeling item, Material material, String name, int quantity, int slot) {
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

    public ItemStack createItemStack(ItemHandeling item) {
        item.setItemMaterial(material);
        item.setItemName(this.name);
        item.setItemQuantity(quantity);
        return item.getItemStack();
    }

    /*
    USAGE:
    public List<GuiTemplate> getGuiItems() {
        return List.of(
                new GuiTemplate(Material.TOTEM_OF_UNDYING, "&6Totem Effect", 1, 10),
                new GuiTemplate(Material.LAVA_BUCKET, "&cBleed Effect", 1, 11),
                new GuiTemplate(Material.POLAR_BEAR_SPAWN_EGG, "&4Rage Effect", 1, 12),
                new GuiTemplate(Material.HEART_OF_THE_SEA, "&dLove Effect", 1, 13)
        );
    }

    use the function for in the var

    easyGuiBorder.openGuiBorder(player, 54, title);
        Inventory inventory = player.getOpenInventory().getTopInventory();

        example above


        for (KillEffectTemplate template : killEffectTemplates) {
            ItemStack itemStack = template.createItemStack(itemHandling);
            inventory.setItem(template.getSlot(), itemStack);
        } killeffect is in this term gui

        and open it with
        public void openKillEffectsShop(Player player) {
        List<KillEffectTemplate> killEffectItems = getKillEffectItems();
        openKillEffectsGui(player, "&4&kKill Effects", killEffectItems);
    }
    examples!!!!
     */
}
