package de.bergwerklabs.dungeonfighters.game.core.specialitems;

import de.bergwerklabs.dungeonfighters.api.SpecialItem;

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
    public static SpecialItem createItem(String name) {
        if (name == null) return null;

        switch (name) {
            case "§c✚ §fMediPack": return new MedPack();
            default: return null;
        }
    }

}
