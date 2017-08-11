package de.bergwerklabs.dungeonfighters.game.core.specialitem;

import java.util.Arrays;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p> Enumeration of all {@link SpecialItem}s.
 *
 * @author Yannic Rieger
 */
public enum SpecialItemType {
    KNOCKBACK("20%"),
    MED_PACK("MED_PACK"),
    BACKPACK("Backpack");

    private String itemName;

    SpecialItemType(String itemName) {
        this.itemName = itemName;
    }

    /**
     *
     * @param name
     * @return
     */
    public static SpecialItemType getItemByDisplayName(String name) {
       return Arrays.stream(values()).filter(item -> item.itemName.equals(name)).findFirst().orElse(null);
    }
}
