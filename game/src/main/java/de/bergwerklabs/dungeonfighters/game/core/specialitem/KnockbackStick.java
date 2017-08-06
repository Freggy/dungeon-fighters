package de.bergwerklabs.dungeonfighters.game.core.specialitem;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitTask;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>
 * Knockbackstick that fills up power over time. If someone is hit, the power is calculated based on the loading percentage.
 *
 * @author Yannic Rieger
 */
public class KnockbackStick implements LoadableItem {

    // contains all players which are currently loading a stick
    private static Map<UUID, Long> loading = new ConcurrentHashMap<>();

    private BukkitTask loadingTask = null;
    private BukkitTask cancelTask = null;

    @Override
    public void use(Player player, long lastUsed) {
        if (!loading.keySet().contains(player.getUniqueId())) {
            loading.put(player.getUniqueId(), lastUsed);
        }
        else {
            loading.put(player.getUniqueId(), lastUsed);
            return; // return so the tasks below don't get executed twice.
        }

        cancelTask = Bukkit.getScheduler().runTaskTimerAsynchronously(DungeonFightersPlugin.getInstance(), () -> {
            if (player.getItemInHand().getType() != Material.BLAZE_ROD) {
                System.out.println("canceling due to item switch");
                this.cancelAndRemove(player.getUniqueId());
            }
            if (System.currentTimeMillis() - loading.get(player.getUniqueId()) > 500) {
                System.out.println("canceling due to time");
                this.cancelAndRemove(player.getUniqueId());
            }
        }, 0, 5L);

        loadingTask = Bukkit.getScheduler().runTaskTimerAsynchronously(DungeonFightersPlugin.getInstance(), () -> {
                int currentPower = Util.getPower(player.getItemInHand().getItemMeta().getDisplayName());
                if (currentPower < 100) {
                    ItemStackUtil.setName(player.getItemInHand(), Util.name.replace("{percentage}", String.valueOf(currentPower + 10)));
                }
                else this.cancelAndRemove(player.getUniqueId());
        }, 40L, 40L);
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

    /**
     * Cancels and removes the player that stopped loading the Stick.
     *
     * @param uuid {@link UUID} of the player.
     */
    private void cancelAndRemove(UUID uuid) {
        loadingTask.cancel();
        cancelTask.cancel();
        loading.remove(uuid);
    }
}
