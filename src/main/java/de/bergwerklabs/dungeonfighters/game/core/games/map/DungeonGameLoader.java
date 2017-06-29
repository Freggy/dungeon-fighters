package de.bergwerklabs.dungeonfighters.game.core.games.map;

import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.Dungeon;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import de.bergwerklabs.framework.schematicservice.SchematicService;
import de.bergwerklabs.framework.schematicservice.SchematicServiceBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Created by Yannic Rieger on 26.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonGameLoader {

    private final Location start = new Location(Bukkit.getWorld("spawn"), 270, 69, 93); // TODO: change world name
    private final SchematicService<ModuleMetadata> service = new SchematicServiceBuilder<ModuleMetadata>().setDeserializer(new StartModuleMetadataDeserializerImpl()).build();

    public void buildDungeons(Dungeon dungeon, List<Player> players) {
        Random random = new Random();
        LabsSchematic startPoint = this.determineSchematic(dungeon.getStartPoints(), random);

        for (int x = 0; x < 12; x++) {
            start.add(46, 0, 0);
            this.buildStartPoints(startPoint);
        }
    }

    /**
     *
     * @param startPoint
     */
    private void buildStartPoints(LabsSchematic startPoint) {
        Chunk chunk = this.start.getChunk();

        Location location = chunk.getBlock(0, 69, 0).getLocation();

        startPoint.pasteAsync(this.start.getWorld().getName(), location.toVector());
    }

    /**
     *
     * @param schematics
     * @param random
     * @return
     */
    private LabsSchematic determineSchematic(List<File> schematics, Random random) {
        return this.service.createSchematic(schematics.get(random.nextInt(schematics.size())));
    }
}