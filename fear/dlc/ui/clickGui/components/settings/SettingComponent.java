/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings;

import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.ui.clickGui.Component;

public abstract class SettingComponent
extends Component {
    private final SettingLayer settingLayer;

    public SettingLayer getSettingLayer() {
        return this.settingLayer;
    }

    public SettingComponent(SettingLayer settingLayer) {
        this.settingLayer = settingLayer;
    }
}
