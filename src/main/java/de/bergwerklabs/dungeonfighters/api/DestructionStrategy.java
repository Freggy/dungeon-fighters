package de.bergwerklabs.dungeonfighters.api;

import org.bukkit.Chunk;

/**
 * Created by Yannic Rieger on 21.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public interface DestructionStrategy {

    /**
     *
     * @param chunk
     */
    void destruct(Chunk chunk);
}
