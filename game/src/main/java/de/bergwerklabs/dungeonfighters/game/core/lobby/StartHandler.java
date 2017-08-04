package de.bergwerklabs.dungeonfighters.game.core.lobby;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfig;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.games.GameUpdateTask;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.DungeonPath;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.DungeonPathLoader;
import de.bergwerklabs.framework.commons.spigot.game.LabsPlayer;
import de.bergwerklabs.framework.commons.spigot.general.timer.LabsTimer;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboard;
import de.bergwerklabs.framework.commons.spigot.scoreboard.Row;
import de.bergwerklabs.framework.commons.spigot.title.Title;
import de.bergwerklabs.util.effect.Particle;
import de.bergwerklabs.util.mechanic.StartTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.UUID;

/**
 * Created by Yannic Rieger on 19.07.2017.
 * <p> This class contains a methods that will be executed when the {@link StartTimer} hits zero.
 *
 * @author Yannic Rieger
 */
public class StartHandler implements StartTimer.StartHandler {

    private LabsTimer timer = new LabsTimer(10, (timeLeft) -> {

        Bukkit.getOnlinePlayers().forEach(player -> {
            Particle.sendParticle(player, Particle.ParticleEffect.ENCHANTMENT_TABLE, player.getLocation(), 4F, 4F, 4F, 0, 200);
        });

        if (timeLeft < 6 && timeLeft != 0) {
            if (timeLeft < 4) {
                this.displayTitle(new Title("§b" + String.valueOf(timeLeft), null, 0, 0, 60));
            }
            this.broadcastTime(timeLeft);
            this.playSound(Sound.CLICK, 100, 1);
        }

        if (timeLeft == 0) {
            //this.removeWall();
            this.unfreeze();
            this.displayTitle(new Title("§9LOS!", null, 20 ,20, 20));
            this.playSound(Sound.ENDERDRAGON_GROWL, 1, 50);
        }
    });

    @Override
    public void handle(Player[] players) {
        Queue<DungeonPath> paths = new DungeonPathLoader().buildDungeons(DungeonFightersPlugin.game.getDungeon(), Bukkit.getOnlinePlayers().size(), "temple");
        Iterator<Location> spawns = DungeonFightersPlugin.game.getSpawns().iterator();
        this.displayTitle(DungeonFightersPlugin.getInstance().getDungeonFighterConfig().getIntermissionTitle());

        Bukkit.getScheduler().runTaskLater(DungeonFightersPlugin.getInstance(), () -> {
            for (DungeonFighter fighter : DungeonFightersPlugin.game.getPlayerManager().getPlayers().values()) {
                DungeonPath path = paths.poll();
                this.createScoreBoard(fighter.getPlayer());
                fighter.getPlayer().teleport(path.getSpawn());
                fighter.getSession().setCurrentGame(path.getGames().poll());
                fighter.freeze();

                if (spawns.hasNext())
                    fighter.getPlayer().teleport(spawns.next());

                fighter.getSession().getGames().addAll(path.getGames());
            }
        }, 20 * 3);


        Bukkit.getScheduler().runTaskLaterAsynchronously(DungeonFightersPlugin.getInstance(), () -> DungeonFightersPlugin.animation.play(), 90L);
        Bukkit.getScheduler().runTaskLater(DungeonFightersPlugin.getInstance(), () -> {
            this.broadcastTime(10);
            timer.start();
        }, 120L);

        HandlerList.unregisterAll(DungeonFightersPlugin.lobbyListener);
        Bukkit.getServer().getPluginManager().registerEvents( DungeonFightersPlugin.moduleEventHandler, DungeonFightersPlugin.getInstance());
        Bukkit.getScheduler().runTaskTimer(DungeonFightersPlugin.getInstance(), new GameUpdateTask(), 0, 20L);
    }

    /**
     * Broadcasts a {@link Title}.
     *
     * @param title Title to broadcast.
     */
    private void displayTitle(Title title) {
        Bukkit.getOnlinePlayers().forEach(title::display);
    }

    /**
     * Removes the wall in front of the players.
     */
    private void removeWall() {
    }

    /**
     * Plays a {@link Sound} to all players with a given pitch.
     *
     * @param sound Sound to play.
     * @param pitch Pitch of the given sound.
     */
    private void playSound(Sound sound, float pitch, float volume) {
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getEyeLocation(), sound, volume, pitch));
    }

    /**
     * Unfreezes all players.
     */
    private void unfreeze() {
        DungeonFightersPlugin.game.getPlayerManager().getPlayers().values().forEach(LabsPlayer::unfreeze);
    }

    /**
     * Creates and sets the scoreboard to all {@link Player}s.
     *
     * @param player Player to display the scoreboard to.
     */
    private void createScoreBoard(Player player) {
        LabsScoreboard scoreboard = new LabsScoreboard("§eDungeonFighters §6❘ §b{time}", "module");

        scoreboard.addRow(101, new Row(scoreboard, "§e§e§e"))
                  .addRow(100, new Row(scoreboard, "§e§lServer-IP:"))
                  .addRow(99, new Row(scoreboard, "bergwerkLABS.de"))
                  .addRow(98, new Row(scoreboard, "§e"))
                  .addRow(97, new Row(scoreboard, "§e§lGold:"))
                  .addRow(96, new Row(scoreboard, "§7§l0"))
                  .addRow(95, new Row(scoreboard, "§e§e"));

        HashMap<UUID, DungeonFighter> players = DungeonFightersPlugin.game.getPlayerManager().getPlayers();

        players.values().forEach(fighter -> {
            Player p = fighter.getPlayer();

            if (player.getUniqueId().equals(p.getUniqueId())) {
                scoreboard.addRow(0, new Row(scoreboard, ChatColor.UNDERLINE + p.getName()));
            }
            else {
                scoreboard.addRow(0, new Row(scoreboard, p.getName()));
            }
        });
        players.get(player.getUniqueId()).setScoreboard(scoreboard);
    }


    /**
     * Broadcasts the given time to all players in a format defined in {@link DungeonFighterConfig#getGameCountdownMessage()}.
     *
     * @param time amount of seconds
     */
    private void broadcastTime(int time) {
        Bukkit.broadcastMessage(DungeonFightersPlugin.getInstance().getChatPrefix() +
                                DungeonFightersPlugin.getInstance().getDungeonFighterConfig()
                                                     .getGameCountdownMessage()
                                                     .replace("{time}", String.valueOf(time)));
    }
}
