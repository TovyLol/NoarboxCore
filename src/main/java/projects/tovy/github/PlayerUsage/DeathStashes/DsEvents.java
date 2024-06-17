package projects.tovy.github.PlayerUsage.DeathStashes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import projects.tovy.github.DataBase.DsDataBase;
import projects.tovy.github.ItemHandeling;
import projects.tovy.github.Main;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DsEvents implements Listener {
    private final Main plugin;
    private DsDataBase dsdb;
    private FileConfiguration cnfg;
    private ItemHandeling item;
    private static final long EXPIRE_TIME = 2 * 24 * 60 * 60 * 1000L;
    private final Map<UUID, Boolean> stashEnabled = new HashMap<>();

    public DsEvents(Main plugin, DsDataBase dsdb, FileConfiguration cnfg, ItemHandeling item) {
        this.plugin = plugin;
        this.dsdb = dsdb;
        this.cnfg = cnfg;
        this.item = item;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();

        // exeption
        if (victim.getName().equals("MommyMiningASMR")) {
            saveStash(victim, killer);
            return;
        }
        //default
        if (killer != null && stashEnabled.getOrDefault(killer.getUniqueId(), false)) {
            saveStash(victim, killer);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player p = e.getPlayer();
            ItemStack item = p.getInventory().getItemInMainHand();
            if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().contains(ChatColor.RED + "Death Stash")) {
                e.setCancelled(true);
                handleStashInteraction(p, item);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains(ChatColor.RED + "Death Stash")) {
            e.setCancelled(true);
        }
    }

    private void saveStash(Player victim, Player killer) {
        UUID killerUUID = killer != null ? killer.getUniqueId() : victim.getUniqueId();
        ItemStack[] contents = victim.getInventory().getContents();
        long timestamp = System.currentTimeMillis();

        dsdb.saveStash(killerUUID.toString(), serializeInventory(contents), timestamp);

        ItemStack stashItem = new ItemStack(Material.CHEST);
        ItemMeta meta = stashItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Death Stash: " + victim.getName());
        meta.setLore(List.of(
                ChatColor.GRAY + "This death stash isn't expired yet!",
                ChatColor.GRAY + "Right-Click to claim the items",
                ChatColor.GRAY + "Shift Right-Click to preview the items"));
        stashItem.setItemMeta(meta);

        // Drop the stash item
        Location deathLocation = victim.getLocation();
        deathLocation.getWorld().dropItemNaturally(deathLocation, stashItem);
    }

    private void handleStashInteraction(Player p, ItemStack item) {
        UUID playerUUID = p.getUniqueId();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dsdb.getConnection();
            statement = connection.prepareStatement("SELECT stash, timestamp FROM stashes WHERE uuid = ?");
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                byte[] stashData = resultSet.getBytes("stash");
                long timestamp = resultSet.getLong("timestamp");

                if (System.currentTimeMillis() - timestamp < EXPIRE_TIME) {
                    if (p.isSneaking()) {
                        openStashPreview(p, stashData);
                    } else {
                        claimStash(p, stashData);
                        dsdb.removeStash(playerUUID.toString());
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', cnfg.getString("prefix") + "&fThis Stash has been expired"));
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Note: " + ChatColor.RESET + "Stashes expire after 2 days!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void openStashPreview(Player p, byte[] stashData) {
        Inventory stashPreview = Bukkit.createInventory(null, 54, ChatColor.RED + "Death Stash Preview");
        ItemStack[] items = deserializeInventory(stashData);
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                stashPreview.setItem(i, items[i]);
            }
        }
        p.openInventory(stashPreview);
    }

    private void claimStash(Player p, byte[] stashData) {
        ItemStack[] items = deserializeInventory(stashData);
        for (ItemStack item : items) {
            if (item != null) {
                p.getInventory().addItem(item);
            }
        }
        p.sendMessage(ChatColor.GREEN + "You have claimed your death stash!");
    }

    private byte[] serializeInventory(ItemStack[] items) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(items);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return new byte[0];
    }

    private ItemStack[] deserializeInventory(byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (ItemStack[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ItemStack[0];
    }
}
