package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import org.bukkit.ChunkSnapshot;

import java.util.HashSet;

/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public abstract class BaseMechanic implements DungeonMechanicProvider {

    @Override
    public HashSet<ChunkSnapshot> getChunks() {
        return this.chunks;
    }

    @Override
    public DungeonFighter getFighter() {
        return this.fighter;
    }

    protected HashSet<ChunkSnapshot> chunks;
    protected DungeonFighter fighter;

    @Override
    public void assignChunks(HashSet<ChunkSnapshot> chunks) {
        this.chunks = chunks;
    }

    @Override
    public void assignPlayer(DungeonFighter fighter) {
        this.fighter = fighter;
    }
}
