package de.bergwerklabs.dungeonfighters.game.core.specialitems.arrow;

import de.bergwerklabs.dungeonfighters.Main;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ArrowMetadataHandler {
    public static void setMetadata(String bowName, Projectile projectile) {

        if (bowName.equals(ArrowType.POISON.getBowName())) {
            System.out.println("poi");
            projectile.setMetadata("damageType", new FixedMetadataValue(Main.getInstance(), new PoisonedArrow()));
        }
        else if (bowName.equals(ArrowType.EXPLOSION.getBowName())) {
            System.out.println("exp");
            projectile.setMetadata("damageType", new FixedMetadataValue(Main.getInstance(), new ExplosionArrow()));
        }
        else if (bowName.equals(ArrowType.FIRE.getBowName())) {
            System.out.println("fire");
            projectile.setMetadata("damageType", new FixedMetadataValue(Main.getInstance(), new FireArrow()));
        }
    }
}
