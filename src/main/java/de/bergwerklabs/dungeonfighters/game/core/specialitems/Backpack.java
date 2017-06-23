package de.bergwerklabs.dungeonfighters.game.core.specialitems;

import de.bergwerklabs.dungeonfighters.api.SpecialItem;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.List;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class Backpack implements SpecialItem {
    @Override
    public void use(Player player) {

    }

    @Override
    public List<Action> getRequiredActions() {
        return null;
    }
}
