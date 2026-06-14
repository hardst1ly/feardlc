/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.settings;

import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.Collection;

public interface SettingLayerBuilder {
    public SettingLayerBuilder register(ModuleLayer var1);

    public SettingLayerBuilder collection(Collection var1);
}
