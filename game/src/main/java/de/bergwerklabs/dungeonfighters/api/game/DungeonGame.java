package de.bergwerklabs.dungeonfighters.api.game;

import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
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

    @Override
    public LabsSchematic getModule() { return this.module; }

    /**
     *
     * @param tier
     */
    public void setStageTier(StageTier tier) { this.tier = tier; }

    /**
     *
     * @param configLocation
     */
    public void setConfigLocation(String configLocation) { this.configLocation = configLocation; }

    protected HashSet<String> chunks = new HashSet<>();
    protected DungeonFighter fighter;
    protected String configLocation;
    protected LabsSchematic module;
    protected StageTier tier;

    @Override
    public void assignChunks(HashSet<String> chunks) {
        this.chunks = chunks;
    }

    @Override
    public void assignModule(LabsSchematic module) {
        this.module = module;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void assignPlayer(DungeonFighter fighter) {
        this.fighter = fighter;
    }

    /**
     * This method is like the {@link JavaPlugin#onEnable()} method.
     * It gets called when the game is first loaded. In this method, all the necessary things should
     * done like loading the config. Instead of the {@link JavaPlugin#onEnable()} method, this one should be used.
     */
    public abstract void onLoad();

}
