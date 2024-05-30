package projects.tovy.github.ServerUsage.KillEffects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import projects.tovy.github.EasyGuiBorder;
import projects.tovy.github.ItemHandeling;

public class GUI implements CommandExecutor{
    private ItemHandeling item;
    private EasyGuiBorder border;

    public GUI (ItemHandeling item, EasyGuiBorder border) {
        this.item = item;
        this.border = border;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (sender instanceof Player) {
            openGui();
        }
        return false;
    }

    public void openGui() {

    }
}
