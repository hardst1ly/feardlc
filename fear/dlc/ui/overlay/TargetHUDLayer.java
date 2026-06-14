/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.api.draggable.DraggableLayer;
import fear.dlc.modules.impl.combat.AimAssistModule;
import fear.dlc.modules.impl.render.HudModule;
import fear.dlc.ui.overlay.OverlayRenderer;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.class_1657;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_7532;
import net.minecraft.class_9779;

public class TargetHUDLayer
extends DraggableLayer {
    float animationValue;
    private static final float PORTRAIT_SIZE = 32f;
    private static final Color PORTRAIT_BG_COLOR = new Color(22, 22, 26, 255);
    static Supplier<HudModule> module = Suppliers.memoize(TargetHUDLayer::lambda$static$0);

    public TargetHUDLayer() {
        super(10f, 45f, 140f, 45f, TargetHUDLayer::lambda$new$1);
    }

    public void render(class_332 context, double mouseX, double mouseY, class_9779 tickCounter) {
        class_1657 target = AimAssistModule.lockedTarget;
        if (target == null) {
            target = mc.field_1724;
        }
        OverlayRenderer.rect(context, this.getX().floatValue(), this.getY().floatValue(), this.getWidth().floatValue(), this.getHeight().floatValue());
        this.animationValue = class_3532.method_16439(0.1f, this.animationValue, Math.clamp(target.method_6032() / target.method_6063(), 0f, 1f));
        float portraitX = this.getX().floatValue() + 6.7f;
        float portraitY = this.getY().floatValue() + (this.getHeight().floatValue() - 32f) / 2f;
        ((BuiltRectangle)Api.rectangle().radius(new QuadRadiusState(4f)).color(new QuadColorState(PORTRAIT_BG_COLOR.getRGB())).size(new SizeState(32f, 32f)).build()).render(context.method_51448().method_23760().method_23761(), portraitX, portraitY);
        if (target != null) {
            class_7532.method_52722(context, mc.method_1582().method_52862(target.method_7334()), (int)portraitX, (int)portraitY, 32);
        }
        float contentX = portraitX + 32f + 8f;
        ((BuiltText)Api.text().font(Api.inter()).text(target.method_5477().getString()).color(-3554305).size(6f).build()).render(context.method_51448().method_23760().method_23761(), contentX, this.getY().floatValue() + 8f);
        int distance = mc.field_1724.method_5739(target);
        String distText = distance + "m";
        float distWidth = Api.inter().getWidth(distText, 6f);
        ((BuiltText)Api.text().font(Api.inter()).text(distText).color(ColorUtility.applyOpacity(-3554305, 50)).size(6f).build()).render(context.method_51448().method_23760().method_23761(), this.getX().floatValue() + this.getWidth().floatValue() - distWidth - 5f, this.getY().floatValue() + 8f);
        Object[] tmp0 = new Object[1];
        tmp0[0] = target.method_6032();
        ((BuiltText)Api.text().font(Api.inter()).text("Health: " + String.format("%.1f", tmp0)).color(ColorUtility.applyOpacity(-3554305, 50)).size(6f).build()).render(context.method_51448().method_23760().method_23761(), contentX, this.getY().floatValue() + 16f);
        ((BuiltRectangle)Api.rectangle().radius(new QuadRadiusState(0.2f)).color(new QuadColorState(-7893505, -7893505, -3554305, -3554305)).size(new SizeState((this.getWidth().floatValue() - 10f) * this.animationValue, 3f)).build()).render(context.method_51448().method_23760().method_23761(), this.getX().floatValue() + 5f, this.getY().floatValue() + this.getHeight().floatValue() - 7f);
    }
}
