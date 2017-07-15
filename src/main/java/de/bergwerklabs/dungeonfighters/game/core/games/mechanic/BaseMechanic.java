package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;

import java.util.HashSet;

/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public abstract class BaseMechanic implements DungeonMechanicProvider {

    @Override
    public HashSet<String> getChunks() {
        return this.chunks;
    }

    @Override
    public DungeonFighter getFighter() {
        return this.fighter;
    }

    protected HashSet<String> chunks;
    protected DungeonFighter fighter;

    @Override
    public void assignChunks(HashSet<String> chunks) {
        this.chunks = chunks;
    }

    @Override
    public void assignPlayer(DungeonFighter fighter) {
        this.fighter = fighter;
    }

    @Override
    public void stop() {}
}
