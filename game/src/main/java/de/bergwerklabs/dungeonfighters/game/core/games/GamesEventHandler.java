package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.api.game.event.GameFinishedEvent;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.DungeonSession;
import de.bergwerklabs.dungeonfighters.game.core.stats.NetSynWrapper;
import de.bergwerklabs.dungeonfighters.game.core.stats.Statistic;
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
        session.incrementCompletedModules();
        NetSynWrapper.saveStatistic(Statistic.COMPLETED_MODULES, fighter.getPlayer(), session.getCompletedModules());
    }
}
