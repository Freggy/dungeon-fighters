package de.bergwerklabs.dungeonfighters.game.core.specialitem.knockback;

import de.bergwerklabs.dungeonfighters.game.core.specialitem.LoadableItem;
import de.bergwerklabs.dungeonfighters.game.core.specialitem.SpecialItemType;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>
 * Knockbackstick that fills up power over time. If someone is hit, the power is calculated based on the loading percentage.
 *
 * @author Yannic Rieger
 */
public class KnockbackStick implements LoadableItem {

    public static int getPower(String itemName) {
        return Integer.valueOf(Pattern.compile("\\d+%").matcher(itemName).group(0));
    }

    public static void setPower(Player p) {
        String itemName = p.getItemInHand().getItemMeta().getDisplayName();
        ItemStackUtil.setName(p.getItemInHand(), itemName.replace("\\d+%", String.valueOf(getPower(itemName))));
    }

    @Override
    public void use(Player player, long lastUsed) {
        KnockbackStickManager.addLoadingPlayer(player, lastUsed);
    }

    @Override
    public void use(Player player) {
        throw new NotImplementedException();
    }

    @Override
    public List<Action> getRequiredActions() {
        return Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK);
    }

    @Override
    public SpecialItemType getType() {
        return SpecialItemType.KNOCKBACK;
    }

}
