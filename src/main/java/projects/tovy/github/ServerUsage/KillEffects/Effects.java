package projects.tovy.github.ServerUsage.KillEffects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Effects {
    private KeMain ke;

    public Effects (KeMain ke) {
        this.ke = ke;
    }

    public void totemEffect(Location attackerLocation, Location deathLocation) {
        attackerLocation.getWorld().playSound(attackerLocation, Sound.ITEM_TOTEM_USE, 1, 1);
        deathLocation.getWorld().spawnParticle(Particle.TOTEM, deathLocation, 50, 0.0, 0.0, 0.0, 0.0);
    }

    public void bleedEffect(Location attackerLocation, Location deathLocation) {
        attackerLocation.getWorld().playSound(attackerLocation, Sound.ENTITY_BLAZE_DEATH, 1, 1);
        deathLocation.getWorld().spawnParticle(Particle.DRIP_LAVA, deathLocation, 20, 0.0, 0.0, 0.0, 0.0);
        deathLocation.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, deathLocation, 5, 0.0, 0.2, 0.0, 0.0);
    }

    public void rageEffect(Location attackerLocation, Location deathLocation) {
        attackerLocation.getWorld().playSound(attackerLocation, Sound.ENTITY_POLAR_BEAR_WARNING, 3, 1);
        deathLocation.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, deathLocation, 10, 0.0, 0.2, 0.0, 0.0);
    }

    public void loveEffect(Location attackerLocation, Location deathLocation) {
        attackerLocation.getWorld().playSound(attackerLocation, Sound.ENTITY_PLAYER_LEVELUP, 3, 1);
        deathLocation.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, deathLocation, 10, 0.0, 0.2, 0.0, 0.0);
        deathLocation.getWorld().spawnParticle(Particle.HEART, deathLocation, 10, 0.0, 0.2, 0.0, 0.0);
    }

    //miss meer idk

}
