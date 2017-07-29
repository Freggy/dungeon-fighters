package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow.trail.TrailType;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p> Handles arrow metadata which is mostly arrow trails.
 *
 * @author Yannic Rieger
 */
public class ArrowMetadataHandler {

    /**
     * sets the metadata of {@link Projectile}.
     *
     * @param bowName Name of the bow the player shot with.
     * @param projectile Projectile which was fired by the bow.
     */
    public static void setMetadata(String bowName, Projectile projectile) {

        if (bowName.equals(ArrowType.POISON.getBowName())) {
            projectile.setMetadata("damageType", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), new PoisonedArrow()));
            projectile.setMetadata("trail", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), TrailType.POISON.getInfo()));
        }
        else if (bowName.equals(ArrowType.EXPLOSION.getBowName())) {
            projectile.setMetadata("damageType", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), new ExplosionArrow()));
            projectile.setMetadata("trail", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), TrailType.EXPLOSION.getInfo()));
        }
        else if (bowName.equals(ArrowType.FIRE.getBowName())) {
            projectile.setMetadata("damageType", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), new FireArrow()));
            projectile.setMetadata("trail", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), TrailType.FIRE.getInfo()));
        }
        else if (bowName.equals(ArrowType.NORMAL.getBowName())) {
            projectile.setMetadata("trail", new FixedMetadataValue(DungeonFightersPlugin.getInstance(), TrailType.HEART.getInfo()));
        }
    }
}
