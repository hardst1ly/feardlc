/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.movement;

import com.google.common.eventbus.Subscribe;
import fear.dlc.api.events.impl.PlayerEvent;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_5498;

public class FreeCamModule
extends ModuleLayer {
    private static FreeCamModule instance;
    public final SliderSetting speed;
    public final SliderSetting acceleration;
    public final BooleanSetting noClip;
    private class_243 cameraPos;
    private class_243 savedPlayerPos;
    private float savedYaw;
    private float savedPitch;
    private class_5498 previousPerspective;

    public FreeCamModule() {
        super(class_2561.method_30163("FreeCam"), class_2561.method_30163("\u0421\u0432\u043e\u0431\u043e\u0434\u043d\u0430\u044f \u043a\u0430\u043c\u0435\u0440\u0430"), Category.Movement);
        this.speed = new SliderSetting(class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c"), class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u043f\u043e\u043b\u0435\u0442\u0430"), FreeCamModule::lambda$new$0).set(0.1f, 5f, 0.1f).set(1f).register(this);
        this.acceleration = new SliderSetting(class_2561.method_30163("\u0423\u0441\u043a\u043e\u0440\u0435\u043d\u0438\u0435"), class_2561.method_30163("\u041c\u043d\u043e\u0436\u0438\u0442\u0435\u043b\u044c \u043f\u0440\u0438 Sprint"), FreeCamModule::lambda$new$1).set(1f, 10f, 0.5f).set(3f).register(this);
        this.noClip = new BooleanSetting(class_2561.method_30163("NoClip"), class_2561.method_30163("\u041f\u0440\u043e\u0445\u043e\u0434\u0438\u0442\u044c \u0441\u043a\u0432\u043e\u0437\u044c \u0431\u043b\u043e\u043a\u0438"), FreeCamModule::lambda$new$2).set(true).register(this);
        instance = this;
    }

    public static FreeCamModule getInstance() {
        return instance;
    }

    public static boolean isFreeCamActive() {
        return instance != null && instance.getEnabled().booleanValue();
    }

    public void activate() {
        super.activate();
        if (mc.field_1724 == null) {
            this.deactivate();
            return;
        }
        this.savedPlayerPos = mc.field_1724.method_19538();
        this.savedYaw = mc.field_1724.method_36454();
        this.savedPitch = mc.field_1724.method_36455();
        this.cameraPos = mc.field_1724.method_33571();
        this.previousPerspective = mc.field_1690.method_31044();
        if (this.previousPerspective == class_5498.field_26664) {
            mc.field_1690.method_31043(class_5498.field_26665);
        }
    }

    public void deactivate() {
        super.deactivate();
        if (mc.field_1724 == null) {
            return;
        }
        if (this.savedPlayerPos != null) {
            mc.field_1724.method_33574(this.savedPlayerPos);
            mc.field_1724.method_36456(this.savedYaw);
            mc.field_1724.method_36457(this.savedPitch);
        }
        if (this.previousPerspective != null) {
            mc.field_1690.method_31043(this.previousPerspective);
        }
    }

    @Subscribe
    public void onTick(RenderEvent.AfterHand event) {
        if (!this.getEnabled().booleanValue() || mc.field_1724 == null) {
            return;
        }
        float yaw = mc.field_1724.method_36454();
        float pitch = mc.field_1724.method_36455();
        class_243 forward = this.getForwardVector(yaw, pitch);
        class_243 right = this.getRightVector(yaw);
        class_243 up = new class_243(0, 1, 0);
        class_243 movement = class_243.field_1353;
        if (mc.field_1690.field_1894.method_1434()) {
            movement = movement.method_1019(forward);
        }
        if (mc.field_1690.field_1881.method_1434()) {
            movement = movement.method_1020(forward);
        }
        if (mc.field_1690.field_1849.method_1434()) {
            movement = movement.method_1019(right);
        }
        if (mc.field_1690.field_1913.method_1434()) {
            movement = movement.method_1020(right);
        }
        if (mc.field_1690.field_1903.method_1434()) {
            movement = movement.method_1019(up);
        }
        if (mc.field_1690.field_1832.method_1434()) {
            movement = movement.method_1020(up);
        }
        if (movement.method_1027() > 0) {
            movement = movement.method_1029();
        }
        float currentSpeed = this.speed.getValue().floatValue();
        if (mc.field_1690.field_1867.method_1434()) {
            currentSpeed = currentSpeed * this.acceleration.getValue().floatValue();
        }
        movement = movement.method_1021((double)currentSpeed);
        this.cameraPos = this.cameraPos.method_1019(movement);
    }

    @Subscribe
    public void onPlayerMovement(PlayerEvent.MovementEvent event) {
        if (!this.getEnabled().booleanValue() || mc.field_1724 == null || this.savedPlayerPos == null) {
            return;
        }
        mc.field_1724.method_33574(this.savedPlayerPos);
        mc.field_1724.method_18799(class_243.field_1353);
    }

    private class_243 getForwardVector(float yaw, float pitch) {
        float yawRad = Math.toRadians((double)yaw);
        float pitchRad = Math.toRadians((double)pitch);
        double x = -Math.sin((double)yawRad) * Math.cos((double)pitchRad);
        double y = -Math.sin((double)pitchRad);
        double z = Math.cos((double)yawRad) * Math.cos((double)pitchRad);
        return new class_243(x, y, z);
    }

    private class_243 getRightVector(float yaw) {
        float yawRad = Math.toRadians((double)(yaw + 90f));
        return new class_243(-Math.sin((double)yawRad), 0, Math.cos((double)yawRad));
    }

    public class_243 getCameraPos() {
        return this.cameraPos != null ? this.cameraPos : (mc.field_1724 != null ? mc.field_1724.method_33571() : class_243.field_1353);
    }
}
