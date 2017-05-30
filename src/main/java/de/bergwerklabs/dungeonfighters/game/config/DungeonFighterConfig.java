package de.bergwerklabs.dungeonfighters.game.config;

import de.bergwerklabs.framework.location.LocationUtil;
import org.bukkit.Location;

import java.util.HashMap;

/**
 * Created by Yannic Rieger on 06.05.2017.
 * <p> Config class holding all configurable values. </p>
 * @author Yannic Rieger
 */
public class DungeonFighterConfig {

    private int maxEmeraldDrop;
    private int minEmeraldDrop;
    private String deathMessage;
    private String joinMessage;
    private String notEnoughMoneyMessage;
    private String cannotEnchatMessage;
    private Location gridOrigin;

    public DungeonFighterConfig(HashMap<String, String> messages, HashMap<String, Integer> emeraldSettings,
                                Location gridOrigin) {

        this.deathMessage = messages.get("death-message");
        this.joinMessage = messages.get("join-message");
        this.notEnoughMoneyMessage = messages.get("not-enough-money-message");
        this.cannotEnchatMessage = messages.get("cannot-enchant-message");

        this.maxEmeraldDrop = emeraldSettings.get("max-emerald-drop");
        this.minEmeraldDrop = emeraldSettings.get("min-emerald-drop");

        this.gridOrigin = gridOrigin;
    }

    /**
     *
     */
    public int getMaxEmeraldDrop() {
        return maxEmeraldDrop;
    }

    /**
     *
     */
    public int getMinEmeraldDrop() {
        return minEmeraldDrop;
    }

    /**
     *
     */
    public String getDeathMessage() {
        return deathMessage;
    }

    /**
     *
     */
    public String getJoinMessage() {
        return joinMessage;
    }

    /**
     *
     */
    public String getNotEnoughMoneyMessage() {
        return notEnoughMoneyMessage;
    }

    /**
     *
     */
    public String getCannotEnoughEnchatMessage() {
        return cannotEnchatMessage;
    }

    public Location getGridOrigin() {
        return gridOrigin;
    }
}
