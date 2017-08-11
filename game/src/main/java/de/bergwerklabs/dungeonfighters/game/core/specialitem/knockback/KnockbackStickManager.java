package de.bergwerklabs.dungeonfighters.game.core.specialitem.knockback;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.commons.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class KnockbackStickManager {

    // contains all players which are currently loading a stick
    private static Map<Player, Long> loading = new ConcurrentHashMap<>();

    private static BukkitTask loadingTask = Util.getEmptyBukkitTask();
    private static BukkitTask cancelTask =  Util.getEmptyBukkitTask();


    public static void addLoadingPlayer(Player player, long lastUsed) {
        loading.putIfAbsent(player, lastUsed);

        if (Bukkit.getScheduler().isCurrentlyRunning(cancelTask.getTaskId())) {
            cancelTask = Bukkit.getScheduler().runTaskTimerAsynchronously(DungeonFightersPlugin.getInstance(), () -> {
                loading.forEach((key, value) -> {
                    if (key.getItemInHand().getType() != Material.BLAZE_ROD) {
                        System.out.println("canceling due to item switch");
                        cancelAndRemove(player);
                    }
                    if (System.currentTimeMillis() - loading.get(player) > 500) {
                        System.out.println("canceling due to time");
                        cancelAndRemove(player);
                    }
                });
            }, 0, 5);
        }

        if (Bukkit.getScheduler().isCurrentlyRunning(loadingTask.getTaskId())) {
            loadingTask = Bukkit.getScheduler().runTaskTimerAsynchronously(DungeonFightersPlugin.getInstance(), () -> {
                loading.keySet().forEach(p -> {
                    String itemName = p.getItemInHand().getItemMeta().getDisplayName();
                    int currentPower = Integer.valueOf(Pattern.compile("\\d+%").matcher(itemName).group(0));
                    if (currentPower < 100) {
                        KnockbackStick.setPower(p);
                    }
                    else cancelAndRemove(p);
                });
            }, 0, 40);
        }
    }

    /**
     * Cancels and removes the player that stopped loading the Stick.
     *
     * @param player player to remove from the loading cache.
     */
    private static void cancelAndRemove(Player player) {
        loading.remove(player);
        if (loading.size() == 0) {
            loadingTask.cancel();
            cancelTask.cancel();
        }
    }
}
