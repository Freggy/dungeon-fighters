package de.bergwerklabs.dungeonfighters.api.module;

import de.bergwerklabs.framework.schematicservice.SchematicService;
import de.bergwerklabs.framework.schematicservice.SchematicServiceBuilder;
import org.bukkit.util.Vector;

/**
 * Created by Yannic Rieger on 29.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ModuleMetadata {

    public static SchematicService<ModuleMetadata> getService() {return service; }

    /**
     * Gets the end point of this module.
     */
    public Vector getEnd() {
        return this.end;
    }

    /**
     * Gets the length of this module in chunks.
     */
    public short getLength() {
        return length;
    }

    public String getType() { return type; }

    private static SchematicService<ModuleMetadata> service = new SchematicServiceBuilder<ModuleMetadata>()
                                                              .setDeserializer(new ModuleMetadataDeserializerImpl()).build();
    private Vector end;
    private short length;
    private String type;


    /**
     * @param end
     * @param length
     */
    public ModuleMetadata(Vector end, short length) {
        this.end = end;
        this.length = length;
    }
}
