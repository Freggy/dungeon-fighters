package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class EndMechanic extends BaseMechanic {

    @Override
    public String getId() {
        return  "end-built-in";
    }

    @Override
    public void start() {
        this.fighter.getPlayer().setItemInHand(new ItemStack(Material.WOOD_SWORD));
    }
}
