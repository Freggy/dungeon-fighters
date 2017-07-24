package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.framework.commons.spigot.game.LabsPlayer;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboard;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 01.05.2017.
 * <p> Player object specifically for DungeonFighter therefore providing more specialized functionality
 * @author Yannic Rieger
 */
public class DungeonFighter extends LabsPlayer {

    /**
     * Gets the current amount of emeralds.
     */
    public double getEmeralds() {
        return emeralds;
    }

    /**
     * Gets the Bukkit player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     */
    public DungeonSession getSession() { return this.session; }

    /**
     *
     * @return
     */
    public LabsScoreboard getScoreboard() { return this.scoreboard; }


    public void setScoreboard(LabsScoreboard scoreboard) {
        this.scoreboard = scoreboard;
        this.scoreboard.apply(this.player);
    }

    /**
     * Sets the current amount of emeralds.
     *
     * @param emeralds Amount of emeralds the player will have.
     */
    private void setEmeralds(double emeralds) {
        this.scoreboard.getRowsByContent().get(String.valueOf(this.emeralds)).setText(String.valueOf(emeralds));
        this.emeralds = emeralds;
    }

    private double emeralds = 0.0;
    private Player player;
    private boolean canFireBow = true;
    private LabsScoreboard scoreboard;
    private DungeonSession session;

    /**
     * @param p Player representing the new DungeonFighter
     */
    public DungeonFighter(Player p) {
        this.player = p;
        this.session = new DungeonSession();
        this.session.setFighter(this);
    }

    /**
     * Transfers money to the fighter.
     *
     * @param earnedMoney Money earned.
     * @param sound Sound which should be played.
     */
    public void earnMoney(double earnedMoney, Sound sound) {
        this.setEmeralds(this.getEmeralds() + earnedMoney);
        Player p = this.getPlayer();
        p.playSound(p.getLocation(), sound, 2, 2);
    }

    /**
     *
     */
    public void spendMoney(double cost) {
        this.emeralds -= cost;
        this.player.playSound(this.player.getEyeLocation(), Sound.CLICK, 100, 1);
    }

    /**
     *
     * @param cost
     * @return
     */
    public boolean hasEnoughMoney(double cost) {
        return cost < this.emeralds;
    }

    /**
     *
     */
    public boolean canFireBow() {
        return canFireBow;
    }

    /**
     *
     */
    public void setCanFireBow(boolean canFireBow) {
        this.canFireBow = canFireBow;
    }
}
