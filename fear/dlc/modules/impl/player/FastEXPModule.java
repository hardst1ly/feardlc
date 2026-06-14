/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BindSetting;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.minecraft.class_1268;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_2868;
import net.minecraft.class_3675;
import net.minecraft.class_746;

public class FastEXPModule
extends ModuleLayer {
    public final BindSetting bind;
    public final SliderSetting throwsPerSecond;
    public final BooleanSetting silent;
    private int previousSlot;
    private boolean active;
    private long lastThrowMs;

    public FastEXPModule() {
        super(class_2561.method_30163("Fast EXP"), class_2561.method_30163("\u0411\u044b\u0441\u0442\u0440\u044b\u0439 \u0431\u0440\u043e\u0441\u043e\u043a \u0431\u0443\u0442\u044b\u043b\u043e\u043a \u043e\u043f\u044b\u0442\u0430 \u043f\u043e \u0431\u0438\u043d\u0434y"), Category.Player);
        this.bind = new BindSetting(class_2561.method_30163("\u041a\u043b\u0430\u0432\u0438\u0448\u0430"), class_2561.method_30163("\u0417\u0430\u0436\u043c\u0438 \u0447\u0442\u043e\u0431\u044b \u043a\u0438\u0434\u0430\u0442\u044c \u043e\u043f\u044b\u0442"), FastEXPModule::lambda$new$0).register(this);
        this.throwsPerSecond = new SliderSetting(class_2561.method_30163("\u0411\u0440\u043e\u0441\u043a\u043e\u0432/\u0441\u0435\u043a"), class_2561.method_30163("\u0421\u043a\u043e\u043b\u044c\u043a\u043e \u0440\u0430\u0437 \u0432 \u0441\u0435\u043a\u0443\u043d\u0434\u0443 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c \u0431\u0443\u0442\u044b\u043b\u043a\u0443"), FastEXPModule::lambda$new$1).set(1f, 20f, 1f).set(8f).register(this);
        this.silent = new BooleanSetting(class_2561.method_30163("\u0422\u0438\u0445\u0438\u0439 \u0441\u0432\u0430\u043f"), class_2561.method_30163("\u041d\u0435 \u0434\u0432\u0438\u0433\u0430\u0442\u044c \u0432\u0438\u0434\u0438\u043c\u044b\u0439 \u0441\u043b\u043e\u0442 \u043f\u0440\u0438 \u0441\u0432\u0430\u043f\u0435"), FastEXPModule::lambda$new$2).set(false).register(this);
        this.previousSlot = -1;
        this.active = false;
        this.lastThrowMs = 0L;
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        if (this.active) {
            this.restoreSlot();
        }
        super.deactivate();
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (!this.getEnabled().booleanValue() || mc.field_1724 == null || mc.field_1761 == null) {
            return;
        }
        boolean held = this.isBindHeld();
        if (held) {
            if (!this.active) {
                this.startThrowing();
            }
            if (this.active) {
                this.doThrow();
            }
        } else {
            if (this.active) {
                this.restoreSlot();
            }
        }
    }

    private boolean isBindHeld() {
        int key = this.bind.getKey().intValue();
        if (key == -1) {
            return false;
        }
        try {
            return class_3675.method_15987(mc.method_22683().method_4490(), key);
        }
        catch (Exception e) {
            return false;
        }
    }

    private void startThrowing() {
        class_746 player = mc.field_1724;
        class_1661 inv = player.method_31548();
        int bottleSlot = this.findBottleSlot(inv);
        if (bottleSlot == -1) {
            return;
        }
        this.previousSlot = inv.field_7545;
        if (bottleSlot != this.previousSlot) {
            this.switchSlot(bottleSlot);
        }
        this.active = true;
        this.lastThrowMs = 0L;
    }

    private void doThrow() {
        long now = System.currentTimeMillis();
        long minInterval = (1000f / Math.max(1f, this.throwsPerSecond.getValue().floatValue()));
        if (now - this.lastThrowMs < minInterval) {
            return;
        }
        class_1799 held = mc.field_1724.method_31548().method_7391();
        if (held == null || held.method_7960() || held.method_7909() != class_1802.field_8287) {
            int next = this.findBottleSlot(mc.field_1724.method_31548());
            if (next == -1) {
                this.restoreSlot();
                return;
            }
            this.switchSlot(next);
        }
        mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
        mc.field_1724.method_6104(class_1268.field_5808);
        this.lastThrowMs = now;
    }

    private void restoreSlot() {
        if (this.previousSlot != -1) {
            if (mc.field_1724 != null) {
                this.switchSlot(this.previousSlot);
            }
        }
        this.previousSlot = -1;
        this.active = false;
        this.lastThrowMs = 0L;
    }

    private int findBottleSlot(class_1661 inv) {
        for (int i = 0; i < 9; i++) {
            class_1799 stack = inv.method_5438(i);
            if (stack.method_7960() && stack.method_7909() == class_1802.field_8287) continue;
            return i;
        }
        return -1;
    }

    private void switchSlot(int slot) {
        if (slot < 0 || slot > 8) {
            return;
        }
        if (mc.field_1724 == null) {
            return;
        }
        mc.field_1724.method_31548().field_7545 = slot;
        mc.field_1724.field_3944.method_52787(new class_2868(slot));
    }
}
