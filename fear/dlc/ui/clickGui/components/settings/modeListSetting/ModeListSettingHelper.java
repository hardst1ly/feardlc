/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.modeListSetting;

import fear.dlc.modules.settings.impl.ModeListSetting;
import fear.dlc.ui.clickGui.components.settings.modeListSetting.ModeListSettingValueComponent;
import java.util.List;

public class ModeListSettingHelper {
    public static List<ModeListSettingValueComponent> values(ModeListSetting modeListSetting) {
        return modeListSetting.asStringList().stream().map(ModeListSettingHelper::lambda$values$0 /* captured: modeListSetting */).toList();
    }
}
