package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboardFactory;
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
        DungeonFightersPlugin.game.getPlayerManager().getPlayers()
                                  .put(e.getPlayer().getUniqueId(), new DungeonFighter(e.getPlayer(),
                                                                               LabsScoreboardFactory
                                                                                       .createInstance(DungeonFightersPlugin
                                                                                                               .getInstance().getDataFolder() + "/scoreboard.json")));
    }

}
