package projects.tovy.github.PlayerUsage.DeathStashes;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import projects.tovy.github.Main;

import java.util.UUID;

public class StashCommand implements CommandExecutor {
    private final DsMain dsMain;
    private final FileConfiguration cnfg;
    private final Main main;

    public StashCommand(DsMain dsMain, FileConfiguration cnfg, Main main) {
        this.dsMain = dsMain;
        this.cnfg = cnfg;
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            UUID playerUUID = p.getUniqueId();
            boolean enabled = dsMain.isStashEnabled(playerUUID);
            dsMain.setStashEnabled(playerUUID, !enabled);
            String message = cnfg.getString("prefix") + "&fDeath stash has been " + (enabled ? "disabled" : "enabled") + ".";
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            main.sendTitle(p, ChatColor.RED + "Warning", ChatColor.RED + "This reverts after a restart.");
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            return true;
        }
        return false;
    }
}
