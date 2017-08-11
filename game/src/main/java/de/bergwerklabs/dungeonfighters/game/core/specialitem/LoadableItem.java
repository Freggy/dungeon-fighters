package de.bergwerklabs.dungeonfighters.game.core.specialitem;

import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>
 * Should be implemented by {@link SpecialItem}s that have a loading mechanism.
 *
 * @author Yannic Rieger
 */
public interface LoadableItem extends SpecialItem {

    /**
     * @param player Player that used the {@link SpecialItem}.
     * @param lastUsed Last time used determined by {@link System#currentTimeMillis()}.
     */
    void use(Player player, long lastUsed);
}
