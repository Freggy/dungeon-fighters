package de.bergwerklabs.dungeonfighters.util.animation;

import com.google.gson.*;
import de.bergwerklabs.framework.commons.spigot.json.JsonUtil;
import de.bergwerklabs.framework.commons.spigot.title.Title;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yannic Rieger on 20.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class TitleAnimationDeserializer implements JsonDeserializer<TitleAnimation> {

    @Override
    public TitleAnimation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int rate = jsonObject.get("rate").getAsInt();

        List<Title> frames = JsonUtil.jsonArrayToJsonObjectList(jsonObject.get("frames").getAsJsonArray())
                                     .stream().map(Title::fromJson).collect(Collectors.toList());

        return new TitleAnimation(rate, frames);
    }
}
