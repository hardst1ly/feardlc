/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.settings.impl;

import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.class_2561;

public class Collection
extends SettingLayer {
    private final List<SettingLayer> settingLayers = new ArrayList();

    public Collection(class_2561 name, class_2561 description, Supplier<Boolean> visible) {
        super(name, description, visible);
    }

    public Collection put(SettingLayer settingLayer) {
        this.settingLayers.add(settingLayer);
        return this;
    }

    public Collection register(ModuleLayer provider) {
        super.reg(provider);
        return this;
    }

    public SettingLayer collection(Collection collection) {
        collection.put(this);
        return this;
    }

    public List<SettingLayer> getSettingLayers() {
        return this.settingLayers;
    }
}
