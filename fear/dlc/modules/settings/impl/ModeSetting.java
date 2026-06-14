/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.settings.impl;

import fear.dlc.api.animations.Animation;
import fear.dlc.api.animations.Direction;
import fear.dlc.api.animations.implement.DecelerateAnimation;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.class_2561;

public class ModeSetting
extends SettingLayer {
    private final List<String> values = new ArrayList();
    private Boolean opened = false;
    private String value = null;
    private final Animation openAnimation = new DecelerateAnimation().setMs(250).setValue(1);

    public ModeSetting(class_2561 name, class_2561 description, Supplier<Boolean> visible) {
        super(name, description, visible);
        this.openAnimation.setDirection(this.opened.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        this.getAnimation().setMs(400);
    }

    public ModeSetting setOpened(Boolean opened) {
        this.opened = opened;
        this.openAnimation.setDirection(this.opened.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        this.openAnimation.reset();
        return this;
    }

    public ModeSetting set(String ... strings) {
        this.values.addAll(Arrays.asList(strings));
        return this;
    }

    public ModeSetting set(String value) {
        if (this.value != null && this.value.equalsIgnoreCase(value)) {
            return this;
        }
        this.value = value;
        this.getAnimation().reset();
        return this;
    }

    public ModeSetting register(ModuleLayer provider) {
        super.reg(provider);
        return this;
    }

    public ModeSetting collection(Collection collection) {
        collection.put(this);
        return this;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof String)|| this.value == null) {
            return false;
        }
        return this.value.equalsIgnoreCase((String)obj);
    }

    public List<String> getValues() {
        return this.values;
    }

    public Boolean getOpened() {
        return this.opened;
    }

    public String getValue() {
        return this.value;
    }

    public Animation getOpenAnimation() {
        return this.openAnimation;
    }
}
