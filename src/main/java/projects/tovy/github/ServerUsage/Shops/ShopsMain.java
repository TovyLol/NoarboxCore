package projects.tovy.github.ServerUsage.Shops;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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
                case "":
                    getShops.getShop1();
                    break;

            }
        }


    }
}
