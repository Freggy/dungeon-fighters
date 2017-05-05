package de.bergwerklabs.dungeonfighters.game;

import de.bergwerklabs.dungeonfighters.Main;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.util.ParticleUtil;
import de.bergwerklabs.util.effect.Particle;
import de.bergwerklabs.util.effect.Particle.ParticleEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Created by Yannic Rieger on 01.05.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonFightersEventHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Main.game.getPlayerManager().getPlayers().put(e.getPlayer().getUniqueId(), new DungeonFighter(e.getPlayer(),
                                                                                                      Main.getInstance().getScoreboard().clone()));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();

        if (block.getType() == Material.EMERALD_ORE) {
            Particle particle = ParticleUtil.createParticle(ParticleEffect.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.5, 0.5),0.2F,0.3F,0.2F,0.2F,20);
            ParticleUtil.sendParticleToPlayer(particle, e.getPlayer());

            Random r = new Random();
            int amounToDrop = r.nextInt((10 - 4) + 1) + 4; // make configurable
            block.setType(Material.AIR);

            double chance = Math.random();
            if (chance < 0.03) block.getWorld().dropItemNaturally(block.getLocation().clone().add(0.5,0.5,0.5), new ItemStack(Material.NETHER_STAR));

            for (int i = 0; i < amounToDrop; i++) {
                Item item = block.getWorld().dropItemNaturally(block.getLocation().clone().add(0.5,0.5,0.5), new ItemStack(Material.EMERALD));
                item.setVelocity(new Vector(0.1 * Math.random(), 0.3, 0.1 * Math.random())); // Apply random velocity to make it look cool when dropping the emeralds.
            }
        }
    }

    @EventHandler
    public void onPlayerPickUpItem(PlayerPickupItemEvent e) {
        DungeonFighter fighter = Main.game.getPlayerManager().getPlayers().get(e.getPlayer().getUniqueId());
        ItemStack itemStack = e.getItem().getItemStack();

        if (itemStack.getType() == Material.EMERALD)
            fighter.earnMoney(itemStack.getAmount(), Sound.ORB_PICKUP);

        else if (itemStack.getType() == Material.NETHER_STAR)
            this.transferMoneyAndCancelEvent(fighter, itemStack.getAmount() * 50, e.getItem(), e);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Main.game.getPlayerManager().getPlayers().remove(e.getPlayer().getUniqueId());
    }

    /**
     *
     * @param fighter
     * @param money
     * @param item
     * @param event
     */
    private void transferMoneyAndCancelEvent(DungeonFighter fighter, int money, Item item, Cancellable event) {
        fighter.earnMoney(money, Sound.ORB_PICKUP);
        item.remove();
        event.setCancelled(true);
    }
}