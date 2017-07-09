package de.bergwerklabs.dungeonfighters.game.core.games.map.metadata;

import com.flowpowered.nbt.CompoundTag;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadataDeserializerImpl;
import de.bergwerklabs.framework.schematicservice.NbtUtil;
import de.bergwerklabs.framework.schematicservice.metadata.MetadataDeserializer;

/**
 * Created by Yannic Rieger on 29.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class StartModuleMetadataDeserializerImpl implements MetadataDeserializer<StartModuleMetadata> {

    @Override
    public StartModuleMetadata deserialize(CompoundTag compoundTag) {
        ModuleMetadata moduleMetadata = new ModuleMetadataDeserializerImpl().deserialize(compoundTag);
        return new StartModuleMetadata(moduleMetadata.getEnd(), moduleMetadata.getLength(),
                                       NbtUtil.vectorFromNbt((CompoundTag)compoundTag.getValue().get("spawn")));
    }
}