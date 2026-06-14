/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_2561;

public class AntiBlind
extends ModuleLayer {
    public AntiBlind() {
        super(class_2561.method_30163("AntiBlind"), class_2561.method_30163("\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u044d\u0444\u0444\u0435\u043a\u0442 \u0441\u043b\u0435\u043f\u043e\u0442\u044b \u0438 \u0442\u044c\u043c\u044b \u043e\u0442 \u0432\u0430\u0440\u0434\u0435\u043d\u0430."), Category.Render);
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
        this.removeDarknessEffect();
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        super.deactivate();
    }

    @Subscribe
    public void onUpdate(TickEvent e) {
        this.removeDarknessEffect();
    }

    private void removeDarknessEffect() {
        if (mc.field_1724 == null || mc.field_1687 == null) {
            return;
        }
        class_1293 darknessEffect = mc.field_1724.method_6112(class_1294.field_38092);
        if (darknessEffect != null) {
            mc.field_1724.method_6016(class_1294.field_38092);
        }
        class_1293 blindnessEffect = mc.field_1724.method_6112(class_1294.field_5919);
        if (blindnessEffect != null) {
            mc.field_1724.method_6016(class_1294.field_5919);
        }
    }
}
