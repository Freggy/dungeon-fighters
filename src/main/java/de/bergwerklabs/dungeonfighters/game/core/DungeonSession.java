package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Yannic Rieger on 04.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonSession {

    private class DummyGame implements DungeonMechanicProvider {

        @Override
        public HashSet<String> getChunks() {
            return new HashSet<>();
        }

        @Override
        public DungeonFighter getFighter() {
            return null;
        }

        @Override
        public String getId() {
            return "bla";
        }

        @Override
        public void assignChunks(HashSet<String> chunks) {

        }

        @Override
        public void assignPlayer(DungeonFighter fighter) {

        }

        @Override
        public void assignModule(LabsSchematic schematic) {

        }

        @Override
        public LabsSchematic getModule() {
            return null;
        }

        @Override
        public void start() {

        }

        @Override
        public Object clone() {
            return null;
        }

        @Override
        public void stop() {}

        @Override
        public void reset() {}
    }

    /**
     *
     */
    public DungeonMechanicProvider getCurrentGame() { return this.currentGame; }

    /**
     *
     */
    public Queue<DungeonMechanicProvider> getGames() { return this.games; }

    /**
     *
     */
    public int getGold() { return this.gold; }

    /**
     *
     */
    public DungeonFighter getFighter() { return this.fighter; }


    /**
     *
     * @param games
     */
    public void setGames(Queue<DungeonMechanicProvider> games) {
        this.games = games;
    }

    /**
     *
     * @param provider
     */
    public void setCurrentGame(DungeonMechanicProvider provider) { this.currentGame = (DungeonMechanicProvider) provider.clone(); }

    /**
     *
     * @param amount
     */
    public void addGold(int amount) {
        Player player = this.fighter.getPlayer();
        player.sendMessage(DungeonFightersPlugin.getInstance().getChatPrefix() + "§b+" + amount + " §eGold");
        player.playSound(player.getEyeLocation(), Sound.LEVEL_UP, 100, 1);

        this.fighter.getScoreboard().getRowsByContent().get(String.valueOf(this.gold))
                    .setText("§f§l" + String.valueOf(this.gold += amount));
    }

    /**
     *
     * @param fighter
     */
    public void setFighter(DungeonFighter fighter) {
        if (this.fighter == null) this.fighter = fighter;
    }


    private DungeonMechanicProvider currentGame = new DummyGame();
    private Queue<DungeonMechanicProvider> games = new LinkedList<>();
    private DungeonFighter fighter;
    private int gold = 0;
}
