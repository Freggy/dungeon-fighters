package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.DungeonSession;
import org.bukkit.entity.Player;

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

            DungeonSession session = fighter.getSession();
            DungeonMechanicProvider currentGame = session.getCurrentGame();

            System.out.println(currentGame.getId());

            if (currentGame.getNextLine().activated(fighter.getPlayer().getLocation())) {
                DungeonMechanicProvider nextGame = session.getGames().poll();

                session.getCurrentGame().stop();
                session.setCurrentGame(nextGame);
                session.getCurrentGame().assignPlayer(fighter);
                session.getCurrentGame().assignModule(null);
                session.getCurrentGame().start();

            }

            /*

            // TODO: update scoreboard time

            String chunkCoordinates = Util.getChunkCoordinateString(fighter.getPlayer().getLocation().getChunk());

            DungeonMechanicProvider gameToPlay = DungeonFightersPlugin.game.getDungeon().getGamePositions().get(chunkCoordinates);
            DungeonMechanicProvider currentGame = fighter.getSession().getCurrentGame();

            if (gameToPlay != null) {
                if (gameToPlay.getId().contains("built-in") && currentGame.getNexInfo().contains(chunkCoordinates)) {
                    this.initGame(fighter, gameToPlay, chunkCoordinates);
                }
                else if (!currentGame.getId().equals(gameToPlay.getId())) {
                    this.initGame(fighter, gameToPlay, chunkCoordinates);
                }
                else if (currentGame.getNexInfo().contains(chunkCoordinates)) {
                    this.close(fighter.getPlayer(), chunkCoordinates);
                    this.initGame(fighter, gameToPlay, chunkCoordinates);
                }
            } */
        });
    }



    /**
     * Closes the module behind the player.
     *
     * @param player player who left the module.
     * @param coords String representing the XZ coordinates of a {@link org.bukkit.Chunk}.
     */
    private void close(Player player, String coords) {
        //List<Location> blocks = DungeonFightersPlugin.game.getModules().get(coords).getBlockLocations();
        //Util.closeEntrance(player, blocks);
    }

}
