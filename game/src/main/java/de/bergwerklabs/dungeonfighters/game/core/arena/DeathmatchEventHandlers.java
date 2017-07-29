package de.bergwerklabs.dungeonfighters.game.core.arena;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.commons.ParticleUtil;
import de.bergwerklabs.dungeonfighters.commons.RoundSummaryMapRenderer;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.specialitem.*;
import de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow.ArrowMetadataHandler;
import de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow.trail.ArrowTrailTask;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackUtil;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboard;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboardFactory;
import de.bergwerklabs.util.effect.Particle;
import de.bergwerklabs.util.effect.Particle.ParticleEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Created by Yannic Rieger on 01.05.2017.
 * <p> Class providing EventHandlers for the following events:
 * <ul>
 *     <li> BlockBreakEvent </li>
 *     <li> PlayerJoinEvent </li>
 *     <li> PlayerQuitEvent </li>
 *     <li> PlayerDeathEvent </li>
 *     <li> PlayerPickupItemEvent </li>
 *     <li> PlayerInteractEvent </li>
 *     <li> EntityShootBowEvent </li>
 *     <li> EntityDamageByEntity </li>
 * </ul>
 * @author Yannic Rieger
 */
public class DeathmatchEventHandlers implements Listener {

    @EventHandler
    public void onMapInitialized(MapInitializeEvent e) {
        e.getMap().removeRenderer(e.getMap().getRenderers().get(0));
        e.getMap().setScale(MapView.Scale.FARTHEST);
        e.getMap().addRenderer(new RoundSummaryMapRenderer(new File(DungeonFightersPlugin.getInstance().getDataFolder() + "/image.png")));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();

        if (block.getType() == Material.EMERALD_ORE) {
            Particle particle = ParticleUtil.createParticle(ParticleEffect.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.5, 0.5),0.2F,0.3F,0.2F,0.2F,20);
            ParticleUtil.sendParticleToPlayer(particle, e.getPlayer());

            Random r = new Random();
            int max = DungeonFightersPlugin.getInstance().getDungeonFighterConfig().getMaxEmeraldDrop();
            int min = DungeonFightersPlugin.getInstance().getDungeonFighterConfig().getMinEmeraldDrop();
            int amounToDrop = r.nextInt((max - min) + 1) + max;
            double chance = Math.random();

            block.setType(Material.AIR);

            if (chance < 0.03) block.getWorld().dropItemNaturally(block.getLocation().clone().add(0.5,0.5,0.5), new ItemStack(Material.NETHER_STAR));

            for (int i = 0; i < amounToDrop; i++) {
                Item item = block.getWorld().dropItemNaturally(block.getLocation().clone().add(0.5,0.5,0.5), new ItemStack(Material.EMERALD));
                item.setVelocity(new Vector(0.1 * Math.random(), 0.3, 0.1 * Math.random())); // Apply random velocity to make it look cool when dropping the emeralds.
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        // NOTE:
        // There is some strange behavior going on. When the server first starts and the scoreboard gets deserialized
        // from the JSON file, the scoreboard gets applied correctly, but if the player relogs nothing works anymore
        // because the initial scoreboard we deserialized got modified although we clone it.
        LabsScoreboard scoreboard = LabsScoreboardFactory.createInstance(DungeonFightersPlugin.getInstance().getDataFolder() + "/scoreboard.json");

        DungeonFightersPlugin.game.getPlayerManager().getPlayers().get(e.getPlayer().getUniqueId()).setScoreboard(scoreboard);
    }

    @EventHandler
    public void onPlayerPickUpItem(PlayerPickupItemEvent e) {
        DungeonFighter fighter = DungeonFightersPlugin.game.getPlayerManager().getPlayers().get(e.getPlayer().getUniqueId());
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
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player died = e.getEntity();
        Player killer = died.getKiller();

        e.setDeathMessage(DungeonFightersPlugin.getInstance().getChatPrefix() + DungeonFightersPlugin.getInstance().getDungeonFighterConfig().getDeathMessage()
                                                                                                     .replace("{player}", died.getDisplayName()));

        Location eye = died.getEyeLocation();

        Particle headParticles = ParticleUtil.createParticle(ParticleEffect.REDSTONE, eye, 0.3F, 0.3F, 0.3F, 0F, 25);
        Particle centerParticles = ParticleUtil.createParticle(ParticleEffect.REDSTONE, eye.clone().subtract(0, 0.5, 0), 0.3F, 0.3F, 0.3F, 0F, 25);
        Particle feetParticles = ParticleUtil.createParticle(ParticleEffect.REDSTONE, died.getLocation(), 0.3F, 0.3F, 0.3F, 0F, 25);


        // can happen if someone types /kill (which shouldn't happen in a regular match)
        if (killer != null) {
            killer.playSound(killer.getEyeLocation(), Sound.IRONGOLEM_DEATH, 50, 1);
        }
        
        ParticleUtil.sendParticleToPlayer(headParticles, killer);
        ParticleUtil.sendParticleToPlayer(centerParticles, killer);
        ParticleUtil.sendParticleToPlayer(feetParticles, killer);

        e.getEntity().setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onGameFinished() {
       // TODO: disable all tasks.
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) return;

        SpecialItem item = SpecialItemFactory.createItem(e.getItem().getItemMeta().getDisplayName(), e.getPlayer());
        Material material = e.getItem().getType();

        if (item != null && material != Material.BOW) {
            if (item.getRequiredActions().contains(e.getAction())) {
                Player player = e.getPlayer();
                if (item.getType() == SpecialItemType.KNOCKBACK) {
                    ((LoadableItem)item).use(player, System.currentTimeMillis());
                }
                else {
                    item.use(player);
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Projectile projectile = (Projectile) e.getProjectile();
            ArrowMetadataHandler.setMetadata(e.getBow().getItemMeta().getDisplayName(), projectile);
            ArrowTrailTask.launchedProjectiles.add(projectile);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntityType() == EntityType.ARROW) {
            Entity arrow = e.getEntity();
            ArrowTrailTask.launchedProjectiles.remove(e.getEntity());
            List<MetadataValue> values = arrow.getMetadata("damageType");

            if (values.size() > 0) {
                SpecialArrow specialArrow = ((SpecialArrow)values.get(0).value());
                if (specialArrow.executeOnGround()) {
                    specialArrow.groundHit(arrow.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();

        if (damager instanceof Arrow) {
            if (e.getEntity() instanceof Player) {
                List<MetadataValue> values = e.getDamager().getMetadata("damageType");
                if (values.size() > 0) {
                    SpecialArrow specialArrow = (SpecialArrow) values.get(0).value();
                    specialArrow.playerHit((Player) e.getEntity());
                }
            }
        }
        else if (damager instanceof Player) {
            Player player = (Player)damager;
            ItemStack item = player.getItemInHand();
            if (item.getType() == Material.BLAZE_ROD) {
                double modifier = 0.1 * Util.getPower(player.getItemInHand().getItemMeta().getDisplayName());
                e.getEntity().setVelocity(player.getLocation().getDirection().multiply(modifier));
                ItemStackUtil.setName(item, Util.name.replace("{percentage}", "0"));
            }
        }
    }
}