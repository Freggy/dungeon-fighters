package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.api.game.event.GameFailEvent;
import de.bergwerklabs.dungeonfighters.api.game.event.GameFailedEvent;
import de.bergwerklabs.dungeonfighters.api.game.event.GameFinishedEvent;
import de.bergwerklabs.dungeonfighters.commons.ScreenWarning;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.DungeonSession;
import de.bergwerklabs.dungeonfighters.game.core.stats.NetsynWrapper;
import de.bergwerklabs.dungeonfighters.game.core.stats.Statistic;
import de.bergwerklabs.framework.commons.spigot.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by Yannic Rieger on 28.06.2017.
 * <p>
 * Class containing every event that needs to be handled while the games session.
 *
 * @author Yannic Rieger
 */
public class GamesEventHandler implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onGameFinished(GameFinishedEvent e) {
        DungeonFighter fighter = e.getFighter();
        DungeonSession session = fighter.getSession();
        //ScreenWarning.send(fighter.getPlayer(), false); // remove red screen border from last minigame, if there is one.
        session.incrementCompletedModules();
        NetsynWrapper.saveStatistic(Statistic.COMPLETED_MODULES, fighter.getPlayer(), session.getCompletedModules());
    }

    @EventHandler
    public void onGameFail(GameFailEvent e) {
        int tries = e.getTries();
        DungeonFighter fighter = e.getFighter();
        Player player = fighter.getPlayer();

        switch (tries) {
            case 1: new Title("", "§c✖ §7✖ §7✖", 20, 20, 20).display(player); break;
            case 2: new Title("", "§c✖ ✖ §7✖", 20, 20, 20).display(player); break;
            case 3: new Title("§4Letzter Versuch", "§c✖ ✖ ✖", 20, 20, 20).display(player); break;
        }

        // TODO: teleport player to module spawn.
        if (tries >= 4) Bukkit.getPluginManager().callEvent(new GameFailedEvent(fighter, e.getGame()));
    }

    @EventHandler
    public void onGameFailed(GameFailedEvent e) {
        // TODO: teleport player to next module spawn.
    }
}
