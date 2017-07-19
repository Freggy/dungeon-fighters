package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow.trail.TrailType;
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
            System.out.println("exp");
            projectile.setMetadata("damageType", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), new PoisonedArrow()));
            projectile.setMetadata("trail", new FixedMetadataValue(DungeonFightersPlugin
                                                                           .getInstance(), TrailType.POISON.getInfo()));
        }
        else if (bowName.equals(ArrowType.EXPLOSION.getBowName())) {
            System.out.println("exp");
            projectile.setMetadata("damageType", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), new ExplosionArrow()));
            projectile.setMetadata("trail", new FixedMetadataValue(DungeonFightersPlugin
                                                                           .getInstance(), TrailType.EXPLOSION.getInfo()));
        }
        else if (bowName.equals(ArrowType.FIRE.getBowName())) {
            System.out.println("fire");
            projectile.setMetadata("damageType", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), new FireArrow()));
            projectile.setMetadata("trail", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), TrailType.FIRE.getInfo()));
        }
        else if (bowName.equals(ArrowType.NORMAL.getBowName())) {
            projectile.setMetadata("trail", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), TrailType.HEART.getInfo()));
        }
    }
}
