package projects.tovy.github.PlayerUsage.DeathStashes;

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
    private FileConfiguration cnfg;
    private Main main;

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
            p.sendMessage(cnfg.getString("prefix") +"&fDeath stash has been " + (enabled ? "disabled" : "enabled") + ".");
            main.sendTitle(p, "&cWarning", "&cThis reverts after a restart.");
            p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            return true;
        }
        return false;
    }

}
