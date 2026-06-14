/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import net.minecraft.class_1702;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class AutoEat
extends ModuleLayer {
    private static final class_310 mc = class_310.method_1551();
    private int originalSlot = -1;
    public static boolean onSelfDestruct = false;

    public AutoEat() {
        super(class_2561.method_30163("Auto Eat"), class_2561.method_30163("\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0435\u0441\u0442 \u0435\u0434\u0443."), Category.Player);
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
        this.originalSlot = -1;
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
        super.deactivate();
    }

    @Subscribe
    private void onEvent(TickEvent e) {
        if (onSelfDestruct) {
            super.deactivate();
            return;
        }
        if (!super.getEnabled().booleanValue() || mc.field_1724 == null) {
            return;
        }
        class_746 player = mc.field_1724;
        if (player == null) {
            return;
        }
        class_1702 hunger = player.method_7344();
        int foodLevel = hunger.method_7586();
        if (foodLevel < 5) {
            int foodSlot = AutoEat.findFood();
            if (foodSlot != -1) {
                if (player.method_31548().field_7545 != foodSlot) {
                    this.originalSlot = player.method_31548().field_7545;
                    player.method_31548().field_7545 = foodSlot;
                }
                mc.field_1690.field_1904.method_23481(true);
                return;
            }
        }
        mc.field_1690.field_1904.method_23481(false);
        player.method_31548().field_7545 = this.originalSlot;
        if (this.originalSlot != -1 && player.method_31548().field_7545 != this.originalSlot) {
            this.originalSlot = -1;
        }
    }

    private static int findFood() {
        for (int i = 0; i < 9; i++) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            if (stack == null || stack.method_7960() && AutoEat.isFood(stack)) continue;
            return i;
        }
        return -1;
    }

    private static boolean isFood(class_1799 stack) {
        return stack.method_7909() == class_1802.field_8176 || stack.method_7909() == class_1802.field_8261 || stack.method_7909() == class_1802.field_8229 || stack.method_7909() == class_1802.field_8544 || stack.method_7909() == class_1802.field_8373 || stack.method_7909() == class_1802.field_8509 || stack.method_7909() == class_1802.field_8347 || stack.method_7909() == class_1802.field_8752 || stack.method_7909() == class_1802.field_8512 || stack.method_7909() == class_1802.field_8179 || stack.method_7909() == class_1802.field_8071 || stack.method_7909() == class_1802.field_8186 || stack.method_7909() == class_1802.field_8497 || stack.method_7909() == class_1802.field_16998 || stack.method_7909() == class_1802.field_28659 || stack.method_7909() == class_1802.field_8551 || stack.method_7909() == class_1802.field_8423 || stack.method_7909() == class_1802.field_8741 || stack.method_7909() == class_1802.field_8208 || stack.method_7909() == class_1802.field_8308 || stack.method_7909() == class_1802.field_8515 || stack.method_7909() == class_1802.field_8766;
    }
}
