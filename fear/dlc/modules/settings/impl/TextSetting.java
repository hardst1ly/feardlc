/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.settings.impl;

import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.Collection;
import java.util.function.Supplier;
import net.minecraft.class_2561;

public class TextSetting
extends SettingLayer {
    private StringBuilder text = new StringBuilder();
    private String placeholder = "";
    private int maxLength = 256;
    private boolean selected = false;

    public TextSetting(class_2561 name, class_2561 description, Supplier<Boolean> visible) {
        super(name, description, visible);
    }

    public TextSetting set(String value) {
        this.text.setLength(0);
        if (value != null) {
            if (this.maxLength > 0) {
                if (value.length() > this.maxLength) {
                    value = value.substring(0, this.maxLength);
                }
            }
            this.text.append(value);
        }
        return this;
    }

    public TextSetting placeholder(String placeholder) {
        this.placeholder = placeholder == null ? "" : placeholder;
        return this;
    }

    public TextSetting maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public String getValue() {
        return this.text.toString();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void append(char chr) {
        if (this.maxLength > 0 && this.text.length() >= this.maxLength) {
            return;
        }
        this.text.append(chr);
    }

    public void backspace() {
        if (this.text.isEmpty()) {
            return;
        }
        this.text.deleteCharAt(this.text.length() - 1);
    }

    public void clear() {
        this.text.setLength(0);
    }

    public TextSetting register(ModuleLayer provider) {
        super.reg(provider);
        return this;
    }

    public TextSetting collection(Collection collection) {
        collection.put(this);
        return this;
    }

    public StringBuilder getText() {
        return this.text;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
