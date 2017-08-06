package de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameWrapper;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BattleZone;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BuildResult;
import de.bergwerklabs.dungeonfighters.game.core.games.mechanic.BattleZoneMechanic;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Location;
import java.util.Iterator;

public class DungeonModuleConstructor {

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
        else
            return null;
    }
}
