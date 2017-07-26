package de.bergwerklabs.dungeonfighters.game.core.games.map;

import com.google.common.collect.Iterables;
import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.games.map.metadata.StartModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.games.map.metadata.StartModuleMetadataDeserializerImpl;
import de.bergwerklabs.dungeonfighters.game.core.Dungeon;
import de.bergwerklabs.dungeonfighters.game.core.games.mechanic.BattleZoneMechanic;
import de.bergwerklabs.dungeonfighters.game.core.games.mechanic.EndMechanic;
import de.bergwerklabs.dungeonfighters.util.Util;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import de.bergwerklabs.framework.schematicservice.SchematicService;
import de.bergwerklabs.framework.schematicservice.SchematicServiceBuilder;
import org.bukkit.Location;
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

    private Location start = new Location(DungeonFightersPlugin.moduleWorld, 270, 69, 93); // TODO: change world name
    private Dungeon dungeon;
    private BattleZoneMechanic battleZoneMechanic = new BattleZoneMechanic();
    private EndMechanic endMechanic = new EndMechanic();
    private LabsSchematic<ModuleMetadata> end;
    private String theme;

    /**
     *
     *
     * @param dungeon
     * @param players
     * @param theme
     */
    public void buildDungeons(Dungeon dungeon, int players, String theme) {
        Random random = new Random();
        SchematicService<StartModuleMetadata> service = new SchematicServiceBuilder<StartModuleMetadata>().setDeserializer(new StartModuleMetadataDeserializerImpl()).build();
        LabsSchematic<StartModuleMetadata> startPoint = this.determineSchematic(dungeon.getStartPoints(), service, random);
        List<Location> startLocations = new ArrayList<>();
        Location newLocation = this.start.getChunk().getBlock(0, 69, 0).getLocation().clone();
        this.dungeon = dungeon;
        this.theme = theme;
        this.end = this.determineSchematic(this.dungeon.getEndPoints(), ModuleMetadata.getService(), random);

        for (int x = 0; x < players; x++) {
            this.buildStartPoints(startPoint, newLocation);
            Location end = newLocation.clone().subtract(startPoint.getMetadata().getEnd().clone().add(new Vector(3,0, -1)));

            newLocation.getChunk().load();

            Location inChunk = end.clone().subtract(0, 0, 1);

            List<Location> blockLoc = this.getWallLocs(inChunk);
            String coord = Util.getChunkCoordinateString(inChunk.getChunk());

            DungeonFightersPlugin.game.getModules().putIfAbsent(coord, new ModuleInfo(blockLoc, startPoint));

            Util.closeEntrance(null, blockLoc);

            startLocations.add(end);
            newLocation.add(46, 0 ,0);
        }

        // Use cycle iterators to avoid the Iterator#hasNext query.
        this.buildPath(Iterables.cycle(this.determineGames(this.dungeon.getDungeonGames())).iterator(), startLocations);
    }

    /**
     *
     * @param startPoint
     */
    private void buildStartPoints(LabsSchematic<StartModuleMetadata> startPoint, Location location) {
        Location spawn = location.clone().subtract(startPoint.getMetadata().getSpawn().clone()).add(0, 1, 0.9);
        spawn.setPitch(0);
        spawn.setYaw(0);
        DungeonFightersPlugin.game.getSpawns().add(spawn);
        startPoint.pasteAsync(this.start.getWorld().getName(), location.toVector());
    }

    private void buildPath(Iterator<DungeonGameWrapper> availableGames, List<Location> starts) {
        Random random =  new Random();

        // Use cycle iterators to avoid the Iterator#hasNext query.
        Iterator<BattleZone> battleZones = Iterables.cycle(this.determineBattleZones(2, DungeonFightersPlugin.getInstance().getThemedBattleZoneFolder(this.theme), random))
                                                    .iterator();

        for (int path = 0; path < starts.size(); path++) {
            Location start = starts.get(path);
            for (int position = 1; position < 13; position++) {
                if (position % 4 == 0 && position != 1 && position != 12) {
                    start = this.buildBattleZonePart(start, battleZones.next(), this.getPartByPosition(path, starts.size() - 1)).add(new Vector(0, 0, 1));
                }
                else if (position == 12) { // end has been reached
                    this.buildEnd(start);
                }
                else {
                   start = this.buildGame(availableGames.next(), start, position).add(new Vector(0, 0, 1));
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

        // adding this to a ArrayList object, if I would do
        // List<File> zones = Arrays.asList(battleZones.listFiles());
        // a UnsupportedOperationException is thrown.
        List<File> zones = new ArrayList<>(Arrays.asList(battleZones.listFiles()));

        for (int i = 0; i < count; i++) {
            if (zones.size() == 0) break; // Only for test purposes
            int randomIndex = random.nextInt(zones.size());
            pickedZones.add(new BattleZone(zones.get(randomIndex)));
            zones.remove(randomIndex);
        }
        return pickedZones;
    }

    private BattleZone.Part getPartByPosition(int currentPosition, int endIndex) {
        if (currentPosition == 0) return BattleZone.Part.START;
        else if (currentPosition == endIndex) return BattleZone.Part.END;
        else return BattleZone.Part.MIDDLE;
    }

    private Location buildBattleZonePart(Location toPlace, BattleZone zone, BattleZone.Part part) {
        String chunkCoords = Util.getChunkCoordinateString(toPlace.getChunk());
        this.battleZoneMechanic.getChunks().add(chunkCoords);
        DungeonFightersPlugin.game.getDungeon().getGamePositions().put(chunkCoords, battleZoneMechanic);

        switch (part) {
            case START:  return this.placeModule(zone.getStart(), toPlace);
            case MIDDLE: return this.placeModule(zone.getMiddle(), toPlace);
            case END:    return this.placeModule(zone.getEnd(), toPlace);
            default: return null;
        }
    }

    private Location buildGame(DungeonGameWrapper game, Location start, int position) {
        DungeonGame dungeonGame = (DungeonGame) game.getGame().clone();
        String chunkCoord = Util.getChunkCoordinateString(start.getChunk());

        System.out.println(StageTier.getStageTierByPosition(position));

        dungeonGame.setStageTier(StageTier.getStageTierByPosition(position));

        DungeonFightersPlugin.game.getDungeon().getGamePositions().put(chunkCoord, dungeonGame);
        dungeonGame.getChunks().add(chunkCoord);


        return this.placeModule(game.getModule(), start);
    }

    private <T extends ModuleMetadata> Location placeModule(LabsSchematic<T> schematic, Location to) {
        schematic.pasteAsync(DungeonFightersPlugin.moduleWorld.getName(), to.toVector());

        to.getChunk().load();

        if (schematic.hasMetadata()) {
            Location endLocation = to.clone().subtract(schematic.getMetadata().getEnd());
            DungeonFightersPlugin.game.getModules().putIfAbsent(Util.getChunkCoordinateString(to.getChunk()),
                                                                new ModuleInfo(this.getWallLocs(to.clone()), schematic));
            return endLocation;
        }
        else
            return null;
    }

    /**
     * Determines a {@link LabsSchematic}.
     *
     * @param schematics
     * @param random
     * @return
     */
    private <T extends ModuleMetadata> LabsSchematic<T> determineSchematic(List<File> schematics, SchematicService<T> service, Random random) {
        return service.createSchematic(schematics.get(random.nextInt(schematics.size())));
    }

    /**
     *
     * @param availableGames
     * @return
     */
    private List<DungeonGameWrapper> determineGames(List<DungeonGameWrapper> availableGames) {
        SecureRandom random = new SecureRandom();
        List<DungeonGameWrapper> chosenGames = new ArrayList<>();

        for (int i = 0; i < 8; i++) { // TODO: make game size configurable
            if (availableGames.size() == 0) break; // Only for test purposes

            int index = random.nextInt(availableGames.size());
            DungeonGameWrapper game = availableGames.get(index);
            chosenGames.add(game);
            availableGames.remove(index);
        }
        return chosenGames;
    }


    private void buildEnd(Location to) {
        DungeonFightersPlugin.game.getDungeon().getGamePositions().put(Util.getChunkCoordinateString(to.getChunk()), this.endMechanic);
        this.placeModule(this.end, to);
    }

    private List<Location> getWallLocs(Location end) {
        Location min = end.add(0, 1, 0);
        Location max = min.clone().add(3, 3, 0);
        return Util.getDoorLocations(min, max);
    }
}