package de.bergwerklabs.dungeonfighters.game.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Yannic Rieger on 22.06.2017.
 * <p>
 *
 * @author Yannic Rieger
 */
public class MapConfigDeserializer implements JsonDeserializer<MapConfig> {

    @Override
    public MapConfig deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
