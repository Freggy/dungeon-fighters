package de.bergwerklabs.dungeonfighters.game.core.games.map;

import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import de.bergwerklabs.framework.schematicservice.SchematicService;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
}
