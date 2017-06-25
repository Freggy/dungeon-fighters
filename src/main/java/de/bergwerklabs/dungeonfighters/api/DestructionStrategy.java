package de.bergwerklabs.dungeonfighters.api;

import org.bukkit.Chunk;

/**
 * Created by Yannic Rieger on 21.06.2017.
 * <p> Interface that needs to be implemented by classes in a {@link DungeonFightersPlugin}
 *     that have a different implementation of a {@code DestructionStrategy} than the default one.
 *
 * @author Yannic Rieger
 */
public interface DestructionStrategy {

    /**
     * Destructs a module in a given {@link Chunk}.
     *
     * @param chunk Chunk where the module is located.
     */
    void destruct(Chunk chunk);
}
