/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.utility.inventory.SilentInventoryUtil;
import fear.dlc.utility.movement.MovementFreeze;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class AutoTotem
extends ModuleLayer {
    SliderSetting health;
    SliderSetting delay;
    BooleanSetting legit;
    SliderSetting openDelay;
    SliderSetting clickDelay;
    private final class_310 mc;
    private int delayTimer;
    private int actionTimer;
    private int stage;
    private int targetSlot;

    public AutoTotem() {
        super(class_2561.method_30163("Auto Totem"), null, Category.Combat);
        this.health = new SliderSetting(class_2561.method_30163("\u0417\u0434\u043e\u0440\u043e\u0432\u044c\u0435"), class_2561.method_30163("\u041f\u043e\u0440\u043e\u0433 \u0441\u0440\u0430\u0431\u0430\u0442\u044b\u0432\u0430\u043d\u0438\u044f"), AutoTotem::lambda$new$0).set(0.5f, 10f, 0.5f).set(3f).register(this);
        this.delay = new SliderSetting(class_2561.method_30163("\u0417\u0430\u0434\u0435\u0440\u043a\u0436\u0430"), null, AutoTotem::lambda$new$1).set(1f, 5f, 1f).set(3f).register(this);
        this.legit = new BooleanSetting(class_2561.method_30163("\u041b\u0435\u0433\u0438\u0442"), class_2561.method_30163("\u0411\u0435\u0437 GUI: \u043f\u0430\u043a\u0435\u0442 \u043e\u0442\u043a\u0440\u044b\u0442\u0438\u044f \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044f + \u043f\u0430\u0443\u0437\u0430 \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f"), AutoTotem::lambda$new$2).set(true).register(this);
        this.openDelay = new SliderSetting(class_2561.method_30163("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430 \u043e\u0442\u043a\u0440\u044b\u0442\u0438\u044f"), null, AutoTotem::lambda$new$3).set(1f, 10f, 1f).set(2f).register(this);
        this.clickDelay = new SliderSetting(class_2561.method_30163("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430 \u041a\u043b\u0438\u043a\u0430"), null, AutoTotem::lambda$new$4).set(1f, 10f, 1f).set(1f).register(this);
        this.mc = class_310.method_1551();
        this.delayTimer = 0;
        this.actionTimer = 0;
        this.stage = 0;
        this.targetSlot = -1;
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        this.reset();
        super.deactivate();
    }

    private void reset() {
        this.delayTimer = 0;
        this.actionTimer = 0;
        this.stage = 0;
        this.targetSlot = -1;
    }

    @Subscribe
    public void onUpdate(TickEvent e) {
        if (this.mc.field_1724 == null || this.mc.field_1761 == null) {
            return;
        }
        if (!super.getEnabled().booleanValue()) {
            return;
        }
        float hp = this.mc.field_1724.method_6032() + this.mc.field_1724.method_6067();
        class_1799 offhand = this.mc.field_1724.method_6079();
        if (offhand.method_31574(class_1802.field_8288)) {
            if (this.stage != 0) {
                SilentInventoryUtil.closeInventory();
                this.reset();
            }
            return;
        }
        this.handleLegit();
        if (this.legit.getEnabled().booleanValue() && this.stage != 0) {
            return;
        }
        if (this.delayTimer > 0) {
            this.delayTimer = this.delayTimer - 1;
            return;
        }
        float healthThreshold = this.health.getValue().floatValue() * 2f;
        if (hp > healthThreshold) {
            return;
        }
        int slot = this.findTotem();
        if (slot == -1) {
            return;
        }
        if (this.legit.getEnabled().booleanValue()) {
            this.startLegit(slot);
        } else {
            this.swapInstant(slot);
            this.delayTimer = this.delay.getValue().intValue();
        }
    }

    private void startLegit(int slot) {
        this.targetSlot = slot;
        this.stage = 1;
        this.actionTimer = this.openDelay.getValue().intValue();
    }

    private void handleLegit() {
        if (this.targetSlot == -1) {
            this.reset();
            return;
        }
        if (this.actionTimer > 0) {
            this.actionTimer = this.actionTimer - 1;
            return;
        }
        if (MovementFreeze.isActive()) {
            return;
        }
        switch (this.stage) {
            case 1:
                SilentInventoryUtil.openInventory();
                this.stage = 2;
                this.actionTimer = this.clickDelay.getValue().intValue();
                return;
            case 2:
                SilentInventoryUtil.swapToOffhand(this.targetSlot);
                this.stage = 3;
                this.actionTimer = this.clickDelay.getValue().intValue();
                return;
            case 3:
                if (!this.mc.field_1724.field_7498.method_34255().method_7960()) {
                    SilentInventoryUtil.clickSlot(this.targetSlot, 0, class_1713.field_7790);
                }
                SilentInventoryUtil.closeInventory();
                this.reset();
                this.delayTimer = this.delay.getValue().intValue();
                return;
            default:
                this.reset();
                return;
        }
    }

    private void swapInstant(int slot) {
        int syncId = this.mc.field_1724.field_7498.field_7763;
        this.mc.field_1761.method_2906(syncId, slot, 0, class_1713.field_7790, this.mc.field_1724);
        this.mc.field_1761.method_2906(syncId, 45, 0, class_1713.field_7790, this.mc.field_1724);
        if (!this.mc.field_1724.field_7498.method_34255().method_7960()) {
            this.mc.field_1761.method_2906(syncId, slot, 0, class_1713.field_7790, this.mc.field_1724);
        }
    }

    private int findTotem() {
        for (int i = 9; i < 45; i++) {
            class_1799 stack = this.mc.field_1724.field_7498.method_7611(i).method_7677();
            if (!stack.method_31574(class_1802.field_8288)) continue;
            return i;
        }
        return -1;
    }
}
