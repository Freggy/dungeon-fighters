package de.bergwerklabs.dungeonfighters.game.core;

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

    /**
     *
     * @param gridOrigin
     * @return
     */
    public static Chunk[][] loadChunks(Location gridOrigin) {
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

    public static void placeModule(int row, int column) {

    }
}
