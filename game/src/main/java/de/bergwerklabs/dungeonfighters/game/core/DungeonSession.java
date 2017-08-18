package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboard;
import de.bergwerklabs.framework.commons.spigot.scoreboard.Row;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Yannic Rieger on 04.07.2017.
 * <p>
 * Session which stores information about the game progress of each individual player.
 *
 * @author Yannic Rieger
 */
public class DungeonSession {

    /**
     * Gets the current game the player is playing.
     */
    public DungeonMechanicProvider getCurrentGame() { return this.currentGame; }

    /**
     * Gets all {@link DungeonMechanicProvider} for the game.
     */
    public Queue<DungeonMechanicProvider> getGames() { return this.games; }

    /**
     * Gets the current amount of gold the player has.
     */
    public int getGold() { return this.gold; }

    /**
     * Gets the {@link DungeonFighter} for this session.
     */
    public DungeonFighter getFighter() { return this.fighter; }

    public StageTier getCurrentTier() {
        return currentTier;
    }

    /**
     *
     */
    public int getCompletedModules() {
        return this.completedModules;
    }

    /**
     * Sets the games.
     *
     * @param games Games for this game session.
     */
    public void setGames(Queue<DungeonMechanicProvider> games) {
        this.games = games;
    }

    /**
     * Sets the current game.
     *
     * @param provider representing a game or a built-in mechanic.
     */
    public void setCurrentGame(DungeonMechanicProvider provider) { this.currentGame =  provider; }

    /**
     *
     * @param currentTier
     */
    public void setCurrentTier(StageTier currentTier) {
        this.currentTier = currentTier;
    }

    /**
     *
     */
    public void incrementCompletedModules() {
        ++this.completedModules;
        DungeonFightersPlugin.game.getPlayerManager().getPlayers().values().forEach(fighter -> {
            HashMap<String, Row> rowsByContent = fighter.getScoreboard().getRowsByContent();
            if (fighter.getPlayer().getUniqueId().equals(this.fighter.getPlayer().getUniqueId())) {
                rowsByContent.get(ChatColor.UNDERLINE + this.fighter.getPlayer().getDisplayName()).setScore(this.completedModules);
            }
            else rowsByContent.get(this.fighter.getPlayer().getDisplayName()).setScore(this.completedModules);
        });
    }

    /**
     * Adds gold to the player.
     *
     * @param amount Amount of gold to add.
     */
    public void addGold(int amount) {
        Player player = this.fighter.getPlayer();

        DungeonFightersPlugin plugin = DungeonFightersPlugin.getInstance();

        player.sendMessage(plugin.getChatPrefix() + plugin.getDungeonFighterConfig().getGoldAddedMessage()
                                                             .replace("{amount}", String.valueOf(amount)));

        player.playSound(player.getEyeLocation(), Sound.LEVEL_UP, 100, 1);

        this.fighter.getScoreboard().getRowsByContent().get("§7§l" + String.valueOf(this.gold))
                    .setText("§7§l" + String.valueOf(this.gold += amount));
    }

    /**
     * Sets the {@link DungeonFighter} associated with this session.
     *
     * @param fighter {@link DungeonFighter} associated with this session.
     */
    public void setFighter(DungeonFighter fighter) {
        if (this.fighter == null) this.fighter = fighter;
    }


    private DungeonMechanicProvider currentGame;
    private Queue<DungeonMechanicProvider> games = new LinkedList<>();
    private DungeonFighter fighter;
    private int gold = 0, completedModules = 0;
    private StageTier currentTier = StageTier.ONE;
}
