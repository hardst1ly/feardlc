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

public class BindSetting
extends SettingLayer {
    Integer key = -1;
    Boolean selected = false;

    public BindSetting(class_2561 name, class_2561 description, Supplier<Boolean> visible) {
        super(name, description, visible);
        this.getAnimation().setDirection(this.selected.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
    }

    public BindSetting set(Integer keyCode) {
        this.key = keyCode;
        this.setSelected(false);
        this.getAnimation().setDirection(this.selected.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        return this;
    }

    public BindSetting setSelected(boolean selected) {
        this.selected = selected;
        this.getAnimation().setDirection(this.selected.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        this.getAnimation().reset();
        return this;
    }

    public BindSetting register(ModuleLayer provider) {
        super.reg(provider);
        return this;
    }

    public BindSetting collection(Collection collection) {
        collection.put(this);
        return this;
    }

    public Integer getKey() {
        return this.key;
    }

    public Boolean getSelected() {
        return this.selected;
    }
}
