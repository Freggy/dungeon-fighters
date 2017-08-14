package de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation;

import de.bergwerklabs.dungeonfighters.commons.Util;
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
    public void buildAssociatedGame(boolean buildConnection) {
        BuildResult providerResult = this.info.getProviderResult();
        DungeonModuleConstructor.placeModule(providerResult.getModule(), providerResult.getBuildLocation());

        if (buildConnection) {
            BuildResult connectionResult = this.info.getConnectionResult();
            DungeonModuleConstructor.placeModule(connectionResult.getModule(), connectionResult.getBuildLocation());
        }
        this.moduleBuilt = true;
    }

    public void tryVanishWall(Location playerLocation) {
        int distance = playerLocation.getBlockZ() - this.zValue;
        if (distance >= -5 && distance < 0 && this.xValues.contains(playerLocation.getBlockX())) {
            Util.openEntrance(playerLocation.getWorld().getName(), this.info.getWallRegion());
            this.info.setHasWall(false);
        }
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
