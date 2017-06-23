package de.bergwerklabs.dungeonfighters.api;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.List;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public interface SpecialItem {

    /**
     *
     */
    void use(Player player);

    /**
     *
     * @return
     */
    List<Action> getRequiredActions();
}
