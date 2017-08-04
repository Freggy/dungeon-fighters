package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import org.bukkit.Location;

/**
 * Created by Yannic Rieger on 01.08.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ActivationLine {

    public int getZValue() { return this.zValue; }

    private int zValue;

    public ActivationLine(int zValue) {
        this.zValue = zValue;
    }

    /**
     *
     * @param playerLocation
     * @return
     */
    public boolean activated(Location playerLocation) {
        return playerLocation.getBlockZ() - this.zValue >= 2;
    }
}
