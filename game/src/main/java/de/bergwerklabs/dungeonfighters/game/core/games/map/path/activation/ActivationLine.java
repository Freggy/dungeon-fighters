package de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation;

import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
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

    public int getzValue() { return this.zValue; }

    private int zValue;
    private Set<Integer> xValues;
    private ActivationInfo info;


    public <T extends ModuleMetadata> ActivationLine(ActivationInfo<T> info, int zValue, Set<Integer> xValues) {
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
    public void buildAssociatedGame() {
        BuildResult<ModuleMetadata> providerResult = this.info.getProviderResult();
        BuildResult<ModuleMetadata> connectionResult = this.info.getConnectionResult();

        DungeonModuleConstructor.placeModule(providerResult.getModule(), providerResult.getBuildLocation());
        DungeonModuleConstructor.placeModule(connectionResult.getModule(), connectionResult.getBuildLocation());
    }
}
