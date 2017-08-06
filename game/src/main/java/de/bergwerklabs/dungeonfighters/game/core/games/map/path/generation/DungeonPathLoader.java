package de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation;

import com.google.common.collect.Iterables;
import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.commons.ListUtil;
import de.bergwerklabs.dungeonfighters.game.core.Dungeon;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameWrapper;
import de.bergwerklabs.dungeonfighters.game.core.games.map.metadata.StartModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.games.map.metadata.StartModuleMetadataDeserializerImpl;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.BattleZone;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationLine;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import de.bergwerklabs.framework.schematicservice.SchematicService;
import de.bergwerklabs.framework.schematicservice.SchematicServiceBuilder;
import org.bukkit.Location;

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
    private LabsSchematic<ModuleMetadata> end;
    private String theme;
    private Iterator<LabsSchematic<ModuleMetadata>> connections;

    /**
     * Builds the path that every player has to go through.
     *
     * @param dungeon {@link Dungeon} that contains the important map data.
     * @param theme Theme of the modules. For more info have a look in confluence.
     */
    public void buildDungeons(Dungeon dungeon, String theme) {
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
        return this.buildPath(startPoint, availableGames, battleZones);
    }

    /**
     * Builds a whole path for a single player by placing all the required modules.
     *
     * @param availableGames {@link Iterator<DungeonGameWrapper>} containing all the {@link DungeonGame}sa available.
     */
    private void buildPath(LabsSchematic<StartModuleMetadata> startModule, Iterator<DungeonGameWrapper> availableGames, Iterator<BattleZone> battleZones) {
        List<Location> buildLocations = this.buildSpawns(this.start.getChunk().getBlock(0, 69, 0).getLocation(), startModule);
        // TODO: build and place start points

        for (int pathPosition = 0; pathPosition < buildLocations.size(); pathPosition++) {
            Location start = buildLocations.get(pathPosition);
            for (int position = 1; position < 13; position++) {
                if (position % 4 == 0 && position != 1 && position != 12) {
                    // TODO: build and place battle zone
                }
                else if (position == 12) { // end has been reached
                    // TODO: build end
                }
                else {
                    // TOOD: build game
                }
            }
        }
        return this.path;
    }

    /**
     * Determines a {@link LabsSchematic} randomly from a list.
     *
     * @param schematics {@link List<LabsSchematic>} containing some {@link LabsSchematic}s.
     * @param random Class extending {@link Random}.
     * @return a randomly chosen {@link LabsSchematic}.
     */
    private  <T extends ModuleMetadata> LabsSchematic<T> determineSchematic(List<File> schematics, SchematicService<T> service, Random random) {
        return service.createSchematic(schematics.get(random.nextInt(schematics.size())));
    }


    /**
     * Determines a {@link List} of {@link DungeonGame}s randomly from a list that contains every available.
     *
     * @param availableGames {@link List} that contains every available {@link DungeonGame}.
     * @return {@link List} of randomly chosen {@link DungeonGame}s.
     */
    private static List<DungeonGameWrapper> determineGames(List<DungeonGameWrapper> availableGames) {
        return ListUtil.createRandomItemList(availableGames, 9);
    }

    /**
     * Determines a {@link List} of connections randomly.
     *
     * @param connections {@link List} containing all available connections.
     * @return a new {@link List} with no duplicate connections.
     */
    private static List<LabsSchematic<ModuleMetadata>> determineConnections(List<File> connections) {
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
        //
    }
}