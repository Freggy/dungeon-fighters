package de.bergwerklabs.dungeonfighters.game.config;

import com.google.gson.*;
import de.bergwerklabs.framework.core.location.LocationUtil;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Yannic Rieger on 06.05.2017.
 * <p> Class providing the capability to deserialize a config for DungeonFighters </p>
 * @author Yannic Rieger
 */
public class ConfigDeserializer implements JsonDeserializer<DungeonFighterConfig> {

    @Override
    public DungeonFighterConfig deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        JsonObject messageObject = json.get("messages").getAsJsonObject();
        JsonObject emeraldObject = json.get("emerald-settings").getAsJsonObject();

        HashMap<String, Object> messages = new HashMap<>();
        messages.put("death-message", messageObject.get("death-message").getAsString());
        messages.put("join-messages",  this.getJoinMessages(messageObject.get("join-messages").getAsJsonArray()));
        messages.put("not-enough-money-message", messageObject.get("not-enough-money-message").getAsString());
        messages.put("cannot-enchant-message", messageObject.get("cannot-enchant-message").getAsString());
        messages.put("cannot-convert-xp-message", messageObject.get("cannot-convert-xp-message").getAsString());


        HashMap<String, Integer> emeraldSettings = new HashMap<>();
        emeraldSettings.put("max-emerald-drop", emeraldObject.get("max-emerald-drop").getAsInt());
        emeraldSettings.put("min-emerald-drop", emeraldObject.get("min-emerald-drop").getAsInt());

        Location gridOrigin = LocationUtil.locationFromJson(json.get("grid-origin").getAsJsonObject());

        return new DungeonFighterConfig(messages, emeraldSettings, gridOrigin);
    }

    /**
     * Creates a string array out of a JsonArray.
     * @param messages Array containing messages as string.
     * @return String array
     */
    private String[] getJoinMessages(JsonArray messages) {
        String[] join = new String[messages.size()];

        for (int i = 0; i < messages.size(); i++)
            join[i] = messages.get(i).getAsString();

        return join;
    }
}
