package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.dungeonfighters.Main;
import de.bergwerklabs.dungeonfighters.api.SpecialArrow;
import de.bergwerklabs.dungeonfighters.api.SpecialItem;
import de.bergwerklabs.dungeonfighters.game.core.specialitems.SpecialItemFactory;
import de.bergwerklabs.dungeonfighters.game.core.specialitems.arrow.ArrowMetadataHandler;
import de.bergwerklabs.dungeonfighters.util.ParticleUtil;
import de.bergwerklabs.dungeonfighters.util.RoundSummaryMapRenderer;
import de.bergwerklabs.framework.commons.spigot.general.LabsTabList;
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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.io.File;
import java.security.SecureRandom;
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
 * </ul>
 * @author Yannic Rieger
 */
public class DungeonFightersEventHandler implements Listener {

    private SecureRandom random = new SecureRandom();
    private LabsTabList tabList = new LabsTabList(new String[] {"Hallo", "Header"}, new String[] { "Hallo", "Footer" });

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        String[] messages = Main.getInstance().getDungeonFighterConfig().getJoinMessage();
        tabList.send(e.getPlayer());
        int randomIndex = random.nextInt(messages.length);
        e.setJoinMessage(Main.getInstance().getChatPrefix() + messages[randomIndex].replace("{player}", e.getPlayer().getDisplayName()));
        //Bukkit.getPlayer(UUID.fromString("d44e59e6751f431d83f386b13c5306aa")).sendMessage(Main.getInstance().getChatPrefix() + "Du Pflaume! c:");

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
    public void onMapInitialized(MapInitializeEvent e) {
        e.getMap().removeRenderer(e.getMap().getRenderers().get(0));
        e.getMap().setScale(MapView.Scale.FARTHEST);
        e.getMap().addRenderer(new RoundSummaryMapRenderer(new File(Main.getInstance().getDataFolder() + "/image.png")));
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();

        if (block.getType() == Material.EMERALD_ORE) {
            Particle particle = ParticleUtil.createParticle(ParticleEffect.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.5, 0.5),0.2F,0.3F,0.2F,0.2F,20);
            ParticleUtil.sendParticleToPlayer(particle, e.getPlayer());

            Random r = new Random();
            int max = Main.getInstance().getDungeonFighterConfig().getMaxEmeraldDrop();
            int min = Main.getInstance().getDungeonFighterConfig().getMinEmeraldDrop();
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

        e.setDeathMessage(Main.getInstance().getChatPrefix() + Main.getInstance().getDungeonFighterConfig().getDeathMessage()
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
        Main.getInstance().getTasks().forEach(BukkitTask::cancel);
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) return;

        SpecialItem item = SpecialItemFactory.createItem(e.getItem().getItemMeta().getDisplayName());
        Material material = e.getItem().getType();

        if (item != null && material != Material.BOW) {
            if (item.getRequiredActions().contains(e.getAction())) {
                item.use(e.getPlayer());
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Projectile projectile = (Projectile) e.getProjectile();
            ArrowMetadataHandler.setMetadata(e.getBow().getItemMeta().getDisplayName(), projectile);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntityType() == EntityType.ARROW) {
            Entity arrow = e.getEntity();
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
        if (e.getDamager() instanceof Arrow) {
            if (e.getEntity() instanceof Player) {
                List<MetadataValue> values = e.getDamager().getMetadata("damageType");
                if (values.size() > 0) {
                    SpecialArrow specialArrow = (SpecialArrow) values.get(0).value();
                    specialArrow.playerHit((Player) e.getEntity());
                }
            }
        }
    }
}