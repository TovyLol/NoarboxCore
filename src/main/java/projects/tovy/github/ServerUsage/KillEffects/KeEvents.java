package projects.tovy.github.ServerUsage.KillEffects;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;
import projects.tovy.github.DataBase.KEDataBase;
import projects.tovy.github.ServerUsage.KillEffects.Effects;

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
        try (Connection conn = keDataBase.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player_effects WHERE player_uuid = ?");
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                boolean totemEnabled = rs.getBoolean("totem_enabled");
                boolean bleedEnabled = rs.getBoolean("bleed_enabled");
                boolean rageEnabled = rs.getBoolean("rage_enabled");
                boolean loveEnabled = rs.getBoolean("love_enabled");

                // Play corresponding effects based on enabled effects
                if (totemEnabled) {
                    effects.totemEffect(player.getLocation(), player.getLocation()); // For simplicity, attacker and death locations are same
                }
                if (bleedEnabled) {
                    effects.bleedEffect(player.getLocation(), player.getLocation());
                }
                if (rageEnabled) {
                    effects.rageEffect(player.getLocation(), player.getLocation());
                }
                if (loveEnabled) {
                    effects.loveEffect(player.getLocation(), player.getLocation());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
