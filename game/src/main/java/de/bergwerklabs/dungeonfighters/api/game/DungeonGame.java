package de.bergwerklabs.dungeonfighters.api.game;

import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.ActivationLine;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p> This is a base class for a DungeonGame. A DungeonGame is a game within the DungeonFighters mini game.
 *     As the name suggests, this class provides basic functionality for such games. Every
 *
 * @author Yannic Rieger
 */
public abstract class DungeonGame extends JavaPlugin implements DungeonMechanicProvider {

    @Override
    public ActivationLine getNextLine() {
        return this.nextLine;
    }

    @Override
    public DungeonFighter getFighter() {
        return this.fighter;
    }

    @Override
    public LabsSchematic getModule() { return this.module; }

    @Override
    public boolean hasStarted() { return this.hasStarted; }

    /**
     * Gets the location where the {@link LabsSchematic} got placed to.
     */
    public Location getPlaceLocation() { return this.placeLocation; }

    /**
     * Sets the {@link StageTier} for this {@code DungeonGame}.
     *
     * @param tier Tier of the current stage.
     */
    public void setStageTier(StageTier tier) { this.tier = tier; }

    /**
     * Sets the {@link Location} where the {@link LabsSchematic} will be placed.
     *
     * @param location The location where the {@link LabsSchematic} will be placed to.
     */
    public void setPlaceLocation(Location location) { this.placeLocation = placeLocation; }

    /**
     * Path that leads to the config of this {@code DungeonGame}.
     *
     * @param configLocation String which represents a path leading to a JSON file.
     */
    public void setConfigLocation(String configLocation) { this.configLocation = configLocation; }

    protected ActivationLine nextLine;
    protected DungeonFighter fighter;
    protected String configLocation;
    protected LabsSchematic module;
    protected StageTier tier;
    protected Location placeLocation;
    protected boolean hasStarted;

    @Override
    public void assignNext(ActivationLine nextLine) {
        this.nextLine = nextLine;
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
            throw  new RuntimeException();
        }
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
