package de.bergwerklabs.dungeonfighters.api.game;

import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import org.bukkit.ChunkSnapshot;

import java.util.HashSet;

/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public interface DungeonMechanicProvider {

    /**
     *
     */
    HashSet<ChunkSnapshot> getChunks();

    /**
     *
     */
    DungeonFighter getFighter();

    /**
     *
     */
    short getLength();

    /**
     *
     * @param chunks
     */
    void assignChunks(HashSet<ChunkSnapshot> chunks);

    /**
     *
     * @param fighter
     */
    void assignPlayer(DungeonFighter fighter);

    /**
     *
     */
    void start();
}
