package de.bergwerklabs.dungeonfighters.game.core.specialitems.arrow.trail;

import de.bergwerklabs.util.effect.Particle;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class TrailInfo {

    private Particle.ParticleEffect effect;
    private float dX, dY, dZ, speed;
    private int count;

    public TrailInfo(Particle.ParticleEffect effect, float dX, float dY, float dZ, float speed, int  count) {
        this.effect = effect;
        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
        this.speed = speed;
        this.count = count;
    }


    public int getCount() {
        return count;
    }

    public float getdX() {
        return dX;
    }

    public float getdY() {
        return dY;
    }

    public float getdZ() {
        return dZ;
    }

    public float getSpeed() {
        return speed;
    }

    public Particle.ParticleEffect getEffect() {
        return effect;
    }
}
