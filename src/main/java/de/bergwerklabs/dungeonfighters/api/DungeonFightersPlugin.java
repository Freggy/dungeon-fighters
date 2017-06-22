package de.bergwerklabs.dungeonfighters.api;

/**
 * Created by Yannic Rieger on 22.06.2017.
 * <p>
 *
 * @author Yannic Rieger
 */
public interface DungeonFightersPlugin {

    DestructionStrategy getDestructionStrategy();

    DungeonChallenge getChallenge();
}
