package de.bergwerklabs.dungeonfighters.game.core.lobby;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.games.GameUpdateTask;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameLoader;
import de.bergwerklabs.dungeonfighters.util.Util;
import de.bergwerklabs.framework.commons.spigot.game.LabsPlayer;
import de.bergwerklabs.framework.commons.spigot.general.timer.LabsTimer;
import de.bergwerklabs.framework.commons.spigot.title.Title;
import de.bergwerklabs.util.effect.Particle;
import de.bergwerklabs.util.mechanic.StartTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.Iterator;

/**
 * Created by Yannic Rieger on 19.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class StartHandler implements StartTimer.StartHandler {

    private LabsTimer timer = new LabsTimer(10, (timeLeft) -> {
        this.displayTitle(new Title("§b" + String.valueOf(timeLeft), null,20 ,20, 60));
        this.playSound(Sound.NOTE_BASS);

        Bukkit.getOnlinePlayers().forEach(player -> {
            Particle.sendParticle(player, Particle.ParticleEffect.ENCHANTMENT_TABLE, player.getLocation(), 4F, 4F, 4F, 0, 200);
        });

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
        this.displayTitle(new Title("§aDu betrittst nun den Dungeon§b...", null, 20, 20, 20));

        Bukkit.getScheduler().runTaskLater(DungeonFightersPlugin.getInstance(), () -> {
            for (Player player : players) {
                if (spawns.hasNext()) {
                    LabsPlayer.freeze(player);
                    player.teleport(spawns.next());
                }
            }
            Bukkit.getScheduler().runTaskLater(DungeonFightersPlugin.getInstance(), () -> DungeonFightersPlugin.animation.play(), 20L);
            Bukkit.getScheduler().runTaskLater(DungeonFightersPlugin.getInstance(), () -> timer.start(), 40L);
        }, 20 * 3);

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
}
