/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.modeSetting;

import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.ui.clickGui.components.settings.modeSetting.ModeSettingValueComponent;
import java.util.List;

public final class ModeSettingHelper {
    public static List<ModeSettingValueComponent> values(ModeSetting modeSetting) {
        return modeSetting.getValues().stream().map(ModeSettingHelper::lambda$values$0 /* captured: modeSetting */).toList();
    }
}
