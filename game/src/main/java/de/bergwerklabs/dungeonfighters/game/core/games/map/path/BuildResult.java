package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import org.bukkit.Location;

/**
 * Created by Yannic Rieger on 01.08.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class BuildResult {

    /**
     *
     */
    public DungeonMechanicProvider getProvider() {
        return provider;
    }

    /**
     *
     */
    public Location getNextBuildLocation() {
        return nextBuildLocation;
    }

    private DungeonMechanicProvider provider;
    private Location nextBuildLocation;

    /**
     * @param provider
     * @param nextBuildLocation
     */
    public BuildResult(DungeonMechanicProvider provider, Location nextBuildLocation) {
        this.provider = provider;
        this.nextBuildLocation = nextBuildLocation;
    }
}
