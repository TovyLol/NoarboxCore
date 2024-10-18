package projects.tovy.github.PlayerUsage.Warps;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import projects.tovy.github.ItemHandeling;
import projects.tovy.github.Main;
import projects.tovy.github.EasyGuiBorder; // Import EasyGuiBorder

import java.util.ArrayList;
import java.util.List;

public class WarpCMD implements CommandExecutor, Listener {
    private final WarpManager warpManager;
    private final FileConfiguration config;
    private final String p;
    private final String error;
    private final Main main;
    private final EasyGuiBorder guiBorder;

    public WarpCMD(Main main, WarpManager warpManager) {
        this.config = main.getPluginConfig();
        this.p = config.getString("prefix");
        this.error = config.getString("errormsg");
        this.main = main;
        this.warpManager = warpManager;
        this.guiBorder = new EasyGuiBorder();
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(p + ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player pl = (Player) sender;

        if (command.getName().equalsIgnoreCase("warp")) {
            if (args.length == 0) {
                openWarpGui(pl);
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    openWarpGui(pl);
                } else {
                    if (warpManager.warpExists(args[0])) {
                        if (pl.hasPermission("noarbox.operator.usage")) {
                            warpManager.teleportToWarp(pl, args[0]);
                            pl.sendMessage(ChatColor.GREEN + "Warped to '" + args[0] + "' successfully!");
                        } else {
                            main.noPermission(sender);
                        }
                    } else {
                        pl.sendMessage(error);
                    }
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
                            pl.sendMessage(error);
                        }
                    } else {
                        main.noPermission(sender);
                    }
                } else {
                    pl.sendMessage(ChatColor.RED + "Usage: /warp set <name>, /warp del <name>, /warp list");
                }
            }
        }
        return true;
    }

    private void openWarpGui(Player player) {
        List<WarpManager.WarpStatus> warpStatuses = warpManager.getWarpsWithStatus(player);

        Inventory gui = guiBorder.openGuiBorder(player, 54, "Warps");

        for (WarpManager.WarpStatus warpStatus : warpStatuses) {
            ItemStack item;
            if (warpStatus.isUnlocked()) {
                item = new ItemStack(Material.GREEN_DYE);
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(ChatColor.GREEN + warpStatus.getWarp().getName());
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add("");
                    lore.add(ChatColor.YELLOW + "Right click to warp");
                    meta.setLore(lore);
                        item.setItemMeta(meta);
                }
            } else {
                item = new ItemStack(Material.RED_DYE);
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(ChatColor.RED + warpStatus.getWarp().getName());
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add("");
                    lore.add(ChatColor.DARK_RED + "LOCKED");
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
            }
            gui.addItem(item);
        }

        player.openInventory(gui);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("Warps")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            String warpName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            if (warpManager.warpExists(warpName)) {
                warpManager.teleportToWarp(p, warpName);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                p.sendMessage(config.getString("prefix") + ChatColor.WHITE + " Warped to " + warpName);
                p.closeInventory();
            } else {
                main.errorMessage(p);
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 1);
            }
        }
    }
}

