package projects.tovy.github.ServerUsage.KillEffects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import projects.tovy.github.Main;

public class Effects {
    private KeMain ke;

    private Main main;

    public Effects (KeMain ke, Main main) {
        this.ke = ke;
        this.main = main;
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
    public void swordEffect(Location attackerLocation, Location deathLocation) {
        Location giantLocation = deathLocation.clone().add(0, 7, 0);
        Giant gaint = (Giant) deathLocation.getWorld().spawnEntity(deathLocation, EntityType.GIANT);

        gaint.setAI(false);
        gaint.setInvisible(true);
        gaint.setInvulnerable(true);
        gaint.setCustomName("dinnerbone");
        gaint.setCustomNameVisible(false);
        gaint.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
        Vector direction = deathLocation.toVector().subtract(giantLocation.toVector()).normalize();
        Location lookAt = giantLocation.clone().setDirection(direction);
        new BukkitRunnable() {
            @Override
            public void run() {
                gaint.remove();
            }
        }.runTaskLater(main, 100L);

    }

    //miss meer idk

}
