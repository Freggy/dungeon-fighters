package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Yannic Rieger on 19.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class GameUpdateTask implements Runnable {

    @Override
    public void run() {
        DungeonFightersPlugin.game.getPlayerManager().getPlayers().values().forEach(fighter -> {

            // TODO: update scoreboard time

            String chunkCoordinates = Util.getChunkCoordinateString(fighter.getPlayer().getLocation().getChunk());

            DungeonMechanicProvider gameToPlay = DungeonFightersPlugin.game.getDungeon().getGamePositions().get(chunkCoordinates);
            DungeonMechanicProvider currentGame = fighter.getSession().getCurrentGame();

            if (gameToPlay != null) {
                if (gameToPlay.getId().contains("built-in") && currentGame.getChunks().contains(chunkCoordinates)) {
                    this.initGame(fighter, gameToPlay, chunkCoordinates);
                }
                else if (!currentGame.getId().equals(gameToPlay.getId())) {
                    this.initGame(fighter, gameToPlay, chunkCoordinates);
                }
                else if (currentGame.getChunks().contains(chunkCoordinates)) {
                    this.close(fighter.getPlayer(), chunkCoordinates);
                    currentGame.reset();
                    currentGame.getChunks().remove(chunkCoordinates);
                }
            }
        });
    }

    /**
     *
     * @param fighter
     * @param provider
     * @param chunkCoord
     */
    private void initGame(DungeonFighter fighter, DungeonMechanicProvider provider, String chunkCoord) {
        fighter.getSession().getCurrentGame().stop();
        this.close(fighter.getPlayer(), chunkCoord);
        fighter.getSession().setCurrentGame(provider);
        DungeonFightersPlugin.game.getDungeon().getGamePositions().remove(chunkCoord);
        fighter.getSession().getCurrentGame().getChunks().remove(chunkCoord);
        fighter.getSession().getCurrentGame().assignPlayer(fighter);
        fighter.getSession().getCurrentGame().assignModule(DungeonFightersPlugin.game.getModules().get(chunkCoord).getSchematic());
        fighter.getSession().getCurrentGame().start();
    }

    /**
     *
     * @param player
     * @param coords
     */
    private void close(Player player, String coords) {
        List<Location> blocks = DungeonFightersPlugin.game.getModules().get(coords).getBlockLocations();
        Util.closeEntrance(player, blocks);
    }

    /**
     *
     */
    private void updateScoreboard() {

    }


}
