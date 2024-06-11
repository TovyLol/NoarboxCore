package projects.tovy.github.Administration.Staff.Mode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ModeCommands implements CommandExecutor {

    public boolean enabled = false;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("noarbox.staff.mode")) {

        }
        return false;
    }
    public void modeMethod() {

    }
}
