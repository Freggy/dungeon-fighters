package de.bergwerklabs.dungeonfighters.game.core.specialitems;

import de.bergwerklabs.dungeonfighters.api.SpecialItem;
import de.bergwerklabs.dungeonfighters.game.core.specialitems.backpack.BackpackManager;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class SpecialItemFactory {

    /**
     *
     * @param name
     * @return
     */
    public static SpecialItem createItem(String name, Player player) {
        if (name == null) return null;

        switch (name) {
            case "§c✚ §fMediPack": return new MedPack();
            case "Backpack": return BackpackManager.getBackPack(player);
            default: return null;
        }
    }

}
