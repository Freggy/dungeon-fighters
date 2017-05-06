package de.bergwerklabs.dungeonfighters.game.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Yannic Rieger on 06.05.2017.
 * <p> Class providing the capability to deserialize a config for DungeonFighters </p>
 * @author Yannic Rieger
 */
public class ConfigDeserializer implements JsonDeserializer<DungeonFighterConfig> {

    @Override
    public DungeonFighterConfig deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
}
