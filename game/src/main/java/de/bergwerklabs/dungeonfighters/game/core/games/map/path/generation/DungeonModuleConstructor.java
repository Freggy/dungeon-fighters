package de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation;

import com.google.common.collect.Iterables;
import com.sk89q.worldedit.regions.CuboidRegion;
import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.commons.ListUtil;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameWrapper;
import de.bergwerklabs.dungeonfighters.game.core.games.map.metadata.StartModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BattleZone;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BuildResult;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.DungeonPath;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationInfo;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationLine;
import de.bergwerklabs.dungeonfighters.game.core.games.mechanic.BattleZoneMechanic;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Location;

import java.util.*;
import java.util.stream.Collectors;

public class DungeonModuleConstructor {

    /**
     *
     */
    public static List<CuboidRegion> getStartWallBlockLocations() { return startWallBlockLocations; }

    public static Iterator<LabsSchematic<ModuleMetadata>> getBarrierWalls() {
        return barrierWalls;
    }

    private static List<CuboidRegion> startWallBlockLocations = new ArrayList<>();
    private static Iterator<LabsSchematic<ModuleMetadata>> barrierWalls;


    static {
        List<LabsSchematic<ModuleMetadata>> barrierFiles = DungeonFightersPlugin.getInstance().getThemedBarrierWalls(DungeonFightersPlugin.game.getTheme()).stream()
                                                                                .map(ModuleMetadata.getService()::createSchematic)
                                                                                .collect(Collectors.toList());
        barrierWalls = Iterables.cycle(ListUtil.createRandomItemList(barrierFiles, 9)).iterator();
    }

    /**
     * Places the given {@link BattleZone.Part}.
     *
     * @param toPlace {@link Location} where to place the {@link LabsSchematic}.
     * @param zone {@link BattleZone} containing the modules.
     * @param part Part to place.
     * @return {@link Location} where the next module has to be placed.
     */
    public static BuildResult buildBattleZonePart(Location toPlace, BattleZone zone, BattleZone.Part part) {
        switch (part) {
            case START:  return new BuildResult(new BattleZoneMechanic(), toPlace, zone.getStart());
            case MIDDLE: return new BuildResult(new BattleZoneMechanic(), toPlace, zone.getMiddle());
            case END:    return new BuildResult(new BattleZoneMechanic(), toPlace, zone.getEnd());
            default: return null;
        }
    }

    /**
     * Places a {@link DungeonGame}.
     *
     * @param game Game to place.
     * @param toPlace {@link Location} where to place the {@link DungeonGame}.
     * @param position Value needed for determining the {@link StageTier}.
     * @return {@link Location} where the next module has to be placed.
     */
    public static BuildResult buildGame(DungeonGameWrapper game, Location toPlace, int position) {
        DungeonGame dungeonGame = (DungeonGame) game.getGame().clone();
        // TODO: load module metadata
        dungeonGame.setStageTier(StageTier.getStageTierByPosition(position));
        return new BuildResult(dungeonGame, toPlace, game.getModule());
    }

    /**
     * Places the end module
     *
     * @param to {@link Location} where to place the module.
     */
    public static BuildResult buildEnd(Location to, LabsSchematic<ModuleMetadata> endModule) {
        return new BuildResult(null, to, endModule);
    }

    /**
     * Builds the connection between two modules.
     *
     * @param to The {@link Location} where to build.
     * @return the {@link Location} where the next module will be built.
     */
    public static BuildResult buildConnection(Iterator<LabsSchematic<ModuleMetadata>> connections, Location to) {
        LabsSchematic<ModuleMetadata> module = connections.next();
        return new BuildResult(null, to, module);
    }

    /**
     * Places a {@link LabsSchematic} to the specified {@link Location}.
     *
     * @param schematic Module that will be placed.
     * @param to {@link Location} where the module will be placed.
     * @param <T> Object that extends {@link ModuleMetadata}.
     * @return {@link Location} where the next module has to be placed.
     */
    public static <T extends ModuleMetadata> void placeModule(LabsSchematic<T> schematic, Location to) {
        schematic.pasteAsync(DungeonFightersPlugin.moduleWorld.getName(), to.toVector());
        to.getChunk().load();
    }

    /**
     *
     * @param schematic
     * @param to
     * @param <T>
     * @return
     */
    public static <T extends ModuleMetadata> Location getNextBuildLocation(LabsSchematic<T> schematic, Location to) {
        if (schematic.hasMetadata()) {
            Location endLocation = to.clone().subtract(schematic.getMetadata().getEnd());
            return endLocation.add(0, 0, 1);
        }
        else return null;
    }

    public static ActivationLine createActivationLine(BuildResult gameResult, BuildResult connectionResult, Location end) {
        ActivationInfo info = new ActivationInfo(gameResult.getProvider(), connectionResult, gameResult);
        return new ActivationLine(info, end.getBlockZ(), new HashSet<>(Arrays.asList(end.getBlockX() + 1, end.getBlockX() + 2, end.getBlockX() + 3, end.getBlockX() + 4)));
    }


    public static List<Location> placeSpawns(DungeonPath path, LabsSchematic<StartModuleMetadata> module, Iterator<LabsSchematic<ModuleMetadata>> connections, Location start) {
        List<Location> buildLocations = new ArrayList<>();

        // TODO: refactor
        for (int players = 0; players < 12; players++) {
            DungeonModuleConstructor.placeModule(module, start);
            Location connLoc = DungeonModuleConstructor.getNextBuildLocation(module, start);
            startWallBlockLocations.add(Util.getDoorLocations(connLoc.clone().add(-3, 1, 0), connLoc.clone().add(0, 4, 0)));
            barrierWalls.next().pasteAsync(start.getWorld().getName(), connLoc.clone().add(-3, 1, 0).toVector());

            Location spawn = start.clone().subtract(module.getMetadata().getSpawn().clone()).add(0, 1, 0.9);
            spawn.setPitch(0);
            spawn.setYaw(0);
            path.getSpawns().add(spawn);

            Location nextBuildLoc = placeConnection(connections, connLoc.clone().subtract(3, 0, 0));

            // TODO: place wall at start from connection.
            buildLocations.add(nextBuildLoc);
            start.add(46, 0, 0);
        }
        return buildLocations;
    }

    /**
     *
     * @param toPlace
     * @param zone
     * @param currentPos
     * @param lasPos
     * @return
     */
    public static Location placeBattleZonePart(Location toPlace, BattleZone zone, int currentPos, int lasPos) {
        BuildResult result = DungeonModuleConstructor.buildBattleZonePart(toPlace, zone, BattleZone.getPartByPosition(currentPos, lasPos));
        LabsSchematic module = result.getModule();
        DungeonModuleConstructor.placeModule(module, toPlace);
        return DungeonModuleConstructor.getNextBuildLocation(module, toPlace);
    }

    /**
     *
     * @param connections
     * @param toPlace
     * @return
     */
    public static Location placeConnection(Iterator<LabsSchematic<ModuleMetadata>> connections, Location toPlace) {
        BuildResult connResult = DungeonModuleConstructor.buildConnection(connections, toPlace);
        DungeonModuleConstructor.placeModule(connResult.getModule(), connResult.getBuildLocation());
        return DungeonModuleConstructor.getNextBuildLocation(connResult.getModule(), connResult.getBuildLocation());
    }
}
