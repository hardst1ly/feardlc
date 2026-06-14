/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.FriendManager;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.class_1268;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1819;
import net.minecraft.class_2246;
import net.minecraft.class_2561;
import net.minecraft.class_2868;

public class TriggerBotModule
extends ModuleLayer {
    public final BooleanSetting pauseEating;
    public final BooleanSetting onlyCriticals;
    public final BooleanSetting breakShield;
    public final BooleanSetting targetFriends;
    public final SliderSetting sprintBlockDuration;
    public final BooleanSetting disableOnInventoryOpen;
    public static boolean onSelfDestruct = false;
    private final Random random;
    private int delay;
    private int breakShieldState;
    private int breakShieldDelay;
    private int previousSlot;
    private long lastBreakAttempt;
    private int sprintBlockTicks;

    public TriggerBotModule() {
        super(class_2561.method_30163("Trigger Bot"), class_2561.method_30163("\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0431\u044c\u0435\u0442 \u0438\u0433\u0440\u043e\u043a\u043e\u0432."), Category.Combat);
        this.pauseEating = new BooleanSetting(class_2561.method_30163("\u041f\u0430\u0443\u0437\u0430 \u043f\u0440\u0438 \u0435\u0434\u0435"), null, TriggerBotModule::lambda$new$0).set(true).register(this);
        this.onlyCriticals = new BooleanSetting(class_2561.method_30163("\u041e\u043d\u043b\u0438 \u043a\u0440\u0438\u0442\u044b"), null, TriggerBotModule::lambda$new$1).set(true).register(this);
        this.breakShield = new BooleanSetting(class_2561.method_30163("\u041b\u043e\u043c\u0430\u0442\u044c \u0449\u0438\u0442\u044b"), null, TriggerBotModule::lambda$new$2).set(false).register(this);
        this.targetFriends = new BooleanSetting(class_2561.method_30163("\u0422\u0430\u0440\u0433\u0435\u0442\u0438\u0442\u044c \u0434\u0440\u0443\u0437\u0435\u0439"), null, TriggerBotModule::lambda$new$3).set(false).register(this);
        this.sprintBlockDuration = new SliderSetting(class_2561.method_30163("\u0421\u0431\u0440\u043e\u0441 \u0441\u043f\u0440\u0438\u043d\u0442\u0430"), class_2561.method_30163("\u041d\u0430 \u0441\u043a\u043e\u043b\u044c\u043a\u043e \u0442\u0438\u043a\u043e\u0432 \u043e\u0442\u043a\u043b\u044e\u0447\u0430\u0435\u0442\u0441\u044f \u0441\u043f\u0440\u0438\u043d\u0442 \u043f\u0435\u0440\u0435\u0434 \u0443\u0434\u0430\u0440\u043e\u043c"), TriggerBotModule::lambda$new$4).set(1f, 10f, 1f).set(3f).register(this);
        this.disableOnInventoryOpen = new BooleanSetting(class_2561.method_30163("Disable On Inventory"), class_2561.method_30163("\u041e\u0442\u043a\u043b\u044e\u0447\u0430\u0435\u0442 \u0442\u0440\u0438\u0433\u0433\u0435\u0440 \u043f\u0440\u0438 \u043e\u0442\u043a\u0440\u044b\u0442\u043e\u043c \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435"), TriggerBotModule::lambda$new$5).set(true).register(this);
        this.random = new Random();
        this.breakShieldState = 0;
        this.breakShieldDelay = 0;
        this.previousSlot = -1;
        this.lastBreakAttempt = 0L;
        this.sprintBlockTicks = 0;
        this.setKey(71);
    }

    public void activate() {
        super.activate();
    }

    public void deactivate() {
        this.delay = 0;
        this.breakShieldState = 0;
        this.breakShieldDelay = 0;
        this.previousSlot = -1;
        this.sprintBlockTicks = 0;
        super.deactivate();
    }

    @Subscribe
    private void onTick(TickEvent event) {
        if (!super.getEnabled().booleanValue()) {
            return;
        }
        this.tickEvent();
    }

    private boolean shouldDisableTrigger() {
        if (mc.field_1724 == null) {
            return true;
        }
        if (this.disableOnInventoryOpen.getEnabled().booleanValue() && mc.field_1755 != null) {
            return true;
        }
        return false;
    }

    public void tickEvent() {
        if (onSelfDestruct) {
            super.deactivate();
            return;
        }
        if (this.shouldDisableTrigger()) {
            return;
        }
        if (mc.field_1724 == null) {
            return;
        }
        if (this.sprintBlockTicks > 0) {
            this.sprintBlockTicks = this.sprintBlockTicks - 1;
            mc.field_1724.method_5728(false);
        }
        if (mc.field_1724.method_6115() && this.pauseEating.getEnabled().booleanValue()) {
            return;
        }
        if (!(this.breakShieldState <= 0)) {
            if (this.breakShieldDelay > 0) {
                this.breakShieldDelay = this.breakShieldDelay - 1;
                return;
            }
        }
        switch (this.breakShieldState) {
            case 1:
                class_1297 ent = mc.field_1692;
                if (ent != null) {
                    if (ent instanceof class_1309) {
                        class_1309 target = ent;
                        if (this.shouldBreakShield(target)) {
                            mc.field_1761.method_2918(mc.field_1724, target);
                            mc.field_1724.method_6104(class_1268.field_5808);
                            this.breakShieldState = 2;
                            this.breakShieldDelay = 2 + this.random.nextInt(3);
                        }
                    }
                } else {
                    this.returnToPreviousSlot();
                    this.breakShieldState = 0;
                }
            case 2:
                this.returnToPreviousSlot();
                this.breakShieldState = 0;
            default:
                return;
                if (this.delay > 0) {
                    this.delay = this.delay - 1;
                    return;
                }
                class_1297 ent = mc.field_1692;
                if (ent == null) { /* goto L509; */ }
                if (!(ent instanceof class_1309)) { /* goto L509; */ }
                class_1309 target = ent;
                if (!this.targetFriends.getEnabled().booleanValue() && this.isFriend(target)) {
                    return;
                }
                if (this.breakShield.getEnabled().booleanValue()) {
                    if (this.shouldBreakShield(target)) {
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - this.lastBreakAttempt > (long)(280 + this.random.nextInt(80))) {
                            this.lastBreakAttempt = currentTime;
                            this.tryBreakShield(target);
                        }
                        return;
                    }
                }
                if (this.onlyCriticals.getEnabled().booleanValue()) {
                    if (mc.field_1724.method_7261(0.5f) < 0.9f) {
                        return;
                    }
                    if (this.isCritImpossible()) {
                        this.performAttack(ent);
                        return;
                    }
                    boolean canCrit = !mc.field_1724.method_24828() && mc.field_1724.field_6017 > 0f && !mc.field_1724.method_5624();
                    if (canCrit) {
                        this.performAttack(ent);
                    } else {
                        if (mc.field_1724.method_5624()) {
                            mc.field_1724.method_5728(false);
                            this.sprintBlockTicks = this.sprintBlockDuration.getValue().floatValue();
                        }
                    }
                } else {
                    this.performAttack(ent);
                }
                return;
        }
    }

    private void performAttack(class_1297 ent) {
        mc.field_1761.method_2918(mc.field_1724, ent);
        mc.field_1724.method_6104(class_1268.field_5808);
        this.delay = 10;
    }

    private boolean isCritImpossible() {
        return mc.field_1724.method_31549().field_7479 || mc.field_1724.method_6059(class_1294.field_5902) || mc.field_1687.method_8320(mc.field_1724.method_24515()).method_26204() == class_2246.field_9983 || mc.field_1724.method_5771() || mc.field_1724.method_5799() || mc.field_1724.method_6101();
    }

    private boolean isFriend(class_1309 entity) {
        if (!(entity instanceof class_1657)) {
            return false;
        }
        return FriendManager.isFriend(entity.method_5477().getString().toLowerCase());
    }

    private boolean shouldBreakShield(class_1309 target) {
        return target.method_6115() && target.method_6030().method_7909() instanceof class_1819 && target instanceof class_1657;
    }

    private void tryBreakShield(class_1309 target) {
        if (mc.field_1724 == null || mc.field_1761 == null || this.breakShieldState != 0) {
            return;
        }
        HotbarSlot axeSlot = this.findItemInHotbar(TriggerBotModule::lambda$tryBreakShield$6);
        if (axeSlot != null) {
            this.previousSlot = mc.field_1724.method_31548().field_7545;
            mc.field_1724.method_31548().field_7545 = axeSlot.getSlotId();
            this.breakShieldState = 1;
            this.breakShieldDelay = 1 + this.random.nextInt(3);
        }
    }

    private void returnToPreviousSlot() {
        mc.field_1724.field_3944.method_52787(new class_2868(this.previousSlot));
        if (this.previousSlot != -1 && this.previousSlot != mc.field_1724.method_31548().field_7545) {
            mc.field_1724.method_31548().field_7545 = this.previousSlot;
        }
    }

    private HotbarSlot findItemInHotbar(Predicate<class_1799> predicate) {
        for (int i = 0; i < 9; i++) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            if (!predicate.test(stack)) continue;
            return new HotbarSlot(i, stack);
        }
        return null;
    }
}
