package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.DungeonPlugin;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameLoader;
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
    }
}
