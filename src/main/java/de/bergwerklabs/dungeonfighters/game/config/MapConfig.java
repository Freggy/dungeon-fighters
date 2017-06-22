package de.bergwerklabs.dungeonfighters.game.config;

/**
 * Created by Yannic Rieger on 22.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class MapConfig {

    /**
     * The seconds to wait between task runs.
     */
    public int getDestructionPeriod() {
        return destructionPeriod;
    }

    /**
     * The seconds to wait before running the destruction task.
     */
    public int getGetDestructionDelay() {
        return getDestructionDelay;
    }

    /**
     * Gets the map name.
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Gets the creators of this map.
     */
    public String[] getCreators() {
        return creators;
    }

    private String mapName;
    private String[] creators;
    private int destructionPeriod, getDestructionDelay;
    private boolean hasSpecialItems, hasCustomDestructionStrategy;
}
