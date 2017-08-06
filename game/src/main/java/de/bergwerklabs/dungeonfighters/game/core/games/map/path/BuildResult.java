package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Location;

/**
 * Created by Yannic Rieger on 01.08.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class BuildResult<T extends ModuleMetadata> {

    /**
     *
     */
    public DungeonMechanicProvider getProvider() {
        return provider;
    }


    private DungeonMechanicProvider provider;
    private Location thisBuildLocation;
    private LabsSchematic<T> module;

    /**
     * @param provider
     * @param thisBuildLocation
     */
    public BuildResult(DungeonMechanicProvider provider, Location thisBuildLocation, LabsSchematic<T> module) {
        this.provider = provider;
        this.thisBuildLocation = thisBuildLocation;
        this.module = module;
    }

    public Location getBuildLocation() {
        return thisBuildLocation;
    }

    public LabsSchematic<T> getModule() {
        return module;
    }
}
