package de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation;

import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BuildResult;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation.DungeonModuleConstructor;
import org.bukkit.Location;

import java.util.Set;

/**
 * Created by Yannic Rieger on 01.08.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ActivationLine {

    /**
     *
     * @return
     */
    public ActivationInfo getInfo() { return this.info; }

    private int zValue;
    private Set<Integer> xValues;
    private ActivationInfo info;
    private ActivationLine nextLine;
    private boolean moduleBuilt = false;

    /**
     * @param info
     * @param zValue
     * @param xValues
     */
    public ActivationLine(ActivationInfo info, int zValue, Set<Integer> xValues) {
        this.zValue = zValue;
        this.xValues = xValues;
        this.info = info;
    }

    /**
     *
     * @param playerLocation
     * @return
     */
    public boolean shouldActivate(Location playerLocation) {
        return playerLocation.getBlockZ() - this.zValue >= 2 && this.xValues.contains(playerLocation.getBlockX());
    }

    /**
     *
     */
    public BuildResult buildAssociatedGame() {
        BuildResult providerResult = this.info.getProviderResult();
        BuildResult connectionResult = this.info.getConnectionResult();

        DungeonModuleConstructor.placeModule(providerResult.getModule(), providerResult.getBuildLocation());
        DungeonModuleConstructor.placeModule(connectionResult.getModule(), connectionResult.getBuildLocation());
        this.moduleBuilt = true;
        return providerResult;
    }

    public ActivationLine getNextLine() {
        return nextLine;
    }

    public void setNextLine(ActivationLine nextLine) {
        this.nextLine = nextLine;
    }

    public boolean isModuleBuilt() {
        return moduleBuilt;
    }
}
