package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow.trail;

import de.bergwerklabs.util.effect.Particle;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p> Information about an arrow trail.
 *
 * @author Yannic Rieger
 */
class TrailInfo {

    /**
     * Gets the particle count.
     */
    int getCount() {
        return count;
    }

    /**
     * Gets the x offset.
     */
    float getdX() {
        return dX;
    }

    /**
     * Gets the y offset.
     */
    float getdY() {
        return dY;
    }

    /**
     * Gets the z offset.
     */
    float getdZ() {
        return dZ;
    }

    /**
     * Gets the speed of particle.
     */
    float getSpeed() {
        return speed;
    }

    /**
     * Gets the particle effect.
     */
    Particle.ParticleEffect getEffect() {
        return effect;
    }

    private Particle.ParticleEffect effect;
    private float dX, dY, dZ, speed;
    private int count;

    /**
     * @param effect Particle effect.
     * @param dX x offset
     * @param dY y offset
     * @param dZ z offset
     * @param speed Speed of the particle.
     * @param count Amount of particles to spawn.
     */
    TrailInfo(Particle.ParticleEffect effect, float dX, float dY, float dZ, float speed, int  count) {
        this.effect = effect;
        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
        this.speed = speed;
        this.count = count;
    }
}
