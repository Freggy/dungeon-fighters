package de.bergwerklabs.dungeonfighters.game.config;

import de.bergwerklabs.framework.commons.spigot.title.Title;
import org.bukkit.Location;

import java.util.HashMap;

/**
 * Created by Yannic Rieger on 06.05.2017.
 * <p> Config class holding all configurable values.
 *
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
    private String cannotUseMedPackMessage;
    private String gameCountdownMessage;
    private String goldAddedMessage;
    private Title intermissionTitle;
    private float explosionRadius;
    private int poisonDuration, fireDuration;
    private Location gridOrigin;
    private float medPackHealing;

    DungeonFighterConfig(HashMap<String, Object> messages, HashMap<String, Integer> emeraldSettings,
                         HashMap<String, Number> arrowSettings, HashMap<String, Object> specialItemsSettings, HashMap<String, Title> titles, Location gridOrigin) {

        this.deathMessage = (String)messages.get("death-message");
        this.joinMessages = (String[])messages.get("join-messages");
        this.notEnoughMoneyMessage = (String)messages.get("not-enough-money-message");
        this.cannotEnchantMessage = (String)messages.get("cannot-enchant-message");
        this.cannotConvertMessage = (String)messages.get("cannot-convert-xp-message");
        this.cannotUseMedPackMessage = (String)messages.get("cannot-use-med-pack-message");
        this.gameCountdownMessage = (String)messages.get("countdown-message");
        this.goldAddedMessage = (String)messages.get("gold-add-message");

        this.maxEmeraldDrop = emeraldSettings.get("max-emerald-drop");
        this.minEmeraldDrop = emeraldSettings.get("min-emerald-drop");

        this.gridOrigin = gridOrigin;

        this.explosionRadius = arrowSettings.get("explosion-radius").floatValue();
        this.poisonDuration = arrowSettings.get("poison-duration").intValue();
        this.fireDuration = arrowSettings.get("fire-duration").intValue();

        this.medPackHealing = (Float)specialItemsSettings.get("med-pack-healing");

        this.intermissionTitle = titles.get("intermission-title");
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

    /**
     *
     */
    public String getCannotUseMedPackMessage() {
        return cannotUseMedPackMessage;
    }

    public int getFireDuration() {
        return fireDuration;
    }

    public int getPoisonDuration() {
        return poisonDuration;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public float getMedPackHealing() {
        return medPackHealing;
    }

    public Title getIntermissionTitle() {
        return intermissionTitle;
    }

    public String getGameCountdownMessage() {
        return gameCountdownMessage;
    }

    public String getGoldAddedMessage() {
        return goldAddedMessage;
    }
}
