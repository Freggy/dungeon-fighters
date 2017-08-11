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
public class BuildResult {

    /**
     *
     */
    public DungeonMechanicProvider getProvider() {
        return provider;
    }


    private DungeonMechanicProvider provider;
    private Location thisBuildLocation;
    private LabsSchematic<ModuleMetadata> module;

    /**
     * @param provider
     * @param thisBuildLocation
     */
    public BuildResult(DungeonMechanicProvider provider, Location thisBuildLocation, LabsSchematic<ModuleMetadata> module) {
        this.provider = provider;
        this.thisBuildLocation = thisBuildLocation;
        this.module = module;
    }

    public Location getBuildLocation() {
        return thisBuildLocation;
    }

    public LabsSchematic<ModuleMetadata> getModule() {
        return module;
    }
}
