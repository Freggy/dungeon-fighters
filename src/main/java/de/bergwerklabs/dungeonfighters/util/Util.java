package de.bergwerklabs.dungeonfighters.util;

import de.bergwerklabs.util.effect.Particle;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class Util {

    public static final String name = "§eKnockback-Stick §6❘ §eAufladung:§b {percentage}%";
    private static final World gameWorld = Bukkit.getWorld("spawn");


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
                locations.add(new Location(Bukkit.getWorld("spawn"), x , y, min.getBlockZ()));
            }
        }
        return locations;
    }

    public static void openEntrance(Player player, List<Location> locations) {
        locations.forEach(location -> {
            location.getBlock().setType(Material.AIR);
            Particle.sendParticle(player, Particle.ParticleEffect.CLOUD, location.add(0, 0, 1), 0.2F, 0.2F, 0.2F, 0, 3);
        });
    }

    public static void closeEntrance(Player player, List<Location> locations) {
        locations.forEach(location -> {
            location.getBlock().setType(Material.SMOOTH_BRICK);
            Particle.sendParticle(player, Particle.ParticleEffect.CLOUD, location.add(0, 0, 1), 0.2F, 0.2F, 0.2F, 0, 3);
        });
    }

}
