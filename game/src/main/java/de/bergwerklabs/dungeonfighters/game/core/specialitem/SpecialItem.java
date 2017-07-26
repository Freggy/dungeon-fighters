package de.bergwerklabs.dungeonfighters.game.core.specialitem;

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
     */
    List<Action> getRequiredActions();

    /**
     *
     */
    SpecialItemType getType();




}
