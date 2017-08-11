package de.bergwerklabs.dungeonfighters.game.core.specialitem;

import de.bergwerklabs.dungeonfighters.game.core.specialitem.backpack.Backpack;
import de.bergwerklabs.dungeonfighters.game.core.specialitem.knockback.KnockbackStick;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p> Factory used for creating {@link SpecialItem}s.
 *
 * @author Yannic Rieger
 */
public class SpecialItemFactory {

    /**
     * Returns the {@link SpecialItem} based on the name of the {@link org.bukkit.inventory.ItemStack}.
     *
     * @param type Type of the special item
     * @return returns the {@link SpecialItem} based on the name of the {@link org.bukkit.inventory.ItemStack}.
     */
    public static SpecialItem createItem(SpecialItemType type) {

        if (type == SpecialItemType.MED_PACK) {
            return new MedPack();
        }
        else if (type == SpecialItemType.BACKPACK) {
            return new Backpack();
        }
        else if (type == SpecialItemType.KNOCKBACK) {
            return new KnockbackStick();
        }
        else return null;
    }

}
