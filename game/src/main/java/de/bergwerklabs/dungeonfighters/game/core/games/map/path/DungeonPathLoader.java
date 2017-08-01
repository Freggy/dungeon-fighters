package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import com.google.common.collect.Iterables;
import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.commons.ListUtil;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.dungeonfighters.game.core.Dungeon;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameWrapper;
import de.bergwerklabs.dungeonfighters.game.core.games.map.metadata.StartModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.games.map.metadata.StartModuleMetadataDeserializerImpl;
import de.bergwerklabs.dungeonfighters.game.core.games.mechanic.BattleZoneMechanic;
import de.bergwerklabs.dungeonfighters.game.core.games.mechanic.EndMechanic;
import de.bergwerklabs.dungeonfighters.game.core.games.mechanic.StartMechanic;
import de.bergwerklabs.framework.commons.spigot.general.Tuple;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import de.bergwerklabs.framework.schematicservice.SchematicService;
import de.bergwerklabs.framework.schematicservice.SchematicServiceBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Yannic Rieger on 26.06.2017.
 * <p> This class is responsible for creating the path that every player has to go through in the beginning.
 *
 * @author Yannic Rieger
 */
public class DungeonPathLoader {

    private Location start = new Location(DungeonFightersPlugin.moduleWorld, 270, 69, 93);
    private BattleZoneMechanic battleZoneMechanic = new BattleZoneMechanic();
    private EndMechanic endMechanic = new EndMechanic();
    private StartMechanic startMechanic = new StartMechanic();
    private LabsSchematic<ModuleMetadata> end;
    private String theme;
    private Iterator<LabsSchematic<ModuleMetadata>> connections;

    /**
     * Builds the path that every player has to go through.
     *
     * @param dungeon {@link Dungeon} that contains the important map data.
     * @param players Value indicating how much players are available.
     * @param theme Theme of the modules. For more info have a look in confluence.
     */
    public Queue<DungeonPath> buildDungeons(Dungeon dungeon, int players, String theme) {
        Random random = new Random();
        SchematicService<StartModuleMetadata> service = new SchematicServiceBuilder<StartModuleMetadata>().setDeserializer(new StartModuleMetadataDeserializerImpl()).build();
        LabsSchematic<StartModuleMetadata> startPoint = this.determineSchematic(dungeon.getStartPoints(), service, random);
        this.theme = theme;
        this.end = this.determineSchematic(dungeon.getEndPoints(), ModuleMetadata.getService(), random);
        this.connections = Iterables.cycle(this.determineConnections(DungeonFightersPlugin.getInstance().getThemedConnections(this.theme))).iterator();

        // Use cycle iterators to avoid the Iterator#hasNext query.
        Iterator<BattleZone> battleZones = Iterables.cycle(BattleZone.determineBattleZones(2, DungeonFightersPlugin.getInstance().getThemedBattleZoneFolder(this.theme), random))
                                                    .iterator();

        Iterator<DungeonGameWrapper> availableGames = Iterables.cycle(this.determineGames(dungeon.getDungeonGames())).iterator();

        // Use cycle iterators to avoid the Iterator#hasNext query.
        return this.buildPath(players, startPoint, availableGames, battleZones);
    }

    /**
     * Builds a whole path for a single player by placing all the required modules.
     *
     * @param availableGames {@link Iterator<DungeonGameWrapper>} containing all the {@link DungeonGame}sa available.
     */
    private Queue<DungeonPath> buildPath(int players, LabsSchematic<StartModuleMetadata> startModule, Iterator<DungeonGameWrapper> availableGames, Iterator<BattleZone> battleZones) {
        Queue<DungeonPath> paths = new LinkedList<>();
        Location newLocation = this.start.getChunk().getBlock(0, 69, 0).getLocation().clone();

        for (int pathPosition = 0; pathPosition < players; pathPosition++) {

            DungeonPath path = new DungeonPath();
            Location start = this.prepareAndBuildStartPoints(path, newLocation, startModule).getNextBuildLocation();
            path.getGames().add(this.startMechanic);
            ActivationLine nextLine = this.createActivationLine(start.clone().subtract(0, 0, -1));

            newLocation.add(46, 0 ,0);

            for (int position = 1; position < 13; position++) {
                if (position % 4 == 0 && position != 1 && position != 12) {
                    BuildResult result = this.buildBattleZonePart(start, nextLine, battleZones.next(), BattleZone.getPartByPosition(pathPosition, players - 1));
                    Location toPlace = result.getNextBuildLocation().add(new Vector(0, 0, 1));
                    path.getGames().add(result.getProvider());

                    nextLine = this.createActivationLine(start);
                    start = this.buildConnection(toPlace).getNextBuildLocation().add(new Vector(0, 0, 1));
                }
                else if (position == 12) { // end has been reached
                    this.buildEnd(start);
                }
                else {
                   BuildResult result = this.buildGame(availableGames.next(), nextLine, start, position);
                   Location nextLoc = result.getNextBuildLocation().add(new Vector(0, 0, 1));
                   path.getGames().add(result.getProvider());

                   nextLine = this.createActivationLine(start);
                   start = this.buildConnection(nextLoc).getNextBuildLocation().add(new Vector(0, 0, 1));
                }
            }
            paths.add(path);
        }
        return paths;
    }

    /**
     *
     * @param path
     * @param location
     * @param startPoint
     * @return
     */
    private BuildResult prepareAndBuildStartPoints(DungeonPath path, Location location, LabsSchematic<StartModuleMetadata> startPoint) {

        this.buildStartPoints(path, startPoint, location);
        Location end = location.clone().subtract(startPoint.getMetadata().getEnd().clone().add(new Vector(3,0, -1)));

        BuildResult result = this.buildConnection(end);
        return new BuildResult(this.startMechanic, result.getNextBuildLocation().add(0, 0, 1));
    }

