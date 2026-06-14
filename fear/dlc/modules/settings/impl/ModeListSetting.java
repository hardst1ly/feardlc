/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.settings.impl;

import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.class_2561;

public class ModeListSetting
extends SettingLayer {
    private final List<BooleanSetting> values = new ArrayList();

    public ModeListSetting(class_2561 name, class_2561 description, Supplier<Boolean> visible) {
        super(name, description, visible);
    }

    public ModeListSetting set(String ... list) {
        Objects.requireNonNull(this.values);
        Arrays.stream(list).map(this::create).forEach(this.values::add);
        return this;
    }

    public boolean empty() {
        return this.values.isEmpty();
    }

    public boolean emptySelected() {
        return this.values.stream().filter(BooleanSetting::getEnabled).toList().isEmpty();
    }

    public List<String> asStringList() {
        return this.values.stream().map(SettingLayer::getName).map(class_2561::getString).toList();
    }

    public List<String> getSelected() {
        return this.values.stream().filter(BooleanSetting::getEnabled).map(SettingLayer::getName).map(class_2561::getString).toList();
    }

    public BooleanSetting get(String text) {
        return Objects.requireNonNull((BooleanSetting)this.values.stream().filter(ModeListSetting::lambda$get$0 /* captured: text */).findFirst().orElse(null));
    }

    public BooleanSetting create(String text) {
        return new BooleanSetting(class_2561.method_30163(text), class_2561.method_43473(), ModeListSetting::lambda$create$1);
    }

    public ModeListSetting register(ModuleLayer provider) {
        super.reg(provider);
        return this;
    }

    public ModeListSetting collection(Collection collection) {
        collection.put(this);
        return this;
    }

    public List<BooleanSetting> getValues() {
        return this.values;
    }
}
