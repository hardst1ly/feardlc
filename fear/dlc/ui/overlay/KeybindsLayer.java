/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.api.draggable.DraggableLayer;
import fear.dlc.modules.impl.render.HudModule;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.ui.overlay.OverlayRenderer;
import fear.dlc.utility.keyboard.KeyBoardUtil;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import fear.dlc.utility.render.utility.Scissors;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.class_332;
import net.minecraft.class_9779;

public class KeybindsLayer
extends DraggableLayer {
    static Supplier<List<ModuleLayer>> modules = KeybindsLayer::lambda$static$1;
    static Supplier<HudModule> module = Suppliers.memoize(KeybindsLayer::lambda$static$2);

    public KeybindsLayer() {
        super(10f, 25f, 70f, 24f, KeybindsLayer::lambda$new$3);
    }

    public void render(class_332 context, double mouseX, double mouseY, class_9779 tickCounter) {
        AtomicReference<Float> height = new AtomicReference(0f);
        ((List)modules.get()).forEach(KeybindsLayer::lambda$render$4 /* captured: height */);
        float titleHeight = 10.4f;
        float titlePadding = 7f;
        float dividerGap = 7f;
        float dividerHeight = 1f;
        float itemSpacing = 8.7f;
        float verticalPadding = 3.7f;
        float horizontalPadding = 8.7f;
        float maxNameWidth = 0f;
        float maxKeyWidth = 0f;
        for (ModuleLayer e : ((List)modules.get())) {
            float nameW = Api.inter().getWidth(e.getModuleName().getString(), 6.1f, 0.1f, 0f);
            float keyW = Api.inter().getWidth("[" + KeyBoardUtil.translate(e.getKey().intValue()) + "]", 6.1f, 0.1f, 0f);
            if (nameW <= maxNameWidth) continue;
            maxNameWidth = nameW;
            if (keyW <= maxKeyWidth) continue;
            maxKeyWidth = keyW;
        }
        gapBetween = 7f;
        float totalWidth = horizontalPadding * 2f + maxNameWidth + gapBetween + maxKeyWidth;
        this.setWidth(totalWidth);
        this.setHeight(titlePadding + titleHeight + dividerGap + dividerHeight + verticalPadding + ((Float)height.get()).floatValue() + verticalPadding);
        OverlayRenderer.rect(context, this.getX().floatValue(), this.getY().floatValue(), this.getWidth().floatValue(), this.getHeight().floatValue());
        ((BuiltRectangle)Api.rectangle().color(new QuadColorState(new Color(147376328, true))).size(new SizeState(this.getWidth().floatValue(), Api.inter().getHeight("Keybinds", 7.4f) + 13.5f)).radius(new QuadRadiusState(7f)).build()).render(context.method_51448().method_23760().method_23761(), this.getX().floatValue(), this.getY().floatValue());
        ((BuiltText)Api.text().font(Api.inter()).text("Keybinds").color(-1).size(7.4f).thickness(0.2f).build()).render(context.method_51448().method_23760().method_23761(), this.getX().floatValue() + horizontalPadding, this.getY().floatValue() + titlePadding);
        float lineY = this.getY().floatValue() + titlePadding + titleHeight + dividerGap;
        float lineWidth = this.getWidth().floatValue() - horizontalPadding * 2f;
        ((BuiltRectangle)Api.rectangle().radius(new QuadRadiusState(0.25f)).size(new SizeState(lineWidth, dividerHeight)).color(new QuadColorState(new Color(-1996488705, true))).build()).render(context.method_51448().method_23760().method_23761(), this.getX().floatValue() + horizontalPadding, lineY);
        AtomicReference<Float> offset = new AtomicReference(0f);
        Scissors.push(this.getX().floatValue(), this.getY().floatValue(), this.getWidth().floatValue(), this.getHeight().floatValue());
        ((List)modules.get()).forEach(this::lambda$render$5 /* captured: lineY, dividerHeight, verticalPadding, offset, context, horizontalPadding, itemSpacing */);
        Scissors.pop();
    }
}
