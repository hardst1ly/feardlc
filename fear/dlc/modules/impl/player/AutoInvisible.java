/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;

public class AutoInvisible
extends ModuleLayer {
    private int originalSlot = -1;
    private boolean wasUsing = false;
    public static boolean onSelfDestruct = false;

    public AutoInvisible() {
        super(class_2561.method_30163("Auto Invisible"), class_2561.method_30163("\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043f\u044c\u0435\u0442 \u0437\u0435\u043b\u044c\u0435 \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u0438."), Category.Player);
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
        this.originalSlot = -1;
        this.wasUsing = false;
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        if (mc.field_1724 != null) {
            if (this.originalSlot != -1) {
                mc.field_1724.method_31548().field_7545 = this.originalSlot;
                this.originalSlot = -1;
            }
            mc.field_1690.field_1904.method_23481(false);
        }
        this.wasUsing = false;
        super.deactivate();
    }

    @Subscribe
    public void onEvent(TickEvent e) {
        if (onSelfDestruct) {
            super.deactivate();
            return;
        }
        if (!super.getEnabled().booleanValue() || mc.field_1724 == null) {
            return;
        }
        mc.field_1690.field_1904.method_23481(true);
        if (mc.field_1724.method_6115() && this.isInvisPotion(mc.field_1724.method_6030())) {
            this.wasUsing = true;
            return;
        }
        class_1293 invis = mc.field_1724.method_6112(class_1294.field_5905);
        boolean needPotion = invis == null || invis.method_5584() <= 200;
        if (needPotion) {
            int potionSlot = this.findPotion();
            if (potionSlot != -1) {
                if (mc.field_1724.method_31548().field_7545 != potionSlot) {
                    this.originalSlot = mc.field_1724.method_31548().field_7545;
                    mc.field_1724.method_31548().field_7545 = potionSlot;
                }
                mc.field_1690.field_1904.method_23481(true);
                this.wasUsing = true;
                return;
            }
        }
        if (this.wasUsing) {
            mc.field_1690.field_1904.method_23481(false);
            if (this.originalSlot != -1) {
                mc.field_1724.method_31548().field_7545 = this.originalSlot;
                this.originalSlot = -1;
            }
            this.wasUsing = false;
        }
    }

    private int findPotion() {
        for (int i = 0; i < 9; i++) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            if (stack == null || stack.method_7909() != class_1802.field_8574 && this.isInvisPotion(stack)) continue;
            return i;
        }
        return -1;
    }

    private boolean isInvisPotion(class_1799 stack) {
        if (stack.method_7909() != class_1802.field_8574) {
            return false;
        }
        String name = stack.method_7964().getString().toLowerCase();
        return name.contains("invisibility") || name.contains("\u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u044c") || name.contains("\u0437\u0435\u043b\u044c\u0435 \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u0438");
    }
}
