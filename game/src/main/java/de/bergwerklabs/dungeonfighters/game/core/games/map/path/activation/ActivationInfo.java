package de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BuildResult;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.DungeonWall;
import org.bukkit.Location;

public class ActivationInfo {

    private DungeonMechanicProvider provider;
    private BuildResult connection;
    private BuildResult providerModule;
    private DungeonWall wall;
    private boolean hasWall = false;

    /**
     *
     * @param provider
     * @param connection
     * @param providerModule
     */
    public ActivationInfo(DungeonMechanicProvider provider, BuildResult connection, BuildResult providerModule) {
        this.provider = provider;
        this.connection = connection;
        this.providerModule = providerModule;
    }

    public BuildResult getProviderResult() {
        return providerModule;
    }

    public BuildResult getConnectionResult() {
        return connection;
    }

    public DungeonMechanicProvider getProvider() {
        return provider;
    }

    public DungeonWall getWall() {
        return wall;
    }

    public boolean hasWall() {
        return hasWall;
    }

    public void createWall(Location pos1, Location pos2, Location placeLocation) {
        this.wall = new DungeonWall(Util.getDoorLocations(pos1, pos2), placeLocation, pos1);
        this.hasWall = true;
    }

    void setHasWall(boolean hasWall) {
        this.hasWall = hasWall;
    }
}
