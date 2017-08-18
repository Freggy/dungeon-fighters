package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

import de.bergwerklabs.dungeonfighters.game.core.DungeonSession;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BattleZone;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p>
 * Mechanic for the {@link BattleZone}.
 * this gets triggered when a player enters the zone.
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
        DungeonSession session = fighter.getSession();
        session.setCurrentTier(session.getCurrentTier().getNext());
        this.hasStarted = true;
    }
}
