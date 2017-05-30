package de.bergwerklabs.dungeonfighters.game.map;

import de.bergwerklabs.framework.schematicservice.LabsSchematic;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yannic Rieger on 30.05.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class Dungeon {

    /**
     *
     */
    public HashMap<String, List<LabsSchematic>> getModules() { return this.modules; }

    private HashMap<String, List<LabsSchematic>> modules = new HashMap<>();

    /**
     *
     * @param mapFolder
     */
    public Dungeon(File mapFolder) {
        List<File> modules = Arrays.asList(mapFolder.listFiles());
        this.modules.put("HORIZONTAL_PASSAGE", this.getSchematicList(modules, "HORIZONTAL_PASSAGE"));
        this.modules.put("HORIZONTAL_PASSAGE", this.getSchematicList(modules, "HORIZONTAL_PASSAGE"));
        this.modules.put("HORIZONTAL_PASSAGE", this.getSchematicList(modules, "HORIZONTAL_PASSAGE"));
        this.modules.put("HORIZONTAL_PASSAGE", this.getSchematicList(modules, "HORIZONTAL_PASSAGE"));
        this.modules.put("HORIZONTAL_PASSAGE", this.getSchematicList(modules, "HORIZONTAL_PASSAGE"));
        this.modules.put("HORIZONTAL_PASSAGE", this.getSchematicList(modules, "HORIZONTAL_PASSAGE"));
    }

    /**
     *
     * @param modules
     * @param moduleType
     * @return
     */
    private List<LabsSchematic> getSchematicList(List<File> modules, String moduleType) {
        return modules.stream().filter(file -> file.getName().endsWith(moduleType))
                      .map(file -> new LabsSchematic(file))
                      .collect(Collectors.toList());
    }

}
