/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.minecraft.class_2561;

public class SaturationDisplayModule
extends ModuleLayer {
    public final BooleanSetting showNumbers;
    public final SliderSetting yOffset;
    public final SliderSetting opacity;

    public SaturationDisplayModule() {
        super(class_2561.method_30163("Saturation HUD"), class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u043f\u043e\u043b\u043e\u0441\u043a\u0443 \u043d\u0430\u0441\u044b\u0449\u0435\u043d\u0438\u044f \u043d\u0430\u0434 \u0433\u043e\u043b\u043e\u0434\u043e\u043c"), Category.Render);
        this.showNumbers = new BooleanSetting(class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u0447\u0438\u0441\u043b\u0430"), class_2561.method_30163("\u041e\u0442\u043e\u0431\u0440\u0430\u0436\u0430\u0442\u044c \u0442\u043e\u0447\u043d\u043e\u0435 \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435 saturation"), SaturationDisplayModule::lambda$new$0).set(false).register(this);
        this.yOffset = new SliderSetting(class_2561.method_30163("\u0421\u043c\u0435\u0449\u0435\u043d\u0438\u0435 Y"), class_2561.method_30163("\u0412\u0435\u0440\u0442\u0438\u043a\u0430\u043b\u044c\u043d\u043e\u0435 \u0441\u043c\u0435\u0449\u0435\u043d\u0438\u0435 \u043f\u043e\u043b\u043e\u0441\u043a\u0438"), SaturationDisplayModule::lambda$new$1).set(-10f, 10f, 1f).set(-10f).register(this);
        this.opacity = new SliderSetting(class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c"), class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u043f\u043e\u043b\u043e\u0441\u043a\u0438 saturation"), SaturationDisplayModule::lambda$new$2).set(0.1f, 1f, 0.05f).set(0.9f).register(this);
    }

    public void activate() {
        super.activate();
    }

    public void deactivate() {
        super.deactivate();
    }
}
