package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow.trail;

import de.bergwerklabs.util.effect.Particle;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p> Enum contains every arrow trail available.
 *
 * @author Yannic Rieger
 */
public enum TrailType {

    POISON(new TrailInfo(Particle.ParticleEffect.VILLAGER_HAPPY, 0.1F, 0.1F, 0.1F, 0, 3)),
    FIRE(new TrailInfo(Particle.ParticleEffect.FLAME, 0.1F, 0.1F, 0.1F, 0, 3)),
    EXPLOSION(new TrailInfo(Particle.ParticleEffect.CLOUD, 0.1F, 0.1F, 0.1F, 0, 3)),
    HEART(new TrailInfo(Particle.ParticleEffect.HEART, 0.0F, 0.0F, 0.0F, 0, 1));

    /**
     * Gets information about the arrow trail.
     */
    public TrailInfo getInfo() {
        return info;
    }

    private TrailInfo info;

    /**
     * @param info Information about the arrow trail.
     */
    TrailType(TrailInfo info) {
        this.info = info;
    }
}
