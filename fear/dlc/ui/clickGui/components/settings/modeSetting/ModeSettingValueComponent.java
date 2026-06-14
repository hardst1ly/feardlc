/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.modeSetting;

import fear.dlc.Api;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.ui.clickGui.Component;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import net.minecraft.class_332;

public class ModeSettingValueComponent
extends Component {
    private final ModeSetting setting;
    private final String value;

    public ModeSettingValueComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        float animationValue = this.setting.getAnimation().getOutput().floatValue();
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), this.getHeight())).color(new QuadColorState(ColorUtility.applyOpacity(-16777216, this.setting.equals(this.value) ? (int)(60f * animationValue) : 0))).radius(new QuadRadiusState(2f)).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        ((BuiltText)Api.text().font(Api.inter()).size(8f).text(this.value).color(ColorUtility.applyOpacity(-1, (int)(this.setting.equals(this.value) ? 50f + 50f * animationValue : 50f))).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + 5f, this.getY() + Api.inter().getHeight(this.value, 8f) / 4f);
        ((BuiltText)Api.text().text("B").size(7f).font(Api.icons()).color(ColorUtility.applyOpacity(-1, this.setting.equals(this.value) ? (int)(100f * animationValue) : 0)).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + this.getWidth() - 7f - 5f, this.getY() - 0.5f + (this.getHeight() - 7f) / 2f);
        return null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
            this.setting.set(this.value);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public ModeSettingValueComponent(ModeSetting setting, String value) {
        this.setting = setting;
        this.value = value;
    }
}
