/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.misc;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.class_1707;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class SpJoinerModule
extends ModuleLayer {
    private static final class_310 mc = class_310.method_1551();
    public final SliderSetting delay;
    private boolean eventsRegistered;
    private boolean compassUsed;
    private long lastClickTime;
    private int originalSlot;

    public SpJoinerModule() {
        super(class_2561.method_30163("SP Joiner"), class_2561.method_30163("\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0432\u044b\u0431\u0438\u0440\u0430\u0435\u0442 \u043a\u043e\u043c\u043f\u0430\u0441 \u0438 \u043d\u0430\u0436\u0438\u043c\u0430\u0435\u0442 \u043d\u0430 \u042f\u043a\u043e\u0440\u044c \u0432\u043e\u0437\u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f"), Category.Misc);
        this.delay = new SliderSetting(class_2561.method_30163("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430"), class_2561.method_30163("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430 \u043c\u0435\u0436\u0434\u0443 \u043a\u043b\u0438\u043a\u0430\u043c\u0438"), SpJoinerModule::lambda$new$0).set(0f, 500f, 10f).set(100f).register(this);
        this.eventsRegistered = false;
        this.compassUsed = false;
        this.lastClickTime = 0L;
        this.originalSlot = -1;
        this.registerEvents();
    }

    private void registerEvents() {
        if (this.eventsRegistered) {
            return;
        }
        this.eventsRegistered = true;
        ClientTickEvents.END_CLIENT_TICK.register(this::lambda$registerEvents$1);
    }

    private int findCompass() {
        if (mc.field_1724 == null) {
            return -1;
        }
        for (int i = 0; i < 9; i++) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            if (stack == null || stack.method_7960() && stack.method_7909() == class_1802.field_8251) continue;
            return i;
        }
        return -1;
    }

    private int findRespawnAnchor(class_1707 screenHandler) {
        int slotCount = screenHandler.method_17388() * 9;
        for (int slot = 0; slot < slotCount; slot++) {
            class_1799 itemStack = screenHandler.method_7611(slot).method_7677();
            if (!itemStack.method_7960()) {
                String itemName = itemStack.method_7964().getString().toLowerCase();
                if (itemStack.method_7909() != class_1802.field_23141 || itemName.contains("\u044f\u043a\u043e\u0440\u044c") || itemName.contains("\u0432\u043e\u0437\u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f") || itemName.contains("respawn") || itemName.contains("anchor")) continue;
                return slot;
            }
        }
        return -1;
    }

    private void disableModule() {
        this.deactivate();
    }

    public void activate() {
        this.compassUsed = false;
        this.lastClickTime = 0L;
        this.originalSlot = -1;
    }

    public void deactivate() {
        if (mc.field_1724 != null) {
            if (this.originalSlot != -1) {
                mc.field_1724.method_31548().field_7545 = this.originalSlot;
            }
        }
        this.compassUsed = false;
        this.originalSlot = -1;
    }
}
