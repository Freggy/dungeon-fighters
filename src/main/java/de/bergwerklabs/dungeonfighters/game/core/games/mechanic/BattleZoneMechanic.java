package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class BattleZoneMechanic extends BaseMechanic {

    @Override
    public void start() {
        this.fighter.getPlayer().setItemInHand(new ItemStack(Material.WOOD_SWORD));
    }
}
