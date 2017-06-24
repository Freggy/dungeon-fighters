package de.bergwerklabs.dungeonfighters.game.core.specialitems.arrow.trail;

import de.bergwerklabs.dungeonfighters.util.ParticleUtil;
import de.bergwerklabs.util.effect.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ArrowTrailTask implements Runnable {

    public static List<Projectile> launchedProjectiles = new CopyOnWriteArrayList<>();

    @Override
    public void run() {
        launchedProjectiles.forEach(projectile -> {
            TrailInfo trailInfo = (TrailInfo) projectile.getMetadata("trail").get(0).value();

            Particle particle = ParticleUtil.createParticle(trailInfo.getEffect(),
                                                            projectile.getLocation(),
                                                            trailInfo.getdX(), trailInfo.getdY(), trailInfo.getdZ(),
                                                            trailInfo.getSpeed(), trailInfo.getCount());

            projectile.getNearbyEntities(100, 100, 100).forEach(entity -> {
                if (entity instanceof Player) {
                    ParticleUtil.sendParticleToPlayer(particle, (Player) entity);
                }
            });
        });
    }
}
