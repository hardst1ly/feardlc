/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.configs.layers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fear.dlc.api.draggable.DraggableLayer;
import java.util.List;

public class DraggableObjectLayer {
    public static JsonElement asElement(List<DraggableLayer> draggables) {
        JsonArray draggableArray = new JsonArray();
        for (DraggableLayer draggable : draggables) {
            JsonObject draggableObject = new JsonObject();
            draggableObject.addProperty("Class-Name", draggable.getClass().getSimpleName());
            draggableObject.addProperty("X", draggable.getX());
            draggableObject.addProperty("Y", draggable.getY());
            draggableObject.addProperty("Width", draggable.getWidth());
            draggableObject.addProperty("Height", draggable.getHeight());
            draggableArray.add(draggableObject);
        }
        return draggableArray;
    }

    public static void parseJson(Gson gson, JsonElement jsonElement) {
        if (!jsonElement.isJsonArray()) {
            return;
        }
        jsonElement.getAsJsonArray().asList().forEach(DraggableObjectLayer::lambda$parseJson$2);
    }
}
