package de.bergwerklabs.dungeonfighters.api.game;

import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import org.bukkit.ChunkSnapshot;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public abstract class DungeonGame extends JavaPlugin implements DungeonMechanicProvider {

    @Override
    public HashSet<ChunkSnapshot> getChunks() {
        return this.chunks;
    }

    @Override
    public DungeonFighter getFighter() {
        return this.fighter;
    }

    private HashSet<ChunkSnapshot> chunks;
    private DungeonFighter fighter;

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void assignChunks(HashSet<ChunkSnapshot> chunks) {
        this.chunks = chunks;
    }

    @Override
    public void assignPlayer(DungeonFighter fighter) {
        this.fighter = fighter;
    }
}
