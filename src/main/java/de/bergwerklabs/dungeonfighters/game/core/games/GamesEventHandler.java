package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.DungeonPlugin;
import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChunkSnapshot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Yannic Rieger on 28.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class GamesEventHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        DungeonGameLoader loader = new DungeonGameLoader();
        loader.buildDungeons(DungeonPlugin.game.determineDungeon(), null);


        Bukkit.getScheduler().runTaskTimerAsynchronously(DungeonPlugin.getInstance(), () -> {
            DungeonPlugin.game.getPlayerManager().getPlayers().values().forEach(fighter -> {

                ChunkSnapshot chunk = fighter.getPlayer().getLocation().getChunk().getChunkSnapshot();

                if (!fighter.getSession().getCurrentGame().getChunks().contains(chunk)) {
                    DungeonMechanicProvider gameToPlay = DungeonPlugin.game.getDungeon().getGamePositions().get(chunk);
                    gameToPlay.assignPlayer(fighter);
                    gameToPlay.start();
                }

            });
        }, 0, 20L);

    }
}
