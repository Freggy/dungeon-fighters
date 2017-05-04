package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.framework.game.LABSPlayer;
import de.bergwerklabs.framework.scoreboard.LabsScoreboard;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 01.05.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonFighter extends LABSPlayer {

    /**
     *
     */
    public double getEmeralds() {
        return emeralds;
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @param emeralds
     */
    public void setEmeralds(double emeralds) {
        this.scoreboard.getRowsByContent().get(String.valueOf(this.emeralds)).setText(String.valueOf(emeralds));
        this.emeralds = emeralds;
    }

    private double emeralds = 0.0;
    private Player player;
    private LabsScoreboard scoreboard;

    public DungeonFighter(Player p, LabsScoreboard scoreboard) {
        this.player = p;
        this.scoreboard = scoreboard;
        this.scoreboard.apply(p);
    }
}
