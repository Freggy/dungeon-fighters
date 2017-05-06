package de.bergwerklabs.dungeonfighters.game.config;

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

    public DungeonFighterConfig(int maxEmeralds, int minEmerald, String deathMessage, String joinMessage,
                                String notEnoughMoneyMessage) {

        this.maxEmeraldDrop = maxEmeralds;
        this.minEmeraldDrop = minEmerald;
        this.deathMessage = deathMessage;
        this.joinMessage = joinMessage;
        this.notEnoughMoneyMessage = notEnoughMoneyMessage;
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
}
