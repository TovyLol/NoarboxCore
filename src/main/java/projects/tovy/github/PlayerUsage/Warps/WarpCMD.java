package projects.tovy.github.PlayerUsage.Warps;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import projects.tovy.github.Main;

public class WarpCMD implements CommandExecutor {
    private final WarpManager warpManager;
    private final FileConfiguration config;
    private final String p;
    private final String error;
    private final Main main;

    public WarpCMD(Main main, WarpManager warpManager) {
        this.config = main.getPluginConfig();
        this.p = config.getString("prefix");
        this.error = config.getString("errormsg");
        this.main = main;
        this.warpManager = warpManager;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage( p + ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player pl = (Player) sender;

        if (command.getName().equalsIgnoreCase("warp")) {
            if (args.length == 0) {
                if (pl.hasPermission("noarbox.operator.usage")) {
                    pl.sendMessage(p + ChatColor.WHITE + "Use /warp + the thing you wanna do");
                } else {
                    main.noPermission(sender);
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (pl.hasPermission("noarbox.operator.usage")) {
                        warpManager.listWarps(pl);
                    } else {
                        main.noPermission(sender);
                    }
                } else {
                    pl.sendMessage(error);
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (pl.hasPermission("noarbox.operator.usage")) {
                        String warpName = args[1];
                        warpManager.setWarp(warpName, pl.getLocation());
                        pl.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' set successfully!");
                    } else {
                        main.noPermission(sender);
                    }
                } else if (args[0].equalsIgnoreCase("del")) {
                    if (pl.hasPermission("noarbox.operator.usage")) {
                        String warpName = args[1];
                        if (warpManager.warpExists(warpName)) {
                            warpManager.deleteWarp(warpName);
                            pl.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' deleted successfully!");
                        } else {
                            main.noPermission(sender);
                        }
                    } else {
                        pl.sendMessage(error);
                    }
                } else {
                    pl.sendMessage(ChatColor.RED + "Usage: /warp set <name>, /warp del <name>, /warp list");
                }
            }
        }
        return true;
    }
}
