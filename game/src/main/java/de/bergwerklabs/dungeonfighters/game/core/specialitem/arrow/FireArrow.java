package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p> Contains logic for fire arrows.
 *
 * @author Yannic Rieger
 */
public class FireArrow extends SpecialArrow {

    @Override
    public void playerHit(Player player) {
        System.out.printf("FIRE -> PLAYER");
        player.setFireTicks(20 * DungeonFightersPlugin.getInstance().getDungeonFighterConfig().getFireDuration());
    }

    @Override
    public void groundHit(Location location) {
        throw new NotImplementedException();
    }
}
