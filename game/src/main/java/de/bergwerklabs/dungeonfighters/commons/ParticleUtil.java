package de.bergwerklabs.dungeonfighters.commons;

import de.bergwerklabs.util.effect.Particle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 04.05.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ParticleUtil {

    /**
     *
     * @param effect
     * @param location
     * @param dX
     * @param dY
     * @param dZ
     * @param speed
     * @param count
     * @return
     */
    public static Particle createParticle(Particle.ParticleEffect effect, Location location, float dX, float dY, float dZ, float speed, int count) {
        try {
            return new Particle(effect, location, dX, dY, dZ, speed, count);
        }
        catch (Exception ex) {
            Bukkit.getLogger().info("Something went wrong while creating a particle");
            ex.getStackTrace();
            return null;
        }
    }

    public static void sendParticleToPlayer(Particle particle, Player p) {
        try {
            particle.sendToPlayer(p);
        }
        catch (Exception ex) {
            Bukkit.getLogger().info("Something went wrong while sending a particle to " + p.getName());
            ex.getStackTrace();
        }
    }

}
