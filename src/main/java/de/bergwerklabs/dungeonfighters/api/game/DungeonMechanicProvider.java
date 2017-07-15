package de.bergwerklabs.dungeonfighters.api.game;

import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;

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
    HashSet<String> getChunks();

    /**
     *
     */
    DungeonFighter getFighter();

    /**
     *
     */
    String getId();

    /**
     *
     * @param chunks
     */
    void assignChunks(HashSet<String> chunks);

    /**
     *
     * @param fighter
     */
    void assignPlayer(DungeonFighter fighter);

    /**
     *
     */
    void start();

    /**
     *
     */
    void stop();
}
