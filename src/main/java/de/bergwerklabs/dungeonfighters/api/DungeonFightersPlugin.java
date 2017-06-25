package de.bergwerklabs.dungeonfighters.api;

/**
 * Created by Yannic Rieger on 22.06.2017.
 * <p>
 *
 * @author Yannic Rieger
 */
public interface DungeonFightersPlugin {

    /**
     * Gets the {@link DestructionStrategy} of this Plugin.
     */
    DestructionStrategy getDestructionStrategy();

    /**
     * Gets the {@link DungeonChallenge} associated with this Plugin.
     */
    DungeonChallenge getChallenge();
}
