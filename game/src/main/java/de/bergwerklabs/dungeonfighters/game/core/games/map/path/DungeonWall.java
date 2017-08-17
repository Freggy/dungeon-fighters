package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.commons.ParticleUtil;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation.DungeonModuleConstructor;
import de.bergwerklabs.util.effect.Particle;
import org.bukkit.Location;

public class DungeonWall {

    private CuboidRegion region;
    private Location particleLocation, placeLocation;
    private Particle particle;

    /**
     *
     * @param region
     * @param particleLocation
     */
    public DungeonWall(CuboidRegion region, Location placeLocation, Location particleLocation) {
        this.region = region;
        this.particleLocation = particleLocation;
        this.placeLocation = placeLocation;
        this.particle = ParticleUtil.createParticle(Particle.ParticleEffect.CLOUD, particleLocation.clone().add(2, 2, 1), 2F, 1.5F, 2F, 0, 30);
    }

    /**
     *
     */
    public void close() {
        DungeonModuleConstructor.getBarrierWalls().next().pasteAsync(DungeonFightersPlugin.moduleWorld.getName(), this.placeLocation.toVector());
        ParticleUtil.broadcastParticle(this.particle);
    }

    /**
     *
     */
    public void open() {
        TaskManager.IMP.async(() -> {
            EditSession session = new EditSessionBuilder(FaweAPI.getWorld(particleLocation.getWorld().getName())).fastmode(true).checkMemory(true).build();
            session.setBlocks(region, new BaseBlock(0, 0));
            session.flushQueue();
        });
        ParticleUtil.broadcastParticle(this.particle);
    }
}
