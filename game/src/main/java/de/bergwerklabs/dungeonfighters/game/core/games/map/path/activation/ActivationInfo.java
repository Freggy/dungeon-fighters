package de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BuildResult;

public class ActivationInfo {

    private DungeonMechanicProvider provider;
    private BuildResult connection;
    private BuildResult providerModule;
    public int id; // FOR TESTING

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
}
