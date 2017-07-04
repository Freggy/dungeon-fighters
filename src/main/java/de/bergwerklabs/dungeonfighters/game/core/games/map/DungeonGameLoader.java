package de.bergwerklabs.dungeonfighters.game.core.games.map;

import de.bergwerklabs.dungeonfighters.Main;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.Dungeon;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import de.bergwerklabs.framework.schematicservice.SchematicService;
import de.bergwerklabs.framework.schematicservice.SchematicServiceBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;
import java.security.SecureRandom;
import java.util.*;

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
        LabsSchematic<StartModuleMetadata> startPoint = this.determineSchematic(dungeon.getStartPoints(), random);
        List<Vector> startLocations = new ArrayList<>();

        for (int x = 0; x < 12; x++) {
            start.add(46, 0, 0);
            this.buildStartPoints(startPoint);
            startLocations.add(startPoint.getMetadata().getEnd());
        }
        this.buildPath(this.determineGames(dungeon.getDungeonGames()).iterator(), startLocations);
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

    private void buildPath(Iterator<DungeonGameWrapper> availableGames, List<Vector> starts) {
        Iterator<BattleZone> battleZones = this.determineBattleZones(2, Main.getInstance().getThemedBattleZoneFolder("temple"), new Random())
                                               .iterator();

        for (int path = 0; path < starts.size(); path++) {
            Vector start = starts.get(path);
            for (int i = 0; i < 11; i++) {
                if (i % 4 == 0) {
                    if (battleZones.hasNext())
                        start = this.buildBattleZonePart(start, battleZones.next(), this.getPartByPosition(path, starts.size() - 1));
                }
                else {
                    this.buildGame(availableGames.next(), start);
                }
            }
        }
    }

    /**
     * Randomly picks {@link BattleZone}s that will be generated.
     *
     * @param count Number of {@link BattleZone}s in the path.
     * @param battleZones Folder containing a list of all available {@link BattleZone}s of a specific theme.
     * @param random Used for randomness.
     * @return a List of {@link BattleZone}s that will be generated in the path.
     */
    private List<BattleZone> determineBattleZones(int count, File battleZones, Random random) {
        List<BattleZone> pickedZones = new ArrayList<>();
        List<File> zones = Arrays.asList(battleZones.listFiles());

        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(zones.size());
            pickedZones.add(new BattleZone(zones.get(randomIndex), this.service));
            zones.remove(randomIndex);
        }
        return pickedZones;
    }

    private BattleZone.Part getPartByPosition(int currentPosition, int endIndex) {
        if (currentPosition == 0) return BattleZone.Part.START;
        else if (currentPosition == endIndex) return BattleZone.Part.END;
        else return BattleZone.Part.MIDDLE;
    }

    private Vector buildBattleZonePart(Vector toPlace, BattleZone zone, BattleZone.Part part) {
        switch (part) {
            case START:  return this.placeModule(zone.getStart(), toPlace);
            case MIDDLE: return this.placeModule(zone.getMiddle(), toPlace);
            case END:    return this.placeModule(zone.getEnd(), toPlace);
            default: return null;
        }
    }

    private Vector buildGame(DungeonGameWrapper game, Vector start) {
        Main.game.getGames().add(game);
        // TODO: paste schem.
    }

    private Vector placeModule(LabsSchematic<ModuleMetadata> schematic, Vector to) {
        schematic.pasteAsync("spawn", to);
        return schematic.getMetadata().getEnd();
    }

    /**
     * Determines a {@link LabsSchematic}.
     * @param schematics
     * @param random
     * @return
     */
    private LabsSchematic determineSchematic(List<File> schematics, Random random) {
        return this.service.createSchematic(schematics.get(random.nextInt(schematics.size())));
    }

    /**
     *
     * @param availableGames
     * @return
     */
    private List<DungeonGameWrapper> determineGames(List<DungeonGameWrapper> availableGames) {
        SecureRandom random = new SecureRandom();
        List<DungeonGameWrapper> chosenGames = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            int index = random.nextInt(availableGames.size());
            DungeonGameWrapper game = availableGames.get(index);
            chosenGames.add(game);
            availableGames.remove(index);
        }
        return chosenGames;
    }
}