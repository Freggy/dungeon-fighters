package de.bergwerklabs.dungeonfighters.api.module;

import com.flowpowered.nbt.CompoundTag;
import de.bergwerklabs.framework.schematicservice.NbtUtil;
import de.bergwerklabs.framework.schematicservice.metadata.MetadataDeserializer;

/**
 * Created by Yannic Rieger on 05.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ModuleMetadataDeserializerImpl implements MetadataDeserializer<ModuleMetadata> {

    @Override
    public ModuleMetadata deserialize(CompoundTag compoundTag) {
       return new ModuleMetadata(NbtUtil.vectorFromNbt((CompoundTag)compoundTag.getValue().get("end")),
                                 ((Integer)compoundTag.getValue().get("length").getValue()).shortValue());
    }
}
