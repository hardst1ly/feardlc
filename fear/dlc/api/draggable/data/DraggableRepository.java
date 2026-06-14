/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.draggable.data;

import fear.dlc.Api;
import fear.dlc.api.draggable.DraggableLayer;
import fear.dlc.ui.overlay.KeybindsLayer;
import fear.dlc.ui.overlay.NotificationLayer;
import fear.dlc.ui.overlay.PotionsLayer;
import fear.dlc.ui.overlay.TargetHUDLayer;
import fear.dlc.ui.overlay.WaterMarkLayer;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.MathTime;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBlur;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.class_332;
import net.minecraft.class_3675;
import net.minecraft.class_4587;
import net.minecraft.class_9779;
import org.joml.Matrix4f;

public class DraggableRepository
implements Api {
    List<DraggableLayer> list = new ArrayList();
    MathTime controlClickTime = MathTime.create();
    Supplier<Boolean> altPressed;
    Boolean netEnabled;

    public DraggableRepository() {
        this.altPressed = DraggableRepository::lambda$new$0;
        this.netEnabled = true;
    }

    public List<DraggableLayer> getList() {
        return this.list;
    }

    public void init() {
        this.list.addAll(List.of(new WaterMarkLayer(), new KeybindsLayer(), new TargetHUDLayer(), new NotificationLayer(), new PotionsLayer()));
    }

    public void render(class_332 context, class_9779 tickCounter, double mouseX, double mouseY) {
        this.list.forEach(this::lambda$render$3 /* captured: context, mouseX, mouseY, tickCounter */);
    }

    private void applyAnchoredScale(class_332 context, float anchorX, float anchorY, float scaleX, float scaleY, Runnable body) {
        class_4587 stack = context.method_51448();
        stack.method_22903();
        stack.method_46416(anchorX, anchorY, 0f);
        stack.method_22905(scaleX, scaleY, 1f);
        stack.method_46416(-anchorX, -anchorY, 0f);
        body.run();
        stack.method_22909();
    }

    public void update(class_332 context, float tickDelta, int mouseX, int mouseY) {
        if (this.netEnabled.booleanValue()) {
            this.renderNet(context.method_51448().method_23760().method_23761());
        }
        this.list.stream().filter(DraggableLayer::getDragging).findFirst().ifPresent(this::lambda$update$4 /* captured: mouseX, mouseY, context */);
    }

    void renderNet(Matrix4f matrix4f) {
        float xOffset = 0f;
        float yOffset = 0f;
        ((BuiltBlur)Api.blur().blurRadius(16f).size(new SizeState((float)mc.method_22683().method_4486(), (float)mc.method_22683().method_4502())).color(new QuadColorState(-1)).build()).render(matrix4f, 0f, 0f);
        ((BuiltRectangle)Api.rectangle().color(new QuadColorState(ColorUtility.applyOpacity(-16777216, 127.5f))).size(new SizeState((float)mc.method_22683().method_4486(), (float)mc.method_22683().method_4502())).build()).render(matrix4f, 0f, 0f);
        for (int i = 0; i < mc.method_22683().method_4486() / 10; i++) {
            this.renderLine(matrix4f, xOffset - 0.75f, 0f, 1.5f, (float)mc.method_22683().method_4502(), 85f);
            xOffset = xOffset + (float)mc.method_22683().method_4486() / 10f;
        }
        for (int j = 0; j < mc.method_22683().method_4502() / 10; j++) {
            this.renderLine(matrix4f, 0f, yOffset - 0.75f, (float)mc.method_22683().method_4486(), 1.5f, 85f);
            yOffset = yOffset + (float)mc.method_22683().method_4502() / 10f;
        }
    }

    void renderLine(Matrix4f matrix4f, float x, float y, float width, float height, float opacity) {
        ((BuiltRectangle)Api.rectangle().size(new SizeState(width, height)).color(new QuadColorState(ColorUtility.applyOpacity(-1, opacity))).build()).render(matrix4f, x, y);
    }

    void renderText(class_332 context, String text, float x, float y, float opacity) {
        ((BuiltText)Api.text().color(ColorUtility.applyOpacity(-1, opacity)).text(text).size(8f).font(Api.inter()).outline(-16777216, 0.2f).thickness(0.1f).build()).render(context.method_51448().method_23760().method_23761(), x - 2f - Api.inter().getWidth(text, 8f) / 2f, y - Api.inter().getHeight(text, 8f));
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        this.list.forEach(DraggableRepository::lambda$mouseClicked$5 /* captured: mouseX, mouseY, button */);
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        this.list.forEach(DraggableRepository::lambda$mouseReleased$6 /* captured: button */);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (class_3675.method_15987(mc.method_22683().method_4490(), 341) && keyCode == 342) {
            if (!this.controlClickTime.isReached(30L)) {
                return false;
            }
        }
        if (this.list.stream().filter(DraggableRepository::lambda$keyPressed$7 /* captured: keyCode, scanCode, modifiers */).noneMatch(DraggableRepository::lambda$keyPressed$8 /* captured: keyCode, scanCode, modifiers */)) {
            this.netEnabled = !this.netEnabled.booleanValue();
            this.controlClickTime.resetCounter();
            return true;
        }
        return false;
    }
}
