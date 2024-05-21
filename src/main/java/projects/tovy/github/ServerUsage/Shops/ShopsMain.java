package projects.tovy.github.ServerUsage.Shops;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ShopsMain implements Listener {

    private GetShops getShops;
    public ShopsMain(GetShops getShops) {
        this.getShops = getShops;
    }
    public void onRightClick(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (entity.getCustomName() != null ) {
            switch(entity.getCustomName()) {
                case "&5&kShulkers":
                    getShops.getShulkerShop(e.getPlayer());
                    break;

            }
        }
    }
    public void onInvclick(InventoryClickEvent e) {

    }
}
