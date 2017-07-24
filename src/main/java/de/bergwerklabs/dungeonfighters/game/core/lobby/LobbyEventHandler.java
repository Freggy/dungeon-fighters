package de.bergwerklabs.dungeonfighters.game.core.lobby;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.framework.commons.spigot.entity.npc.behavior.LookAtPlayerBehavior;
import de.bergwerklabs.framework.commons.spigot.general.LabsTabList;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class LobbyEventHandler implements Listener {

    private SecureRandom random = new SecureRandom();
    private LabsTabList tabList = new LabsTabList(new String[] {"Hallo", "Header"}, new String[] { "Hallo", "Footer" });
    private float yaw  = 0;


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        DungeonFightersPlugin.npc.addBehavior(new LookAtPlayerBehavior(true, 10, 0F, -171F));

        // DEBUG START
        player.setWalkSpeed(0.2F);
        player.removePotionEffect(PotionEffectType.JUMP);
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
    public int getFixLocation(double pos) {
        return (int) Math.floor(pos * 32.0D);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        DungeonFightersPlugin.game.getPlayerManager().getPlayers().remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.QUARTZ_BLOCK)) {
            Vector doublejump = player.getEyeLocation().getDirection().normalize();
            doublejump.multiply(6);
            player.setVelocity(new Vector(doublejump.getX(), 1, doublejump.getZ()));
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


}
