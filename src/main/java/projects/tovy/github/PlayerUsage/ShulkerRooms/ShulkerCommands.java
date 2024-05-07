package projects.tovy.github.PlayerUsage.ShulkerRooms;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShulkerCommands implements CommandExecutor {
    private final ShulkerManagment shulkerManagment;
    public ShulkerCommands  (ShulkerManagment shulkerManagment) {
        this.shulkerManagment = shulkerManagment;
    }
    private boolean deleteRoomsFlag = false;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("shulker")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    if (player.hasPermission("noarbox.operator.usage")) {
                        if (args[0].equalsIgnoreCase("set")) {
                            try {
                                int roomId = Integer.parseInt(args[1]);
                                shulkerManagment.setRoom(player.getLocation(), roomId);
                                player.sendTitle(ChatColor.BOLD.BLUE + "Succes!", ChatColor.BLUE + "You have set room " + roomId + "to your current location!", 20, 100, 20);
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 1);
                            } catch (NumberFormatException e) {
                                player.sendTitle(ChatColor.BOLD.BLUE + "Error", ChatColor.BLUE + "You need to have a valid id", 20, 100, 20);
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
                            }

                        } else if (args[0].equalsIgnoreCase("del")) {
                            if (args[1].equalsIgnoreCase("all")) {
                                if (!deleteRoomsFlag) {
                                    deleteRoomsFlag = true;
                                    player.sendTitle(ChatColor.DARK_RED + "Warning", ChatColor.RED + "Your about to delete every room! To confirm run the command again");
                                    player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 2, 1);
                                    return true;
                                } else {
                                    shulkerManagment.removeAllRooms();
                                    deleteRoomsFlag = false;
                                    player.sendTitle(ChatColor.BOLD.BLUE + "Succes", ChatColor.BLUE + "All rooms have been deleted");
                                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, 1);
                                    return true;
                                }
                            } else {
                                int roomIdToDelete;
                                try {
                                    roomIdToDelete = Integer.parseInt(args[2]);
                                } catch (NumberFormatException e) {
                                    player.sendMessage(ChatColor.BLUE + "You will have to use a valid room id");
                                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
                                    return true;
                                }
                                if (shulkerManagment.removeRoom(roomIdToDelete)) {
                                    player.sendMessage("Room " + roomIdToDelete + " has been deleted.");

                                } else {
                                    player.sendMessage("Room " + roomIdToDelete + " not found.");
                                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
                                }
                            }
                        } else {
                            for (ProtectedRegion region : WorldGuard.getInstance()
                                    .getPlatform()
                                    .getRegionContainer()
                                    .createQuery()
                                    .getApplicableRegions(BukkitAdapter.adapt(player.getLocation()))) {

                                if (!region.getId().equalsIgnoreCase("shulkerregion")) {
                                    continue;
                                }


                                player.teleport(new org.bukkit.Location(player.getWorld(), 100, 100, 100));
                                return true;
                            }
                            if (args.length == 0) {
                                int availableRoom = findAvailableRoom();
                                if (availableRoom != -1) {
                                    player.teleport(shulkerManagment.getRoom(availableRoom));
                                }
                                player.sendMessage(ChatColor.BLUE + "You got teleported to shulkerroom " + availableRoom);
                                shulkerManagment.setRoomOccupied(availableRoom, true);
                            } else {
                                player.sendMessage(ChatColor.RED + "No available rooms.");
                            }
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Only players can use this command.");
                    return true;
                }
            }
        }
        return false;
    }



    //code that gets run to check the rooms
    private int findAvailableRoom() {
        for (int roomId : shulkerManagment.getRooms().keySet()) {
            if (!shulkerManagment.isRoomOccupied(roomId)) {
                return roomId;
            }
        }
        return -1;
    }
}
