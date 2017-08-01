package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import de.bergwerklabs.framework.commons.spigot.general.Tuple;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Location;

import java.util.HashSet;
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

    /**
     *
     */
    public HashSet<Tuple<Integer, Integer>> getActivationLine() { return this.activationLine; }

    private List<Location> blockLocations;
    private HashSet<Tuple<Integer, Integer>> activationLine;
    private LabsSchematic schematic;

    /**
     * @param blockLocations Locations where to close the module.
     * @param schematic {@link LabsSchematic} that represents the module.
     */
    public ModuleInfo(List<Location> blockLocations, LabsSchematic schematic, HashSet<Tuple<Integer, Integer>> activationLine) {
        this.blockLocations = blockLocations;
        this.schematic = schematic;
        this.activationLine = activationLine;
    }
}
