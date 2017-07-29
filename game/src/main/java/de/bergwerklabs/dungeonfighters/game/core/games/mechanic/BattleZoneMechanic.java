package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p> Mechanic for the {@link de.bergwerklabs.dungeonfighters.game.core.games.map.BattleZone}.
 *     this gets triggered when a player enters the zone.
 *
 * @author Yannic Rieger
 */
public class BattleZoneMechanic extends BaseMechanic {

    @Override
    public String getId() {
        return "battleZone-built-in";
    }

    @Override
    public void start() {
        this.fighter.getPlayer().setItemInHand(new ItemStack(Material.WOOD_SWORD));
        fighter.getSession().getCurrentGame().getChunks().clear();
    }
}
