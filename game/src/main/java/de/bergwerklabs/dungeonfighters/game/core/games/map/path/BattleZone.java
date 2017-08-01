package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import de.bergwerklabs.framework.schematicservice.SchematicService;

import java.io.File;
import java.util.*;

/**
 * Created by Yannic Rieger on 04.07.2017.
 * <p> Wraps a battle zone.
 *
 * @author Yannic Rieger
 */
public class BattleZone {

    /**
     * Contains all available parts.
     */
    public enum Part { START, MIDDLE, END }

    /**
     * Gets the start module of this {@code BattleZone}.
     */
    public LabsSchematic<ModuleMetadata> getStart() {
        return start;
    }

    /**
     * Gets the middle module.
     */
    public LabsSchematic<ModuleMetadata> getMiddle() {
        return middle;
    }

    /**
     * Gets the end module.
     */
    public LabsSchematic<ModuleMetadata> getEnd() {
        return end;
    }

    private LabsSchematic<ModuleMetadata> start, middle, end;

    /**
     * @param folder Folder which contains subfolders that contains the modules.
     */
    public BattleZone(File folder) {
        SchematicService<ModuleMetadata> service = ModuleMetadata.getService();
        List<File> files = Arrays.asList(folder.listFiles());
        this.start = this.getIfPresent(files.stream().filter(file -> file.getName().contains("start")).findAny(), service);
        this.middle = this.getIfPresent(files.stream().filter(file -> file.getName().contains("middle")).findAny(), service);
        this.end = this.getIfPresent(files.stream().filter(file -> file.getName().contains("end")).findAny(), service);
    }

    /**
     * Gets the module if present.
     *
     * @param optional {@link Optional<File>}
     * @return {@link LabsSchematic}, null if not present
     */
    private LabsSchematic<ModuleMetadata> getIfPresent(Optional<File> optional, SchematicService<ModuleMetadata> service) {
        if (optional.isPresent()) {
            return service.createSchematic(optional.get());
        }
        else return null;
    }

    /**
     * Randomly picks {@link BattleZone}s that will be generated.
     *
     * @param count Number of {@link BattleZone}s in the path.
     * @param battleZones Folder containing a list of all available {@link BattleZone}s of a specific theme.
     * @param random Used for randomness.
     * @return a List of {@link BattleZone}s that will be generated in the path.
     */
    public static List<BattleZone> determineBattleZones(int count, File battleZones, Random random) {
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

    /**
     * Gets the {@link BattleZone.Part} based on the current position.
     *
     * @param currentPosition Current position in the path.
     * @param endIndex Index where the end module will be placed.
     * @return the fitting {@link BattleZone.Part}
     */
    public static BattleZone.Part getPartByPosition(int currentPosition, int endIndex) {
        if (currentPosition == 0) return BattleZone.Part.START;
        else if (currentPosition == endIndex) return BattleZone.Part.END;
        else return BattleZone.Part.MIDDLE;
    }
}
