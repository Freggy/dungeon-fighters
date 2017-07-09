package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow;

import de.bergwerklabs.dungeonfighters.DungeonPlugin;
import de.bergwerklabs.dungeonfighters.game.core.specialitem.SpecialArrow;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class PoisonedArrow extends SpecialArrow {

    public void playerHit(Player player) {
        System.out.printf("POISON -> PLAYER");
        new PotionEffect(PotionEffectType.POISON, 20 * DungeonPlugin.getInstance().getDungeonFighterConfig().getPoisonDuration(), 1).apply(player);
    }

    @Override
    public void groundHit(Location location) {
        throw new NotImplementedException();
    }
}
