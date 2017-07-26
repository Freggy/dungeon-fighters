package de.bergwerklabs.dungeonfighters.game.core.specialitem;

import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public interface LoadableItem extends SpecialItem {

    /**
     *
     * @param player
     * @param lastUsed
     */
    void use(Player player, long lastUsed);
}
