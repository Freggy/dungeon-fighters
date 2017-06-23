package de.bergwerklabs.dungeonfighters.game.core.specialitems.arrow;

import de.bergwerklabs.dungeonfighters.Main;
import de.bergwerklabs.dungeonfighters.api.SpecialArrow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p>  </p>
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
                                                       Main.getInstance().getDungeonFighterConfig().getExplosionRadius(),
                                                       false, false);
        System.out.println("EXP -> GROUND");
    }
}
