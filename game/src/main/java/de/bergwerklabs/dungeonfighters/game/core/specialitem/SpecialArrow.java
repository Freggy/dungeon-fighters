package de.bergwerklabs.dungeonfighters.game.core.specialitem;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p> Base class for special arrows.
 *
 * @author Yannic Rieger
 */
public abstract class SpecialArrow {

    /**
     * Gets whether or not {@link SpecialArrow#groundHit(Location)} should be executed when the arrow hit the ground.
     */
    public boolean executeOnGround() { return false; }

    /**
     * Executes when an Arrow hits a player.
     *
     * @param player Player that was hit by an {@code SpecialArrow}.
     */
    public abstract void playerHit(Player player);

    /**
     * Executes when an arrow hits the ground.
     *
     * @param location Location where the arrow hit the ground.
     */
    public abstract void groundHit(Location location);
}
