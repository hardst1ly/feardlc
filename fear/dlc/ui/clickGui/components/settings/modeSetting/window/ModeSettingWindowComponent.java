/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.modeSetting.window;

import fear.dlc.Api;
import fear.dlc.FearDCP;
import fear.dlc.api.animations.Direction;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.ui.clickGui.Component;
import fear.dlc.ui.clickGui.components.settings.modeSetting.ModeSettingHelper;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBlur;
import fear.dlc.utility.render.renderers.impl.BuiltBorder;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.windows.WindowLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.class_332;

public class ModeSettingWindowComponent
extends WindowLayer {
    private final ModeSetting modeSetting;
    private final List<Component> components = new ArrayList();

    public ModeSettingWindowComponent(ModeSetting modeSetting) {
        this.modeSetting = modeSetting;
        this.components.addAll(ModeSettingHelper.values(modeSetting));
    }

    public void init() {
        this.size(((Float)this.modeSetting.getValues().stream().map(ModeSettingWindowComponent::lambda$init$0).reduce(0f, Float::max)).floatValue(), (float)this.modeSetting.getValues().size() * 15f);
    }

    public ModeSettingWindowComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        ((BuiltBorder)Api.border().radius(new QuadRadiusState(2f)).size(new SizeState(this.getWidth(), this.getHeight())).color(new QuadColorState(ColorUtility.applyOpacity(-1, 25))).thickness(-0.5f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        ((BuiltBlur)Api.blur().radius(new QuadRadiusState(2f)).size(new SizeState(this.getWidth(), this.getHeight())).blurRadius(8f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        ((BuiltRectangle)Api.rectangle().radius(new QuadRadiusState(2f)).size(new SizeState(this.getWidth(), this.getHeight())).color(new QuadColorState(ColorUtility.applyOpacity(-16777216, 65))).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        AtomicReference<Float> offset = new AtomicReference(0f);
        this.components.forEach(this::lambda$render$1 /* captured: offset, context, mouseX, mouseY, delta */);
        return null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
            this.components.forEach(ModeSettingWindowComponent::lambda$mouseClicked$2 /* captured: mouseX, mouseY, button */);
            return true;
        }
        if (this.getAnimation().getDirection().equals(Direction.BACKWARDS)) {
            return false;
        }
        FearDCP.getInstance().getClickGuiScreen().getWindowRepository().pop(this);
        return true;
    }
}
