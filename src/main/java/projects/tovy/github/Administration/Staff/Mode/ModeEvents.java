package projects.tovy.github.Administration.Staff.Mode;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ModeEvents implements Listener {
    public ModeMain mmain;

    public ModeEvents(ModeMain mmain) {
        this.mmain = mmain;
    }



    public void onInventoryClick(InventoryClickEvent e) {
        if (mmain.getCommands().enabled) {

        }
    }
}
