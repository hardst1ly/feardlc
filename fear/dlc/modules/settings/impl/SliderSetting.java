/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.settings.impl;

import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.Collection;
import java.util.function.Supplier;
import net.minecraft.class_2561;

public class SliderSetting
extends SettingLayer {
    private Float value;
    private Float min;
    private Float max;
    private Float increments;
    private Boolean dragging = false;

    public SliderSetting(class_2561 name, class_2561 description, Supplier<Boolean> visible) {
        super(name, description, visible);
    }

    public SliderSetting set(float min, float max, float increments) {
        this.min = min;
        this.max = max;
        this.increments = increments;
        this.set(max / 2f);
        return this;
    }

    public SliderSetting set(float value) {
        this.value = value;
        return this;
    }

    public SliderSetting register(ModuleLayer provider) {
        super.reg(provider);
        return this;
    }

    public SliderSetting collection(Collection collection) {
        collection.put(this);
        return this;
    }

    public Float getValue() {
        return this.value;
    }

    public Float getMin() {
        return this.min;
    }

    public Float getMax() {
        return this.max;
    }

    public Float getIncrements() {
        return this.increments;
    }

    public Boolean getDragging() {
        return this.dragging;
    }

    public void setDragging(Boolean dragging) {
        this.dragging = dragging;
    }
}
