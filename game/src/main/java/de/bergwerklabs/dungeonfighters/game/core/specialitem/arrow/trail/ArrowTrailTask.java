package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow.trail;

import de.bergwerklabs.dungeonfighters.commons.ParticleUtil;
import de.bergwerklabs.util.effect.Particle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>
 * Task that displays the arrow trails particles.
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

            Bukkit.getOnlinePlayers().forEach(player -> ParticleUtil.sendParticleToPlayer(particle, player));
        });
    }
}
