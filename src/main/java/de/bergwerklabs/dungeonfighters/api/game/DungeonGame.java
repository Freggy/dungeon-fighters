package de.bergwerklabs.dungeonfighters.api.game;

import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
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
    public HashSet<String> getChunks() {
        return this.chunks;
    }

    @Override
    public DungeonFighter getFighter() {
        return this.fighter;
    }

    /**
     *
     * @param configLocation
     */
    public void setConfigLocation(String configLocation) { this.configLocation = configLocation; }

    protected HashSet<String> chunks;
    protected DungeonFighter fighter;
    protected String configLocation;

    @Override
    public void assignChunks(HashSet<String> chunks) {
        this.chunks = chunks;
    }

    @Override
    public void assignPlayer(DungeonFighter fighter) {
        this.fighter = fighter;
    }
}
