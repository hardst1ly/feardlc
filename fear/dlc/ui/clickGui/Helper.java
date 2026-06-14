/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui;

import fear.dlc.FearDCP;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.BindSetting;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.Collection;
import fear.dlc.modules.settings.impl.ColorSetting;
import fear.dlc.modules.settings.impl.ModeListSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.modules.settings.impl.TextSetting;
import fear.dlc.ui.clickGui.components.module.ModuleLayerComponent;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import fear.dlc.ui.clickGui.components.settings.bindSetting.BindSettingComponent;
import fear.dlc.ui.clickGui.components.settings.booleanSetting.BooleanSettingComponent;
import fear.dlc.ui.clickGui.components.settings.collection.CollectionComponent;
import fear.dlc.ui.clickGui.components.settings.colorSetting.ColorSettingComponent;
import fear.dlc.ui.clickGui.components.settings.modeListSetting.ModeListSettingComponent;
import fear.dlc.ui.clickGui.components.settings.modeSetting.ModeSettingComponent;
import fear.dlc.ui.clickGui.components.settings.sliderSetting.SliderSettingComponent;
import fear.dlc.ui.clickGui.components.settings.textSetting.TextSettingComponent;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class Helper {
    public static List<ModuleLayerComponent> moduleLayers(Category category, Predicate<ModuleLayer> predicate) {
        return FearDCP.getInstance().getModuleRepository().getModuleLayers().stream().filter(Helper::lambda$moduleLayers$0 /* captured: category */).filter(predicate).map(ModuleLayerComponent::new).toList();
    }

    public static List<SettingComponent> settingComponents(ModuleLayer moduleLayer) {
        return moduleLayer.getSettingLayers().stream().map(Helper::find).filter(Objects::nonNull).toList();
    }

    public static float moduleHeight(List<SettingComponent> settingComponents) {
        return 20f + ((Float)settingComponents.stream().filter(Helper::lambda$moduleHeight$1).map(Helper::lambda$moduleHeight$2).reduce(0f, Float::sum)).floatValue();
    }

    public static SettingComponent find(SettingLayer settingLayer) {
        if (settingLayer instanceof BooleanSetting) {
            return new BooleanSettingComponent(settingLayer);
        }
        if (settingLayer instanceof Collection) {
            Collection collection = settingLayer;
            return new CollectionComponent(collection);
        }
        if (settingLayer instanceof SliderSetting) {
            SliderSetting sliderSetting = settingLayer;
            return new SliderSettingComponent(sliderSetting);
        }
        if (settingLayer instanceof ColorSetting) {
            ColorSetting colorSetting = settingLayer;
            return new ColorSettingComponent(colorSetting);
        }
        if (settingLayer instanceof BindSetting) {
            BindSetting bindSetting = settingLayer;
            return new BindSettingComponent(bindSetting);
        }
        if (settingLayer instanceof ModeSetting) {
            ModeSetting modeSetting = settingLayer;
            return new ModeSettingComponent(modeSetting);
        }
        if (settingLayer instanceof ModeListSetting) {
            ModeListSetting modeListSetting = settingLayer;
            return new ModeListSettingComponent(modeListSetting);
        }
        if (settingLayer instanceof TextSetting) {
            TextSetting textSetting = settingLayer;
            return new TextSettingComponent(textSetting);
        }
        return null;
    }
}
