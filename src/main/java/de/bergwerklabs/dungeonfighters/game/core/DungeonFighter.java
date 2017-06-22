package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.framework.core.game.LabsPlayer;
import de.bergwerklabs.framework.core.scoreboard.LabsScoreboard;
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
    private LabsScoreboard scoreboard;

    /**
     * @param p Player representing the new DungeonFighter
     * @param scoreboard Scoreboard that will be displayed.
     */
    public DungeonFighter(Player p, LabsScoreboard scoreboard) {
        this.player = p;
        this.scoreboard = scoreboard;
        p.setScoreboard(scoreboard.getScoreboard());
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
}
