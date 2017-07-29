package de.bergwerklabs.dungeonfighters.game.core.specialitem.backpack;

import de.bergwerklabs.dungeonfighters.game.core.specialitem.SpecialItem;
import de.bergwerklabs.dungeonfighters.game.core.specialitem.SpecialItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p> Class which represents a backpack in Minecraft.
 *
 * @author Yannic Rieger
 */
public class Backpack implements SpecialItem {

    /**
     * Sets the {@link Inventory}
     *
     * @param inventory inventory to set.
     */
    void setInventory(Inventory inventory) { this.inventory = inventory; }

    private Inventory inventory;

    @Override
    public void use(Player player) {
        player.openInventory(BackpackManager.getBackPack(player).inventory);
    }

    @Override
    public List<Action> getRequiredActions()
    {
        return Arrays.asList(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR);
    }

    @Override
    public SpecialItemType getType() {
        return SpecialItemType.BACKPACK;
    }
}
