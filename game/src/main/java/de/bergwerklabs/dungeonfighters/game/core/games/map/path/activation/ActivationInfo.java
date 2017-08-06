package de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BuildResult;

public class ActivationInfo<T extends ModuleMetadata> {

    private DungeonMechanicProvider provider;
    private BuildResult<T> connection;
    private BuildResult<T> providerModule;

    public ActivationInfo(DungeonMechanicProvider provider, BuildResult<T> connection, BuildResult<T> providerModule) {
        this.provider = provider;
        this.connection = connection;
        this.providerModule = providerModule;
    }


    public BuildResult<T> getProviderResult() {
        return providerModule;
    }

    public BuildResult<T> getConnectionResult() {
        return connection;
    }

    public DungeonMechanicProvider getProvider() {
        return provider;
    }
}
