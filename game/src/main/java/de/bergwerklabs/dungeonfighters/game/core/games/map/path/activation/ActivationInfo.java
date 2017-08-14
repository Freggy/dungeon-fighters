package de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation;

import com.sk89q.worldedit.regions.CuboidRegion;
import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BuildResult;
import org.bukkit.Location;

public class ActivationInfo {

    private DungeonMechanicProvider provider;
    private BuildResult connection;
    private BuildResult providerModule;
    private CuboidRegion cuboidRegion;
    private boolean hasWall = false;

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

    public CuboidRegion getWallRegion() {
        return cuboidRegion;
    }

    public boolean hasWall() {
        return hasWall;
    }

    public void createCuboid(Location pos1, Location pos2) {
        this.cuboidRegion = Util.getDoorLocations(pos1, pos2);
        this.hasWall = true;
    }

    void setHasWall(boolean hasWall) {
        this.hasWall = hasWall;
    }
}