    /**
     * Builds a start point and sets the player spawn.
     *
     * @param startPoint {@link LabsSchematic} representing a spawn module.
     */
    private void buildStartPoints(DungeonPath path, LabsSchematic<StartModuleMetadata> startPoint, Location location) {
        Location spawn = location.clone().subtract(startPoint.getMetadata().getSpawn().clone()).add(0, 1, 0.9);
        spawn.setPitch(0);
        spawn.setYaw(0);
        path.setSpawn(spawn);
        startPoint.pasteAsync(this.start.getWorld().getName(), location.toVector());
    }

    /**
     * Places the given {@link BattleZone.Part}.
     *
     * @param toPlace {@link Location} where to place the {@link LabsSchematic}.
     * @param zone {@link BattleZone} containing the modules.
     * @param part Part to place.
     * @return {@link Location} where the next module has to be placed.
     */
    private BuildResult buildBattleZonePart(Location toPlace, ActivationLine next, BattleZone zone, BattleZone.Part part) {
        this.battleZoneMechanic.assignNext(next);

        switch (part) {
            case START:  return new BuildResult(this.battleZoneMechanic,  this.placeModule(zone.getStart(), toPlace));
            case MIDDLE: return new BuildResult(this.battleZoneMechanic, this.placeModule(zone.getMiddle(), toPlace));
            case END:    return new BuildResult(this.battleZoneMechanic, this.placeModule(zone.getEnd(), toPlace));
            default: return null;
        }
    }

    /**
     * Places a {@link DungeonGame}.
     *
     * @param game Game to place.
     * @param start {@link Location} where to place the {@link DungeonGame}.
     * @param position Value needed for determining the {@link StageTier}.
     * @return {@link Location} where the next module has to be placed.
     */
    private BuildResult buildGame(DungeonGameWrapper game, ActivationLine nextLine, Location start, int position) {
        DungeonGame dungeonGame = (DungeonGame) game.getGame().clone();
        // TODO: load module metadata
        dungeonGame.setStageTier(StageTier.getStageTierByPosition(position));
        dungeonGame.assignNext(nextLine);
        return new BuildResult(dungeonGame, this.placeModule(game.getModule(), start));
    }

    /**
     * Places the end module
     *
     * @param to {@link Location} where to place the module.
     */
    private void buildEnd(Location to) {
        DungeonFightersPlugin.game.getDungeon().getGamePositions().put(Util.getChunkCoordinateString(to.getChunk()), this.endMechanic);
        this.placeModule(this.end, to);
    }

    /**
     * Builds the connection between two modules.
     *
     * @param to The {@link Location} where to build.
     * @return the {@link Location} where the next module will be built.
     */
    private BuildResult buildConnection(Location to) {
        LabsSchematic<ModuleMetadata> connection = this.connections.next();
        return new BuildResult(null, this.placeModule(connection, to));
    }

    /**
     * Places a {@link LabsSchematic} to the specified {@link Location}.
     *
     * @param schematic Module that will be placed.
     * @param to {@link Location} where the module will be placed.
     * @param <T> Object that extends {@link ModuleMetadata}.
     * @return {@link Location} where the next module has to be placed.
     */
    private <T extends ModuleMetadata> Location placeModule(LabsSchematic<T> schematic, Location to) {
        schematic.pasteAsync(DungeonFightersPlugin.moduleWorld.getName(), to.toVector());

        if (schematic.hasMetadata()) {
            Location endLocation = to.clone().subtract(schematic.getMetadata().getEnd());
            return endLocation;
        }
        else
            return null;
    }

    /**
     * Determines a {@link LabsSchematic} randomly from a list.
     *
     * @param schematics {@link List<LabsSchematic>} containing some {@link LabsSchematic}s.
     * @param random Class extending {@link Random}.
     * @return a randomly chosen {@link LabsSchematic}.
     */
    private <T extends ModuleMetadata> LabsSchematic<T> determineSchematic(List<File> schematics, SchematicService<T> service, Random random) {
        return service.createSchematic(schematics.get(random.nextInt(schematics.size())));
    }


    /**
     * Determines a {@link List} of {@link DungeonGame}s randomly from a list that contains every available.
     *
     * @param availableGames {@link List} that contains every available {@link DungeonGame}.
     * @return {@link List} of randomly chosen {@link DungeonGame}s.
     */
    private List<DungeonGameWrapper> determineGames(List<DungeonGameWrapper> availableGames) {
        return ListUtil.createRandomItemList(availableGames, 9);
    }

    /**
     * Determines a {@link List} of connections randomly.
     *
     * @param connections {@link List} containing all available connections.
     * @return a new {@link List} with no duplicate connections.
     */
    private List<LabsSchematic<ModuleMetadata>> determineConnections(List<File> connections) {
        return ListUtil.createRandomItemList(connections.stream().map(file -> {
            return ModuleMetadata.getService().createSchematic(file);
        }).collect(Collectors.toList()), 13);
    }

    /**
     *
     * @param end
     * @return
     */
    private ActivationLine createActivationLine(Location end) {
        HashSet<Tuple<Integer, Integer>> activationLine = new HashSet<>();
        activationLine.add(new Tuple<>(end.getBlockX(), end.getBlockZ()));
        activationLine.add(new Tuple<>(end.getBlockX() + 1, end.getBlockZ() + 1));
        activationLine.add(new Tuple<>(end.getBlockX() + 2, end.getBlockZ() + 2));
        return new ActivationLine(activationLine);
    }
}