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
    private String cannotEnchantMessage;
    private String cannotConvertMessage;
    private Location gridOrigin;

    DungeonFighterConfig(HashMap<String, Object> messages, HashMap<String, Integer> emeraldSettings,
                                Location gridOrigin) {

        this.deathMessage = (String)messages.get("death-message");
        this.joinMessages = (String[])messages.get("join-messages");
        this.notEnoughMoneyMessage = (String)messages.get("not-enough-money-message");
        this.cannotEnchantMessage = (String)messages.get("cannot-enchant-message");
        this.cannotConvertMessage = (String)messages.get("cannot-convert-xp-message");

        this.maxEmeraldDrop = emeraldSettings.get("max-emerald-drop");
        this.minEmeraldDrop = emeraldSettings.get("min-emerald-drop");

        this.gridOrigin = gridOrigin;
    }

    /**
     *
     */
    public int getMaxEmeraldDrop() {
        return this.maxEmeraldDrop;
    }

    /**
     *
     */
    public int getMinEmeraldDrop() {
        return this.minEmeraldDrop;
    }

    /**
     *
     */
    public String getDeathMessage() {
        return this.deathMessage;
    }

    /**
     *
     */
    public String[] getJoinMessage() {
        return this.joinMessages;
    }

    /**
     *
     */
    public String getNotEnoughMoneyMessage() {
        return this.notEnoughMoneyMessage;
    }

    /**
     *
     */
    public String getCannotEnoughEnchantMessage() {
        return this.cannotEnchantMessage;
    }

    /**
     *
     */
    public Location getGridOrigin() {
        return this.gridOrigin;
    }

    /**
     *
     */
    public String getCannotConvertMessage() {
        return this.cannotConvertMessage;
    }
}
