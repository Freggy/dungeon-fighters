package de.bergwerklabs.dungeonfighters.game.map;

import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by Yannic Rieger on 29.05.2017.
 * <p>  </p>
 * @author Yannic Rieger
 */
public class DungeonLoader {

    private Chunk[][] chunks;

    /**
     *
     * @param gridOrigin
     */
    public DungeonLoader(Location gridOrigin) {
        this.chunks = this.loadChunks(gridOrigin);
    }


    public void prepareMap(Dungeon dungeon) {

    }

    /**
     *
     * @param row
     * @param column
     * @param schematic
     */
    public void placeModule(int row, int column, LabsSchematic schematic) {
        schematic.pasteAsync("dungeon", this.chunks[row][column].getBlock(0,0,0).getLocation().toVector());
    }

    /**
     *
     * @param gridOrigin
     * @return
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
