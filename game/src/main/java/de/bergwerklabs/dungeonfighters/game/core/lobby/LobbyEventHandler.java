package de.bergwerklabs.dungeonfighters.game.core.lobby;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.framework.commons.spigot.entity.npc.behavior.LookAtPlayerBehavior;
import de.bergwerklabs.framework.commons.spigot.entity.npc.event.Action;
import de.bergwerklabs.framework.commons.spigot.entity.npc.event.NpcInteractEvent;
import de.bergwerklabs.framework.commons.spigot.general.LabsTabList;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.security.SecureRandom;

/**
 * Created by Yannic Rieger on 19.07.2017.
 * <p> Class which contains Methods for handling events while the players are waiting for the game to start.
 *
 * @author Yannic Rieger
 */
public class LobbyEventHandler implements Listener {

    private SecureRandom random = new SecureRandom();
    private LabsTabList tabList = new LabsTabList(new String[] {"Hallo", "Header"}, new String[] { "Hallo", "Footer" });


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        DungeonFightersPlugin.npc.addBehavior(new LookAtPlayerBehavior(true, 10, 0F, -171F));

        // DEBUG START
        player.setWalkSpeed(0.2F);
        player.removePotionEffect(PotionEffectType.JUMP);
        player.removePotionEffect(PotionEffectType.ABSORPTION);
        // DEBUG END

        player.setGameMode(GameMode.ADVENTURE);

        String[] messages = DungeonFightersPlugin.getInstance().getDungeonFighterConfig().getJoinMessage();
        tabList.send(player);
        int randomIndex = random.nextInt(messages.length);
        e.setJoinMessage(DungeonFightersPlugin.getInstance().getChatPrefix() + messages[randomIndex].replace("{player}", player.getDisplayName()));

        Location spawnLocation = new Location(DungeonFightersPlugin.spawnWorld, 31.5, 95, 96.5);
        spawnLocation.setYaw(-90);
        spawnLocation.setPitch(0);

        player.teleport(spawnLocation);

        DungeonFightersPlugin.game.getPlayerManager().getPlayers().put(player.getUniqueId(), new DungeonFighter(e.getPlayer()));

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        DungeonFightersPlugin.game.getPlayerManager().getPlayers().remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.QUARTZ_BLOCK)) {
            Vector boost = player.getEyeLocation().getDirection().normalize();
            boost.multiply(6);
            player.setVelocity(new Vector(boost.getX(), 1, boost.getZ()));
            player.playSound(player.getEyeLocation(), Sound.ENDERDRAGON_WINGS, 2, 2);
        }
    }


    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void  onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(NpcInteractEvent e) {
        if (e.getNpc().getEntityId() == DungeonFightersPlugin.npc.getEntityId() && e.getAction() == Action.RIGHT_CLICK) {
            Bukkit.broadcastMessage(e.getPlayer().getName());
        }
    }
}
