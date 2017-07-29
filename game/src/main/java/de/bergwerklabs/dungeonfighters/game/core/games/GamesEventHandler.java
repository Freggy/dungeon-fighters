package de.bergwerklabs.dungeonfighters.game.core.games;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by Yannic Rieger on 28.06.2017.
 * <p> Class containing every event that needs to be handled while the games session.
 *
 * @author Yannic Rieger
 */
public class GamesEventHandler implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
