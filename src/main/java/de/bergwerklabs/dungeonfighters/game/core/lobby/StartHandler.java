package de.bergwerklabs.dungeonfighters.game.core.lobby;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameLoader;
import de.bergwerklabs.util.mechanic.StartTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.Iterator;

/**
 * Created by Yannic Rieger on 19.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class StartHandler implements StartTimer.StartHandler {

    @Override
    public void handle(Player[] players) {
        DungeonGameLoader loader = new DungeonGameLoader();
        loader.buildDungeons(DungeonFightersPlugin.game.getDungeon(), players.length, "theme");
        Iterator<Location> spawns = DungeonFightersPlugin.game.getSpawns().iterator();

        for (Player player : players) {
            if (spawns.hasNext()) {
                player.teleport(spawns.next());
            }
        }
        HandlerList.unregisterAll(DungeonFightersPlugin.lobbyListener);
        Bukkit.getServer().getPluginManager().registerEvents( DungeonFightersPlugin.moduleEventHandler, DungeonFightersPlugin.getInstance());
    }
}
