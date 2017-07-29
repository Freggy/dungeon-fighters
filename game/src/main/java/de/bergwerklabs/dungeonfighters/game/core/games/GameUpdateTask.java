package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Yannic Rieger on 19.07.2017.
 * <p> This task updates the game and checks whether or not the player is in another module.
 *     If so, the task will start the game.
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
                    this.initGame(fighter, gameToPlay, chunkCoordinates);
                }
            }
        });
    }

    /**
     * Initializes the {@link de.bergwerklabs.dungeonfighters.api.game.DungeonGame} for the player.
     *
     * @param fighter Player that entered a new area.
     * @param provider {@link DungeonMechanicProvider} represents a new game.
     * @param chunkCoord String representing the XZ coordinates of a {@link org.bukkit.Chunk}.
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
     * Closes the module behind the player.
     *
     * @param player player who left the module.
     * @param coords String representing the XZ coordinates of a {@link org.bukkit.Chunk}.
     */
    private void close(Player player, String coords) {
        List<Location> blocks = DungeonFightersPlugin.game.getModules().get(coords).getBlockLocations();
        Util.closeEntrance(player, blocks);
    }
}
