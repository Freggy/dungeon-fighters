package de.bergwerklabs.dungeonfighters.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public abstract class SpecialArrow {

    public boolean executeOnGround() { return false; }

    public abstract void playerHit(Player player);

    public abstract void groundHit(Location location);
}
