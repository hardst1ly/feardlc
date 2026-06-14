/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.configs.layers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.BindSetting;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ColorSetting;
import fear.dlc.modules.settings.impl.ModeListSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.modules.settings.impl.TextSetting;
import java.util.List;
import java.util.Objects;

public class SettingsObjectLayer {
    public static JsonElement asElement(List<SettingLayer> settings) {
        JsonArray settingsArray = new JsonArray();
        settings.forEach(SettingsObjectLayer::lambda$asElement$0 /* captured: settingsArray */);
        return settingsArray;
    }

    public static void parseSetting(ModuleLayer moduleLayer, JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();
        SettingLayer settingLayer = moduleLayer.getSettingLayers().stream().filter(SettingsObjectLayer::lambda$parseSetting$1 /* captured: jsonObject */).findFirst().orElse(null);
        if (Objects.isNull(settingLayer)) {
            return;
        }
        Objects.requireNonNull(settingLayer);
        var var4 = settingLayer;
        int var5 = 0;
        switch (/* lambda: typeSwitch */ var4, var5) {
            case 0:
                BooleanSetting booleanSetting = var4;
                booleanSetting.set(jsonObject.get("Boolean-Enabled").getAsBoolean());
            case 1:
                SliderSetting sliderSetting = var4;
                sliderSetting.set(jsonObject.get("Slider-Value").getAsFloat());
            case 2:
                BindSetting bindSetting = var4;
                bindSetting.setSelected(jsonObject.get("Bind-Setting-Selected").getAsBoolean());
                bindSetting.set(jsonObject.get("Bind-Setting-Value").getAsInt());
            case 3:
                ModeSetting modeSetting = var4;
                modeSetting.set(Objects.equals(jsonObject.get("Mode-Setting-Value").getAsString(), "N/A") ? null : jsonObject.get("Mode-Setting-Value").getAsString());
            case 4:
                ModeListSetting modeListSetting = var4;
                JsonArray jsonArray = jsonObject.getAsJsonArray("Mode-List-Setting-Selected");
                modeListSetting.getValues().forEach(SettingsObjectLayer::lambda$parseSetting$2);
                jsonArray.asList().forEach(SettingsObjectLayer::lambda$parseSetting$3 /* captured: modeListSetting */);
            case 5:
                ColorSetting colorSetting = var4;
                float hue = jsonObject.get("Color-Hue").getAsFloat();
                float saturation = jsonObject.get("Color-Saturation").getAsFloat();
                float brightness = jsonObject.get("Color-Brightness").getAsFloat();
                float alpha = jsonObject.get("Color-Alpha").getAsFloat();
                colorSetting.set(hue, saturation, brightness, alpha);
            case 6:
                TextSetting textSetting = var4;
                if (jsonObject.has("Text-Value")) {
                    textSetting.set(jsonObject.get("Text-Value").getAsString());
                }
            default:
                return;
        }
    }
}
