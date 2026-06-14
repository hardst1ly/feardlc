/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.minecraft.class_2561;

public class AspectRatio
extends ModuleLayer {
    public SliderSetting ratio;

    public AspectRatio() {
        super(class_2561.method_30163("Aspect Ratio"), class_2561.method_30163("\u0420\u0430\u0441\u0442\u044f\u0433 \u044d\u043a\u0440\u0430\u043d\u0430."), Category.Render);
        this.ratio = new SliderSetting(class_2561.method_30163("Ratio"), null, AspectRatio::lambda$new$0).set(0.6f, 2.2f, 0.1f).set(1f).register(this);
    }
}
