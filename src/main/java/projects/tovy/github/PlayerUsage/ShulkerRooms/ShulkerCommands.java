package projects.tovy.github.PlayerUsage.ShulkerRooms;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import projects.tovy.github.Main;

public class ShulkerCommands implements CommandExecutor {
    private final ShulkerManagement shulkerManagement;
    private final Main main;
    private final FileConfiguration cnfg;

    public ShulkerCommands(ShulkerManagement shulkerManagement, Main main, FileConfiguration cnfg) {
        this.shulkerManagement = shulkerManagement;
        this.main = main;
        this.cnfg = cnfg;
    }
    private boolean deleteRoomsFlag = false;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("shulker")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length == 2 && p.hasPermission("noarbox.operator.usage")) {
                    if (args[0].equalsIgnoreCase("set")) {
                        try {
                            int roomId = Integer.parseInt(args[1]);
                            shulkerManagement.setRoom(p.getLocation(), roomId);
                            p.sendTitle(ChatColor.BOLD.BLUE + "Success!", ChatColor.BLUE + "You have set room " + roomId + " to your current location!", 20, 100, 20);
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 1);
                        } catch (NumberFormatException e) {
                            p.sendTitle(ChatColor.BOLD.BLUE + "Error", ChatColor.BLUE + "You need to provide a valid id", 20, 100, 20);
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
                        }
                    } else if (args[0].equalsIgnoreCase("del")) {
                        if (args[1].equalsIgnoreCase("all")) {
                            if (!deleteRoomsFlag) {
                                deleteRoomsFlag = true;
                                p.sendTitle(ChatColor.DARK_RED + "Warning", ChatColor.RED + "You are about to delete every room! To confirm, run the command again");
                                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 2, 1);
                            } else {
                                shulkerManagement.removeAllRooms();
                                deleteRoomsFlag = false;
                                p.sendTitle(ChatColor.BOLD.BLUE + "Success", ChatColor.BLUE + "All rooms have been deleted", 20, 100, 20);
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, 1);
                            }
                        } else {
                            try {
                                int roomIdToDelete = Integer.parseInt(args[1]);
                                if (shulkerManagement.removeRoom(roomIdToDelete)) {
                                    p.sendMessage(ChatColor.GREEN + "Room " + roomIdToDelete + " has been deleted.");
                                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, 1);
                                } else {
                                    p.sendMessage(ChatColor.RED + "Room " + roomIdToDelete + " not found.");
                                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
                                }
                            } catch (NumberFormatException e) {
                                p.sendMessage(ChatColor.RED + "You need to provide a valid room id.");
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
                            }
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Usage: /shulker set <id>, /shulker del <id>, /shulker del all");
                    }
                } else if (args.length == 0) {
                    findRoom(p);
                } else {
                    p.sendMessage(ChatColor.RED + "No available rooms.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            }
        }
        return true;
    }

    private int findAvailableRoom() {
        for (int roomId : shulkerManagement.getAllRoomIds()) {
            if (!shulkerManagement.isRoomOccupied(roomId)) {
                return roomId;
            }
        }
        return -1;
    }

    private void findRoom(Player p) {
        int availableRoom = findAvailableRoom();
        if (availableRoom != -1) {
            p.teleport(shulkerManagement.getRoom(availableRoom));
            p.sendMessage(ChatColor.BLUE + "You have been teleported to shulker room " + availableRoom);
            shulkerManagement.setRoomOccupied(availableRoom, true);
            main.sendtoPermission(p.getName() + " went into a shulker room", "noarbox.operator.usage", p);
        } else {
            p.sendMessage(ChatColor.RED + "No available rooms.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
        }
    }
}
