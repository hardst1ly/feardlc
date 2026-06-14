/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.settings.impl;

import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.Collection;
import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.class_2561;
import net.minecraft.class_9848;

public class ColorSetting
extends SettingLayer {
    public float hsb;
    public float saturation;
    public float brightness = 0f;
    public float alpha = 1f;

    public ColorSetting(class_2561 name, class_2561 description, Supplier<Boolean> visible) {
        super(name, description, visible);
    }

    public ColorSetting set(Integer color) {
        float[] hsbValue = Color.RGBtoHSB(class_9848.method_61327(color.intValue()), class_9848.method_61329(color.intValue()), class_9848.method_61331(color.intValue()), new float[3]);
        this.hsb = hsbValue[0];
        this.saturation = hsbValue[1];
        this.brightness = hsbValue[2];
        this.alpha = class_9848.method_61320(color.intValue()) / 255f;
        return this;
    }

    public ColorSetting set(float hsb, float saturation, float brightness, float alpha) {
        this.hsb = hsb;
        this.saturation = saturation;
        this.brightness = brightness;
        this.alpha = alpha;
        return this;
    }

    public int getColor() {
        int rgb = Color.HSBtoRGB(this.hsb, this.saturation, this.brightness);
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;
        int a = (this.alpha * 255f);
        return a << 24 | r << 16 | g << 8 | b;
    }

    public int getColorRGB() {
        int rgb = Color.HSBtoRGB(this.hsb, this.saturation, this.brightness);
        return rgb | -16777216;
    }

    public ColorSetting register(ModuleLayer provider) {
        super.reg(provider);
        return this;
    }

    public ColorSetting collection(Collection collection) {
        collection.put(this);
        return this;
    }
}
