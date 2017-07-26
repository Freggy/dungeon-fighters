package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow.trail;

import de.bergwerklabs.util.effect.Particle;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public enum TrailType {

    POISON(new TrailInfo(Particle.ParticleEffect.VILLAGER_HAPPY, 0.1F, 0.1F, 0.1F, 0, 3)),
    FIRE(new TrailInfo(Particle.ParticleEffect.FLAME, 0.1F, 0.1F, 0.1F, 0, 3)),
    EXPLOSION(new TrailInfo(Particle.ParticleEffect.CLOUD, 0.1F, 0.1F, 0.1F, 0, 3)),
    HEART(new TrailInfo(Particle.ParticleEffect.HEART, 0.0F, 0.0F, 0.0F, 0, 1));

    /**
     *
     */
    public TrailInfo getInfo() {
        return info;
    }

    private TrailInfo info;

    TrailType(TrailInfo info) {
        this.info = info;
    }
}
