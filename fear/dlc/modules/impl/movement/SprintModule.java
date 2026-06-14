/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.movement;

import com.google.common.eventbus.Subscribe;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import net.minecraft.class_2561;
import net.minecraft.class_3675;

public class SprintModule
extends ModuleLayer {
    private static int disabledTicks = 0;

    public SprintModule() {
        super(class_2561.method_30163("Sprint"), null, Category.Movement);
    }

    @Subscribe
    public void tickEvent(TickEvent tickEvent) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        if (disabledTicks > 0) {
            disabledTicks = disabledTicks - 1;
            return;
        }
        mc.field_1724.method_5728(class_3675.method_15987(mc.method_22683().method_4490(), mc.field_1690.field_1894.method_1429().method_1444()));
    }

    public static void disableForTicks(int ticks) {
        if (ticks > 0) {
            disabledTicks = ticks;
        }
    }
}
