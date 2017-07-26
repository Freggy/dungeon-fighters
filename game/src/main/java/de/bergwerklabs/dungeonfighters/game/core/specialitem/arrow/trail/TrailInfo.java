package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow.trail;

import de.bergwerklabs.util.effect.Particle;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
class TrailInfo {

    private Particle.ParticleEffect effect;
    private float dX, dY, dZ, speed;
    private int count;

    /**
     * @param effect
     * @param dX
     * @param dY
     * @param dZ
     * @param speed
     * @param count
     */
    TrailInfo(Particle.ParticleEffect effect, float dX, float dY, float dZ, float speed, int  count) {
        this.effect = effect;
        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
        this.speed = speed;
        this.count = count;
    }

    int getCount() {
        return count;
    }

    float getdX() {
        return dX;
    }

    float getdY() {
        return dY;
    }

    float getdZ() {
        return dZ;
    }

    float getSpeed() {
        return speed;
    }

    Particle.ParticleEffect getEffect() {
        return effect;
    }
}
