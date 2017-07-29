package de.bergwerklabs.dungeonfighters.game.core.specialitem;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.List;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p> Implemented by items that should have a special behavior.
 *
 * @author Yannic Rieger
 */
public interface SpecialItem {

    /**
     * Excecute method when action is contained in {@link SpecialItem#getRequiredActions()}.
     *
     * @param player Player who used the item
     */
    void use(Player player);

    /**
     * {@link Action}s that trigger {@link SpecialItem#use(Player)}.
     */
    List<Action> getRequiredActions();

    /**
     * Gets the type of this special item.
     */
    SpecialItemType getType();




}
