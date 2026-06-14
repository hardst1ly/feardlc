/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.settings;

import fear.dlc.api.animations.Animation;
import fear.dlc.api.animations.implement.DecelerateAnimation;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.SettingLayerBuilder;
import java.util.function.Supplier;
import net.minecraft.class_2561;

public abstract class SettingLayer
implements SettingLayerBuilder {
    private final class_2561 name;
    private final class_2561 description;
    private final Supplier<Boolean> visible;
    private final Animation animation = new DecelerateAnimation().setMs(250).setValue(1);

    public class_2561 getDescription() {
        return this.description == null ? class_2561.method_30163("") : this.description;
    }

    public void reg(ModuleLayer provider) {
        provider.getSettingLayers().add(this);
    }

    public class_2561 getName() {
        return this.name;
    }

    public Supplier<Boolean> getVisible() {
        return this.visible;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public SettingLayer(class_2561 name, class_2561 description, Supplier<Boolean> visible) {
        this.name = name;
        this.description = description;
        this.visible = visible;
    }
}
