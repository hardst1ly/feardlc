/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.settings.impl;

import fear.dlc.api.animations.Direction;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.Collection;
import java.util.function.Supplier;
import net.minecraft.class_2561;

public class BooleanSetting
extends SettingLayer {
    private Boolean enabled = false;

    public BooleanSetting(class_2561 name, class_2561 description, Supplier<Boolean> visible) {
        super(name, description, visible);
        this.getAnimation().setDirection(this.enabled.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
    }

    public BooleanSetting set(Boolean enabled) {
        this.enabled = enabled;
        this.getAnimation().setDirection(this.enabled.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        this.getAnimation().reset();
        return this;
    }

    public BooleanSetting register(ModuleLayer provider) {
        super.reg(provider);
        return this;
    }

    public BooleanSetting collection(Collection collection) {
        collection.put(this);
        return this;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }
}
