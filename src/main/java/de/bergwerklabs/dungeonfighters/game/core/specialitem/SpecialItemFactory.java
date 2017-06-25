package de.bergwerklabs.dungeonfighters.game.core.specialitem;

import de.bergwerklabs.dungeonfighters.game.core.specialitem.backpack.BackpackManager;
import org.bukkit.entity.Player;

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
     * @param name Name of the item.
     * @param player Only needed if a instance of {@link de.bergwerklabs.dungeonfighters.game.core.specialitem.backpack.Backpack} will be created.
     * @return returns the {@link SpecialItem} based on the name of the {@link org.bukkit.inventory.ItemStack}.
     */
    public static SpecialItem createItem(String name, Player player) {
        if (name == null) return null;

        if (name.equals("§c✚ §fMediPack")) {
            return new MedPack();
        }
        else if (name.equals("Backpack")) {
            return BackpackManager.getBackPack(player);
        }
        else if (name.contains("Aufladung")) {
            return new KnockbackStick();
        }
        else return null;
    }

}
