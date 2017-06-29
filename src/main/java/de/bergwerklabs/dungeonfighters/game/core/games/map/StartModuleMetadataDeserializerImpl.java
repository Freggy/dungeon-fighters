package de.bergwerklabs.dungeonfighters.game.core.games.map;

import com.flowpowered.nbt.CompoundTag;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.framework.schematicservice.NbtUtil;
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
        Vector end = new Vector(Float.valueOf(NbtUtil.readTag("EndDistanceX", compoundTag).getValue().toString()),
                                Float.valueOf(NbtUtil.readTag("EndDistanceY", compoundTag).getValue().toString()),
                                Float.valueOf(NbtUtil.readTag("EndDistanceZ", compoundTag).getValue().toString()));

        Vector spawn = new Vector(Float.valueOf(NbtUtil.readTag("SpawnX", compoundTag).getValue().toString()),
                                  Float.valueOf(NbtUtil.readTag("SpawnY", compoundTag).getValue().toString()),
                                  Float.valueOf(NbtUtil.readTag("SpawnZ", compoundTag).getValue().toString()));

        return new StartModuleMetadata(end, spawn);
    }
}
