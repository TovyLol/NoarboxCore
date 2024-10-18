package projects.tovy.github.ServerUsage.KillEffects;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;
import projects.tovy.github.DataBase.KEDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KeEvents implements Listener {
    private final KEDataBase keDataBase;
    private final Effects effects;

    public KeEvents(KEDataBase keDataBase, Effects effects) {
        this.keDataBase = keDataBase;
        this.effects = effects;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        if (killer == null) {
            return;
        }

        try (Connection conn = keDataBase.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player_effects WHERE player_uuid = ?");
            stmt.setString(1, killer.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                boolean totemEnabled = rs.getBoolean("totem_enabled");
                boolean bleedEnabled = rs.getBoolean("bleed_enabled");
                boolean rageEnabled = rs.getBoolean("rage_enabled");
                boolean loveEnabled = rs.getBoolean("love_enabled");
                boolean swordEnabled = rs.getBoolean("sword_enabled");

                if (totemEnabled) {
                    effects.totemEffect(killer.getLocation(), player.getLocation());
                }
                if (bleedEnabled) {
                    effects.bleedEffect(killer.getLocation(), player.getLocation());
                }
                if (rageEnabled) {
                    effects.rageEffect(killer.getLocation(), player.getLocation());
                }
                if (loveEnabled) {
                    effects.loveEffect(killer.getLocation(), player.getLocation());
                }
                if (swordEnabled) {
                    effects.swordEffect(killer.getLocation(), player.getLocation());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            killer.sendMessage(ChatColor.BLUE + "Noarbox | " + ChatColor.WHITE + "An error occured whilst playing the KillEffect");
        }
    }
}
