package de.bergwerklabs.dungeonfighters.game.core.games.map;

import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Location;

import java.util.List;

/**
 * Created by Yannic Rieger on 18.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ModuleInfo {

    private List<Location> blockLocations;
    private LabsSchematic schematic;

    public ModuleInfo(List<Location> blockLocations, LabsSchematic schematic) {
        this.blockLocations = blockLocations;
        this.schematic = schematic;
    }

    public List<Location> getBlockLocations() {
        return blockLocations;
    }

    public LabsSchematic getSchematic() {
        return schematic;
    }
}
