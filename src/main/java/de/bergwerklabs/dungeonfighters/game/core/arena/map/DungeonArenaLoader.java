package de.bergwerklabs.dungeonfighters.game.core.arena.map;

import de.bergwerklabs.dungeonfighters.Main;
import de.bergwerklabs.dungeonfighters.game.core.Dungeon;
import de.bergwerklabs.dungeonfighters.game.core.arena.fubar.GridCoordinate;
import de.bergwerklabs.dungeonfighters.game.core.arena.fubar.TileType;
import de.bergwerklabs.dungeonfighters.util.DungeonDestructionStrategy;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Yannic Rieger on 29.05.2017.
 * <p> Class  providing methods for placing modules on the grid.
 *
 * @author Yannic Rieger
 */
public class DungeonArenaLoader {

    /**
     *
     */
    public Chunk[] getChunks() { return chunks; }

    public List<Chunk> getDestructedChunks() { return this.destructedChunks; }

    private int maxCycles, cycle = 0;
    private Chunk[] chunks;
    private BukkitTask desturctionTask;
    private List<Chunk> destructedChunks = new CopyOnWriteArrayList<>();
    private static DungeonArenaLoader instance;

    public DungeonArenaLoader() {
        if (instance != null) return;
        instance = this;
    }

    /**
     * Creates a 2 dimensional array of chunks representing the grid.
     *
     * @param gridOrigin Upper left corner of the grid.
     */
    public void generate(Location gridOrigin, Dungeon dungeon) {
        World dungeonMap = Bukkit.getWorld("spawn");
        TileType[] grid = dungeon.getGrid();
        chunks = new Chunk[grid.length];

        int originRow = gridOrigin.getChunk().getX();
        int originColumn = gridOrigin.getChunk().getZ();

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                Chunk chunk = dungeonMap.getChunkAt(originColumn + column, originRow + row);
                TileType tile = grid[GridCoordinate.toIndex(row, column, 10)];

                if (tile != null) {
                    chunks[row * 10 + column] = chunk;
                    List<LabsSchematic> modules = dungeon.getModules().get(tile);
                    if (modules == null) {
                        System.out.println(tile);
                        continue;
                    }
                    this.placeModule(modules.get(0), chunk, gridOrigin.getBlockY() + 1);
                }
                else chunks[row * 10 + column] = null;
            }
        }
    }

    /**
     *
     */
    public void startDestructionSequence() {
        this.maxCycles = (10 / 2) - 2;
        this.desturctionTask = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            if (this.cycle == this.maxCycles) desturctionTask.cancel();

            List<Chunk> borderChunks = new ArrayList<>();
            borderChunks.addAll(this.calculateLeft(cycle));
            borderChunks.addAll(this.calculateBottom(cycle));
            borderChunks.addAll(this.calculateRight(cycle));
            borderChunks.addAll(this.calculateTop(cycle));
            this.destructedChunks.addAll(borderChunks);

            for (Chunk chunk : borderChunks) {
                DungeonDestructionStrategy strategy = new DungeonDestructionStrategy();
                strategy.destruct(chunk);
            }
            cycle++;
        }, 20 * 5, 20 * 5);
    }

    /**
     *
     * @param cycle
     * @return
     */
    private List<Chunk> calculateBottom(int cycle) {
        int startCol =  (cycle + 1);
        int startRow = (10 - 1) - cycle;
        return getColumnChunks(startCol, startRow, startRow);
    }

    /**
     *
     * @param cycle
     * @return
     */
    private List<Chunk> calculateTop(int cycle) {
        int startCol = cycle + 1;
        int endCol = (10 - 1) - cycle;
        return getColumnChunks(startCol, endCol, cycle);
    }

    /**
     *
     * @param cycle
     * @return
     */
    private List<Chunk> calculateLeft(int cycle) {
        int endRow = 10 - cycle;
        return this.getRowChunks(cycle, endRow, cycle);
    }

    /**
     *
     * @param cycle
     * @return
     */
    private List<Chunk> calculateRight(int cycle) {
        int endCol = (10 - 1) - cycle;
        return this.getRowChunks(cycle, endCol, endCol);
    }

    /**
     *
     * @param startCol
     * @param endCol
     * @param row
     * @return
     */
    private List<Chunk> getColumnChunks(int startCol, int endCol, int row) {
        List<Chunk> rowChunks = new ArrayList<>();
        for (int column = startCol; column <= endCol; column++) {
            Chunk chunk = this.chunks[GridCoordinate.toIndex(row, column, 10)];
            if (chunk != null)
                rowChunks.add(chunk);
        }
        return rowChunks;
    }

    /**
     *
     * @param startRow
     * @param endRow
     * @param column
     * @return
     */
    private List<Chunk> getRowChunks(int startRow, int endRow, int column) {
        List<Chunk> rowChunks = new ArrayList<>();
        for (int row = startRow; row < endRow; row++) {
            Chunk chunk = this.chunks[GridCoordinate.toIndex(row, column, 10)];
            if (chunk != null)
                rowChunks.add(chunk);
        }
        return rowChunks;
    }

    /**
     * Places a module on the grid.
     *
     * @param schematic Schematic of the module that will be placed.
     */
    private void placeModule(LabsSchematic schematic, Chunk chunk, int y) {
        schematic.pasteAsync("spawn", chunk.getBlock(0, y, 0).getLocation().toVector());
    }
}
