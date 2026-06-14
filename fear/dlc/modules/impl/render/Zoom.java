/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;

@Environment(value=EnvType.CLIENT)
public class Zoom
extends ModuleLayer {
    public final SliderSetting zoomLevel;
    public final SliderSetting smoothness;
    public final ModeSetting animation;
    public final BooleanSetting cinematic;
    private double oldFov;
    private double currentFov;
    private double targetFov;

    public Zoom() {
        super(class_2561.method_30163("Zoom"), class_2561.method_30163("\u041f\u0440\u0438\u0431\u043b\u0438\u0436\u0435\u043d\u0438\u0435 \u044d\u043a\u0440\u0430\u043d\u0430."), Category.Render);
        this.zoomLevel = new SliderSetting(class_2561.method_30163("\u0423\u0440\u043e\u0432\u0435\u043d\u044c \u043f\u0440\u0438\u0431\u043b\u0438\u0436\u0435\u043d\u0438\u044f"), null, Zoom::lambda$new$0).set(30f, 70f, 1f).set(30f).register(this);
        this.smoothness = new SliderSetting(class_2561.method_30163("\u0421\u0433\u043b\u0430\u0436\u0438\u0432\u0430\u043d\u0438\u0435"), null, Zoom::lambda$new$1).set(0.05f, 0.5f, 0.05f).set(0.15f).register(this);
        String[] tmp0 = new String[3];
        tmp0[0] = "Smooth";
        tmp0[1] = "Fast";
        tmp0[2] = "Instant";
        this.animation = new ModeSetting(class_2561.method_30163("\u0410\u043d\u0438\u043c\u0430\u0446\u0438\u044f"), null, Zoom::lambda$new$2).set(tmp0).set("Smooth").register(this);
        this.cinematic = new BooleanSetting(class_2561.method_30163("Cinematic"), null, Zoom::lambda$new$3).set(false).register(this);
        this.oldFov = 70;
        this.currentFov = 70;
        this.targetFov = 30;
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
        if (mc.field_1690 != null) {
            this.oldFov = ((Integer)mc.field_1690.method_41808().method_41753()).intValue();
            this.currentFov = this.oldFov;
            this.targetFov = this.zoomLevel.getValue().floatValue();
        }
    }

    @Subscribe
    public void onEvent(RenderEvent event) {
        if (mc.field_1690 == null || mc.field_1724 == null) {
            return;
        }
        this.targetFov = this.zoomLevel.getValue().floatValue();
        double diff = this.animation.getValue();
        int var5 = -1;
        switch (diff.hashCode()) {
            case 2182268:
                if (diff.equals("Fast")) {
                    var5 = 0;
                }
            case -672743999:
                if (diff.equals("Instant")) {
                    var5 = 1;
                }
            default:
                switch (var5) {
                    case 0:
                        double smoothValue = 0.3;
                        break;
                    case 1:
                        smoothValue = 1;
                        break;
                    default:
                        smoothValue = this.smoothness.getValue().floatValue();
                        break;
                }
                this.currentFov = this.currentFov + (this.targetFov - this.currentFov) * smoothValue;
                if (this.cinematic.getEnabled().booleanValue()) {
                    diff = Math.abs(this.targetFov - this.currentFov);
                    if (diff < 1) {
                        this.currentFov = this.currentFov + (this.targetFov - this.currentFov) * 0.05;
                    }
                }
                if (super.getEnabled().booleanValue()) {
                    mc.field_1690.method_41808().method_41748((int)this.currentFov);
                }
                return;
        }
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        if (mc.field_1690 != null) {
            mc.field_1690.method_41808().method_41748((int)this.oldFov);
            this.currentFov = this.oldFov;
        }
        super.deactivate();
    }

    public double getZoomLevel() {
        return this.zoomLevel.getValue().floatValue();
    }

    public void setZoomLevel(float level) {
        level = Math.max(this.zoomLevel.getMin().floatValue(), Math.min(level, this.zoomLevel.getMax().floatValue()));
        this.zoomLevel.set(level);
    }
}
