package de.bergwerklabs.dungeonfighters.game.core.specialitem.backpack;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p> Manages every {@link Backpack} instance.
 *
 * @author Yannic Rieger
 */
public class BackpackManager {

    private static HashMap<UUID, Backpack> backpacks = new HashMap<>();

    /**
     * Gets the {@link Backpack} associated with a player, {@code null} if the player has no {@link Backpack}.
     *
     * @param player Player that might have a backpack.
     * @return a {@link Backpack}
     */
    public static Backpack getBackPack(Player player) {
        Backpack backpack = backpacks.get(player.getUniqueId());

        if (backpack == null) {
            backpacks.put(player.getUniqueId(), new Backpack());
            backpack = backpacks.get(player.getUniqueId());
            backpack.setInventory(Bukkit.createInventory(player, 9 * 3, "Backpack"));
            return backpack;
        }
        else {
            return backpacks.get(player.getUniqueId());
        }
    }
}
