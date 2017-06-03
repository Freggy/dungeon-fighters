package de.bergwerklabs.dungeonfighters.game.map;

import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by Yannic Rieger on 29.05.2017.
 * <p> Class  providing methods for placing modules on the grid. </p>
 * @author Yannic Rieger
 */
public class DungeonLoader {

    private Chunk[][] chunks;

    /**
     * @param gridOrigin Origin of the grid. Has to be the Upper left corner.
     */
    public DungeonLoader(Location gridOrigin) {
        this.chunks = this.loadChunks(gridOrigin);
    }

    /**
     * Prepares the grid for the game.
     * @param dungeon Current map that wil be played.
     */
    public void prepareMap(Dungeon dungeon) {

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                //dungeon.getGrid()[row][column]
            }
        }
    }

    /**
     * Places a module on the grid.
     * @param row Grid row
     * @param column Grid column
     * @param schematic Schematic of the module that will be placed.
     */
    private void placeModule(int row, int column, LabsSchematic schematic) {
        schematic.pasteAsync("dungeon", this.chunks[row][column].getBlock(0,0,0).getLocation().toVector());
    }

    /**
     * Creates a 2 dimensional array of chunks representing the grid.
     * @param gridOrigin Upper left corner of the grid.
     * @return 2 dimensional array of chunks representing the grid.
     */
    private Chunk[][] loadChunks(Location gridOrigin) {
        Chunk[][] chunks = new Chunk[10][10]; // TODO: use config
        World dungeon = Bukkit.getWorld("dungeon");

        int originRow = gridOrigin.getChunk().getX();
        int originColumn = gridOrigin.getChunk().getZ();

        for (int row = 0; row < chunks.length; row++) {
            for (int column = 0; column < chunks.length; column++) {
                chunks[row][column] = dungeon.getChunkAt(originRow + row, originColumn + column);
            }
        }
        return chunks;
    }
}
