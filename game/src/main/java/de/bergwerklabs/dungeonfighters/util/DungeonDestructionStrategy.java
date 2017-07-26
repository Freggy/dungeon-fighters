package de.bergwerklabs.dungeonfighters.util;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.DestructionStrategy;
import de.bergwerklabs.util.effect.Particle;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Yannic Rieger on 20.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonDestructionStrategy implements DestructionStrategy {

    private int counter = 0, currentLayer = 15, maxLayer = 40; // TODO: this will be configurable
    private Iterator<Block> iterator;
    private BukkitTask task;

    @Override
    public void destruct(Chunk chunk) {
        iterator = getShuffledLayer(currentLayer, chunk);

        task = Bukkit.getScheduler().runTaskTimer(DungeonFightersPlugin.getInstance(), () -> {
            if (iterator.hasNext()) {
                Block block = iterator.next();
                Material material = block.getType();
                byte data = block.getData();
                block.setType(Material.AIR);

                List<Player> players = Arrays.stream(chunk.getEntities()).filter(entity -> entity instanceof Player).map(entity -> (Player) entity).collect(Collectors.toList());

                if (counter % 2 == 0) players.forEach(player -> this.sendParticle(player, block.getLocation()));
                if (counter % 2 == 0) players.forEach(player -> player.damage(0.3));
                if (counter % 10 == 0) players.forEach(player -> chunk.getWorld().playSound(player.getEyeLocation(), Sound.EXPLODE, 100, 1));

                chunk.getWorld().spawnFallingBlock(block.getLocation(), material, data);
                counter++;
            }
            else {
                if (currentLayer == maxLayer) task.cancel();
                iterator = getShuffledLayer(this.currentLayer++, chunk);
            }
        }, 20L, 1L);
    }

    private Iterator<Block> getShuffledLayer(int layerY, Chunk chunk) {
        List<Block> blocks = new ArrayList<>();
        for (int x = 1; x < 16; x++) {
            for (int z = 1; z < 16; z++) {
                Block b = chunk.getBlock(x, layerY, z);
                System.out.println(b.getType());
                if (b.getType() != Material.AIR) {
                    if (b.getRelative(BlockFace.UP).getType() != Material.AIR && b.getRelative(BlockFace.DOWN).getType() != Material.AIR) continue;
                    blocks.add(b);
                }
            }
        }
        Collections.shuffle(blocks);
        return blocks.iterator();
    }

    private void sendParticle(Player player, Location location) {
        double chance = Math.random();

        if (chance < 0.3) {
            try {
                Particle p = new Particle(Particle.ParticleEffect.CLOUD, location, 0.2F, 0.2F, 0.2F, 0.1F, 7);
                p.sendToPlayer(player);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
