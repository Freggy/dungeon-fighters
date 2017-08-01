package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import de.bergwerklabs.framework.commons.spigot.general.Tuple;
import org.bukkit.Location;

import java.util.HashSet;

/**
 * Created by Yannic Rieger on 01.08.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ActivationLine {

    private HashSet<Tuple<Integer, Integer>> line;

    public ActivationLine(HashSet<Tuple<Integer, Integer>> line) {
        this.line = line;
    }

    /**
     *
     * @param playerLocation
     * @return
     */
    public boolean activated(Location playerLocation) {
        Tuple<Integer, Integer> tuple = new Tuple<>(playerLocation.getBlockX(), playerLocation.getBlockZ());
        return this.line.contains(tuple);
    }
}
