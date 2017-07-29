package de.bergwerklabs.dungeonfighters.api.game.config;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Yannic Rieger on 29.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class BaseConfigDeserializer implements JsonDeserializer<BaseDungeonGameConfig> {


    @Override
    public BaseDungeonGameConfig deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();
        int duration = json.get("duration").getAsInt();
        int warningTime = json.get("warning-time").getAsInt();
        String timerString = json.get("timer-string").getAsString();

        return new BaseDungeonGameConfig(timerString, duration, warningTime);
    }
}
