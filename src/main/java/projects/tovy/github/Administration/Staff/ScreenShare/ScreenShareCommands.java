package projects.tovy.github.Administration.Staff.ScreenShare;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ScreenShareCommands implements CommandExecutor {
    private final JavaPlugin plugin;


    public ScreenShareCommands(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("shulker")) {
            Player p = (Player) sender;
            Player targetp = plugin.getServer().getPlayer(args[0]);
            if (args.length > 1) {
                if (args[0].equalsIgnoreCase("set")) {
                    //store the coords in litesql (make a file with the creation of the database
                } else if (args[0].equalsIgnoreCase("del")) {

                } else if (args[0].equalsIgnoreCase("help")) {

                } else if (args[0].equalsIgnoreCase("logs")) {

                } else {
                    if (targetp != null) {
                        //teleport player to the stored coords
                    }
                }
            }
        }
        return false;
    }

}
