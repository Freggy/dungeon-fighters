package de.bergwerklabs.dungeonfighters.game.core.games.map;

import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.util.Vector;

/**
 *
 * @author Yannic Rieger
 */
public class DungeonGameModule {

    public LabsSchematic getSchematic() {
        return schematic;
    }

    public Vector getEnd() {
        return end;
    }

    public Vector getStart() {
        return start;
    }

    private Vector start, end;
    private LabsSchematic schematic;

    public DungeonGameModule(Vector start, Vector end, LabsSchematic schematic) {
        this.start = start;
        this.end = end;
        this.schematic = schematic;
    }
}