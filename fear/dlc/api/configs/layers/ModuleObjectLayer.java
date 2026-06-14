/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.configs.layers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fear.dlc.api.configs.layers.SettingsObjectLayer;
import fear.dlc.modules.more.ModuleLayer;
import java.io.Reader;

public class ModuleObjectLayer {
    public static JsonElement asElement(ModuleLayer module) {
        JsonObject moduleObject = new JsonObject();
        moduleObject.addProperty("Module-Name", module.getModuleName().getString());
        moduleObject.addProperty("Module-Enabled", module.getEnabled());
        moduleObject.addProperty("Module-Key", module.getKey());
        moduleObject.addProperty("Module-Toggle-Action", module.getAction());
        moduleObject.add("Module-Settings", SettingsObjectLayer.asElement(module.getSettingLayers()));
        return moduleObject;
    }

    public static void parseJson(Gson gson, Reader reader) {
        JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
        if (!jsonElement.isJsonArray()) {
            return;
        }
        jsonElement.getAsJsonArray().asList().forEach(ModuleObjectLayer::lambda$parseJson$2);
    }
}
