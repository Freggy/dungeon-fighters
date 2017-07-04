package de.bergwerklabs.dungeonfighters.game.core.games.map;

import com.flowpowered.nbt.CompoundTag;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.framework.schematicservice.metadata.MetadataDeserializer;
import org.bukkit.util.Vector;

/**
 * Created by Yannic Rieger on 29.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
class StartModuleMetadataDeserializerImpl implements MetadataDeserializer<ModuleMetadata> {

    @Override
    public StartModuleMetadata deserialize(CompoundTag compoundTag) {
        return new StartModuleMetadata(this.readVector((CompoundTag)compoundTag.getValue().get("end")),
                                       (Short)compoundTag.getValue().get("length").getValue(),
                                       this.readVector((CompoundTag)compoundTag.getValue().get("spawn")));
    }

    private Vector readVector(CompoundTag tag) {
        return new Vector((Double)tag.getValue().get("x").getValue(),
                          (Double)tag.getValue().get("y").getValue(),
                          (Double)tag.getValue().get("z").getValue());
    }
}