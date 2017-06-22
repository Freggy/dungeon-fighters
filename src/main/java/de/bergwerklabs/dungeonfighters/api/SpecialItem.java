package de.bergwerklabs.dungeonfighters.api;

import org.bukkit.event.block.Action;

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
    void use();

    /**
     *
     * @return
     */
    Action getRequiredAction();
}
