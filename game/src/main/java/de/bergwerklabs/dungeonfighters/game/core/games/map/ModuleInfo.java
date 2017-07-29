package de.bergwerklabs.dungeonfighters.game.core.games.map;

import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Location;

import java.util.List;

/**
 * Created by Yannic Rieger on 18.07.2017.
 * <p> Class contains basic info about a Module of a {@link de.bergwerklabs.dungeonfighters.api.game.DungeonGame}.
 *
 * @author Yannic Rieger
 */
public class ModuleInfo {

    /**
     * Gets the locations where to close the module.
     */
    public List<Location> getBlockLocations() {
        return blockLocations;
    }

    /**
     * Gets the {@link LabsSchematic} that represents the module.
     */
    public LabsSchematic getSchematic() {
        return schematic;
    }

    private List<Location> blockLocations;
    private LabsSchematic schematic;

    /**
     * @param blockLocations Locations where to close the module.
     * @param schematic {@link LabsSchematic} that represents the module.
     */
    public ModuleInfo(List<Location> blockLocations, LabsSchematic schematic) {
        this.blockLocations = blockLocations;
        this.schematic = schematic;
    }
}
