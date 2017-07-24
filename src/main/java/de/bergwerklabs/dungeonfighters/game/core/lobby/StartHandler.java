package de.bergwerklabs.dungeonfighters.game.core.lobby;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.games.GameUpdateTask;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameLoader;
import de.bergwerklabs.dungeonfighters.util.Util;
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
import java.util.UUID;

/**
 * Created by Yannic Rieger on 19.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class StartHandler implements StartTimer.StartHandler {

    private LabsTimer timer = new LabsTimer(10, (timeLeft) -> {

        Bukkit.getOnlinePlayers().forEach(player -> {
            Particle.sendParticle(player, Particle.ParticleEffect.ENCHANTMENT_TABLE, player.getLocation(), 4F, 4F, 4F, 0, 200);
        });

        if (timeLeft < 4) {
            this.displayTitle(new Title("§b" + String.valueOf(timeLeft), null,20 ,20, 60));
            this.playSound(Sound.NOTE_BASS);
        }

        if (timeLeft == 0) {
            this.removeWall();
            this.unfreeze();
            this.displayTitle(new Title("§9LOS!", null,20 ,20, 20));
            this.playSound(Sound.ENDERDRAGON_GROWL);
        }
    });

    @Override
    public void handle(Player[] players) {
        new DungeonGameLoader().buildDungeons(DungeonFightersPlugin.game.getDungeon(), Bukkit.getOnlinePlayers().size(), "temple");
        Iterator<Location> spawns = DungeonFightersPlugin.game.getSpawns().iterator();
        this.displayTitle(DungeonFightersPlugin.getInstance().getDungeonFighterConfig().getIntermissionTitle());

        Bukkit.getScheduler().runTaskLater(DungeonFightersPlugin.getInstance(), () -> {
            for (Player player : players) {
                this.createScoreBoard(player);
                if (spawns.hasNext()) {
                    LabsPlayer.freeze(player);
                    player.teleport(spawns.next());
                }
            }
        }, 20 * 3);


        Bukkit.getScheduler().runTaskLaterAsynchronously(DungeonFightersPlugin.getInstance(), () -> DungeonFightersPlugin.animation.play(), 90L);
        Bukkit.getScheduler().runTaskLater(DungeonFightersPlugin.getInstance(), () -> {
            Bukkit.broadcastMessage(DungeonFightersPlugin.getInstance().getChatPrefix() + "Das Spiel startet in §b10 §7Sekunden");
            timer.start();
        }, 120L);

        HandlerList.unregisterAll(DungeonFightersPlugin.lobbyListener);
        Bukkit.getServer().getPluginManager().registerEvents( DungeonFightersPlugin.moduleEventHandler, DungeonFightersPlugin.getInstance());
        Bukkit.getScheduler().runTaskTimer(DungeonFightersPlugin.getInstance(), new GameUpdateTask(), 0, 20L);
    }

    private void displayTitle(Title title) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            title.display(player);
        });
    }

    private void removeWall() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Util.openEntrance(player, DungeonFightersPlugin.game.getModules().get(Util.getChunkCoordinateString(player.getLocation().getChunk())).getBlockLocations());
        });
    }

    private void playSound(Sound sound) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.playSound(player.getEyeLocation(), sound, 1, 50);
        });
    }

    private void unfreeze() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            LabsPlayer.unfreeze(player);
        });
    }

    private void createScoreBoard(Player player) {
        LabsScoreboard scoreboard = new LabsScoreboard("§eDungeonFighters §6❘ §b{time}", "module");
        scoreboard.addRow(100, new Row(scoreboard, "§e§lServer-IP:"))
                  .addRow(99, new Row(scoreboard, "bergwerkLABS.de"))
                  .addRow(98, new Row(scoreboard, "§e"))
                  .addRow(97, new Row(scoreboard, "§e§lGold:"))
                  .addRow(96, new Row(scoreboard, "§f§l0"))
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
}
