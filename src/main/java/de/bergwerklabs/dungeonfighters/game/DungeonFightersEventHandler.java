package de.bergwerklabs.dungeonfighters.game;

import de.bergwerklabs.dungeonfighters.Main;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.util.ParticleUtil;
import de.bergwerklabs.framework.scoreboard.LabsScoreboardFactory;
import de.bergwerklabs.util.effect.Particle;
import de.bergwerklabs.util.effect.Particle.ParticleEffect;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.sql.BatchUpdateException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Yannic Rieger on 01.05.2017.
 * <p> Class providing EventHandlers for the following events: </p>
 * <ul>
 *     <li> BlockBreakEvent </li>
 *     <li> PlayerJoinEvent </li>
 *     <li> PlayerQuitEvent </li>
 * </ul>
 * @author Yannic Rieger
 */
public class DungeonFightersEventHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        // NOTE:
        // There is some strange behavior going on. When the server first starts and the scoreboard gets deserialized
        // from the JSON file, the scoreboard gets applied correctly, but if the player relogs nothing works anymore
        // because the initial scoreboard we deserialized got modified although we clone it.

        // load it everytime again?
        Main.game.getPlayerManager().getPlayers()
                 .put(e.getPlayer().getUniqueId(), new DungeonFighter(e.getPlayer(),
                                                                      LabsScoreboardFactory.createInstance(Main.getInstance().getDataFolder() + "/scoreboard.json")));
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

        if (itemStack.getType() == Material.EMERALD) {
            fighter.earnMoney(itemStack.getAmount(), Sound.ORB_PICKUP);
            e.getItem().remove();
            e.setCancelled(true);
        }
        else if (itemStack.getType() == Material.NETHER_STAR) {
            fighter.earnMoney(itemStack.getAmount() * 50, Sound.ORB_PICKUP);
            e.getItem().remove();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Main.game.getPlayerManager().getPlayers().remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player died = e.getEntity();
        Player killer = died.getKiller();

        e.setDeathMessage(Main.getInstance().getDungeonFighterConfig().getDeathMessage());

        Location eye = died.getEyeLocation();

        Particle headParticles = ParticleUtil.createParticle(ParticleEffect.REDSTONE, eye, 0.3F, 0.3F, 0.3F, 0F, 25);
        Particle centerParticles = ParticleUtil.createParticle(ParticleEffect.REDSTONE, eye.clone().subtract(0, 0.5, 0), 0.3F, 0.3F, 0.3F, 0F, 25);
        Particle feetParticles = ParticleUtil.createParticle(ParticleEffect.REDSTONE, died.getLocation(), 0.3F, 0.3F, 0.3F, 0F, 25);

        // TODO: 30.05.2017 - play sound 
        
        ParticleUtil.sendParticleToPlayer(headParticles, killer);
        ParticleUtil.sendParticleToPlayer(centerParticles, killer);
        ParticleUtil.sendParticleToPlayer(feetParticles, killer);

        e.getEntity().setGameMode(GameMode.SPECTATOR);
    }
}