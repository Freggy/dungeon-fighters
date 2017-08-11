package de.bergwerklabs.dungeonfighters.commons;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation.DungeonModuleConstructor;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import de.bergwerklabs.util.effect.Particle;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class Util {

    public static final String name = "§eKnockback-Stick §6❘ §eAufladung:§b {percentage}%";

    public static int getPower(String s) {
       return Integer.valueOf(s.split(":")[1].replace("%", "")
                                                .replace("§b", "").trim());
    }

    public static String getChunkCoordinateString(Chunk chunk) {
        return Arrays.toString(new Integer[] { chunk.getX(), chunk.getZ() });
    }

    public static List<Location> getDoorLocations(Location min, Location max) {
        ArrayList<Location> locations = new ArrayList<>();
        for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
            for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                locations.add(new Location(DungeonFightersPlugin.moduleWorld, x, y, min.getBlockZ()));
            }
        }
        return locations;
    }

    public static void openEntrances() {
       DungeonModuleConstructor.getStartWallBlockLocations().forEach(location -> {
            location.getBlock().setType(Material.AIR);
            Particle.broadcastParticle(Bukkit.getOnlinePlayers(), Particle.ParticleEffect.CLOUD, location.add(0, 0, 1), 0.2F, 0.2F, 0.2F, 0, 3);
        });
    }

    public static void closeEntrance(Player player, Location toPlace, Iterator<LabsSchematic<ModuleMetadata>> barrierWalls) {
        Bukkit.getScheduler().runTaskLater(DungeonFightersPlugin.getInstance(), () -> {
            Location to = toPlace.clone().add(0, 1, -1);
            barrierWalls.next().pasteAsync(DungeonFightersPlugin.moduleWorld.getName(), to.toVector());
            player.playSound(player.getEyeLocation(), Sound.ANVIL_LAND, 100, 100);
            ParticleUtil.sendParticleToPlayer(ParticleUtil.createParticle(Particle.ParticleEffect.CLOUD, to.clone().add(2, 2, 1), 2F, 1.5F, 2F, 0, 30), player);
        }, 10L);
    }




}
