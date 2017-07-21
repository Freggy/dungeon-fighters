package de.bergwerklabs.dungeonfighters.game.core.lobby;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.framework.commons.spigot.general.LabsTabList;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboard;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboardFactory;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        // DEBUG START
        player.setWalkSpeed(0.2F);
        player.removePotionEffect(PotionEffectType.JUMP);
        // DEBUG END

        String[] messages = DungeonFightersPlugin.getInstance().getDungeonFighterConfig().getJoinMessage();
        tabList.send(player);
        int randomIndex = random.nextInt(messages.length);
        e.setJoinMessage(DungeonFightersPlugin.getInstance().getChatPrefix() + messages[randomIndex].replace("{player}", player.getDisplayName()));

        Location spawnLocation = new Location(DungeonFightersPlugin.spawnWorld, 31.5, 95, 96.5);
        spawnLocation.setYaw(-90);
        spawnLocation.setPitch(0);

        player.teleport(spawnLocation);

        // NOTE:
        // There is some strange behavior going on. When the server first starts and the scoreboard gets deserialized
        // from the JSON file, the scoreboard gets applied correctly, but if the player relogs nothing works anymore
        // because the initial scoreboard we deserialized got modified although we clone it.
        LabsScoreboard scoreboard = LabsScoreboardFactory.createInstance(DungeonFightersPlugin.getInstance().getDataFolder() + "/scoreboard.json");

        // load it everytime again?
        DungeonFightersPlugin.game.getPlayerManager().getPlayers()
                                  .put(player.getUniqueId(), new DungeonFighter(e.getPlayer(), scoreboard));

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        DungeonFightersPlugin.game.getPlayerManager().getPlayers().remove(e.getPlayer().getUniqueId());
    }


}
