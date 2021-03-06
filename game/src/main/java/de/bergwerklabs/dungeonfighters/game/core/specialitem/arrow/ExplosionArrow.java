package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p> Contains explosive arrow logic.
 *
 * @author Yannic Rieger
 */
public class ExplosionArrow extends SpecialArrow {

    @Override
    public boolean executeOnGround() {
        return true;
    }

    @Override
    public void playerHit(Player player) {
        throw new NotImplementedException();
    }

    @Override
    public void groundHit(Location location) {
        Bukkit.getWorld("spawn").createExplosion(location.getBlockX(), location.getBlockY(), location.getBlockZ(),
                                                 DungeonFightersPlugin.getInstance().getDungeonFighterConfig().getExplosionRadius(),
                                                 false, false);
        System.out.println("EXP -> GROUND");
    }
}
