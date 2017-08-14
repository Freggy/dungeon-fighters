package de.bergwerklabs.dungeonfighters.commons;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation.DungeonModuleConstructor;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import de.bergwerklabs.util.effect.HoverText;
import de.bergwerklabs.util.effect.Particle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Iterator;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class Util {

    public static CuboidRegion getDoorLocations(Location point1, Location point2) {
        return new CuboidRegion(new Vector(point1.getX(), point1.getY(), point1.getZ()),
                                new Vector(point2.getX(), point2.getY(), point2.getZ()));
    }

    public static void openEntrance(String world, CuboidRegion region) {
        TaskManager.IMP.async(() -> {
            EditSession session = new EditSessionBuilder(FaweAPI.getWorld(world)).fastmode(true).checkMemory(true).build();
            session.setBlocks(region, new BaseBlock(0, 0));
            session.flushQueue();
        });
    }

    public static void openEntrances() {
       DungeonModuleConstructor.getStartWallBlockLocations().forEach(region -> {
           openEntrance("module", region);
           //Particle.broadcastParticle(Bukkit.getOnlinePlayers(), Particle.ParticleEffect.CLOUD, location.add(0, 0, 1), 0.2F, 0.2F, 0.2F, 0, 3);
        });
    }

    public static void closeEntrance(Player player, Location toPlace, Iterator<LabsSchematic<ModuleMetadata>> barrierWalls) {
        Bukkit.getScheduler().runTaskLater(DungeonFightersPlugin.getInstance(), () -> {
            Location to = toPlace.clone().add(0, 1, -1);
            barrierWalls.next().pasteAsync(DungeonFightersPlugin.moduleWorld.getName(), to.toVector());
            player.playSound(player.getEyeLocation(), Sound.ANVIL_LAND, 1, 100);
            ParticleUtil.sendParticleToPlayer(ParticleUtil.createParticle(Particle.ParticleEffect.CLOUD, to.clone().add(2, 2, 1), 2F, 1.5F, 2F, 0, 30), player);
        }, 10L);
    }

    public static BukkitTask getEmptyBukkitTask() {
        return new BukkitTask() {
            @Override
            public int getTaskId() { return -1; }

            @Override
            public Plugin getOwner() { return null; }

            @Override
            public boolean isSync() { return false; }

            @Override
            public void cancel() {}
        };
    }

    /**
     *
     * @param player
     * @param text
     * @param time
     */
    public static void sendTimerHoverText(Player player, String text, int time) {
        HoverText.sendHoverTextUpdate(player, String.format(text, time / 60, time % 60));
    }
}
