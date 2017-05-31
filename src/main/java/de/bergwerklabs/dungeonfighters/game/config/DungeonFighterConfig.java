package de.bergwerklabs.dungeonfighters.game.config;

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
    private String[] joinMessages;
    private String notEnoughMoneyMessage;
    private String cannotEnchatMessage;
    private Location gridOrigin;

    public DungeonFighterConfig(HashMap<String, Object> messages, HashMap<String, Integer> emeraldSettings,
                                Location gridOrigin) {

        this.deathMessage = (String)messages.get("death-message");
        this.joinMessages = (String[])messages.get("join-message");
        this.notEnoughMoneyMessage = (String)messages.get("not-enough-money-message");
        this.cannotEnchatMessage = (String)messages.get("cannot-enchant-message");

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
    public String[] getJoinmessages() {
        return joinMessages;
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
    public String getCannotEnoughEnchantMessage() {
        return cannotEnchatMessage;
    }

    /**
     *
     */
    public Location getGridOrigin() {
        return gridOrigin;
    }
}
