/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fear.dlc.FearDCP;
import fear.dlc.api.configs.Configuration;
import fear.dlc.api.configs.layers.DraggableObjectLayer;
import fear.dlc.api.configs.layers.ModuleObjectLayer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.List;
import java.util.Objects;

public class ConfigurationController
implements Configuration {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void save(String name) {
        File file = new File(mc.field_1697.getAbsolutePath() + "/rivalvisual/configs/" + name);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        JsonArray mainArray = new JsonArray();
        JsonObject descriptionObject = new JsonObject();
        descriptionObject.addProperty("Author", "Horrik FunPay - https://funpay.com/users/10237953/");
        mainArray.add(descriptionObject);
        Objects.requireNonNull(mainArray);
        FearDCP.getInstance().getModuleRepository().getModuleLayers().parallelStream().map(ModuleObjectLayer::asElement).forEach(mainArray::add);
        JsonObject draggableContainer = new JsonObject();
        draggableContainer.addProperty("Type", "Draggables");
        draggableContainer.add("Data", DraggableObjectLayer.asElement(FearDCP.getInstance().getDraggableRepository().getList()));
        mainArray.add(draggableContainer);
        Writer writer = new FileWriter(file);
        gson.toJson(mainArray, writer);
        writer.close();
    }

    public void load(String name) {
        File file = new File(mc.field_1697.getAbsolutePath() + "/rivalvisual/configs/" + name);
        if (!file.exists()) {
            System.err.println("\u0424\u0430\u0439\u043b \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u0438 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d: " + name);
            throw new RuntimeException("\u0424\u0430\u0439\u043b \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u0438 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d: " + name);
        }
        Reader reader = new FileReader(file);
        JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
        if (!jsonElement.isJsonArray()) {
            reader.close();
            return;
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        ModuleObjectLayer.parseJson(gson, new StringReader(gson.toJson(jsonArray)));
        jsonArray.asList().forEach(ConfigurationController::lambda$load$0);
        reader.close();
    }

    public void remove(String name) {
        File file = new File(mc.field_1697.getAbsolutePath() + "/rivalvisual/configs/" + name);
        if (file.exists()) {
            file.deleteOnExit();
        }
    }

    public List<String> asList() {
        return List.of();
    }
}
