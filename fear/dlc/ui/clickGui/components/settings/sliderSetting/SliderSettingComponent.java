/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.sliderSetting;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBorder;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.class_332;
import net.minecraft.class_3532;

public class SliderSettingComponent
extends SettingComponent {
    Supplier<String> descriptionText;

    public SliderSettingComponent(SliderSetting sliderSetting) {
        super(sliderSetting);
        this.descriptionText = Suppliers.memoize(this::lambda$new$0);
    }

    public void init() {
        float moduleNameHeight = Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f);
        float descriptionHeight = Api.inter().getHeight((String)this.descriptionText.get(), 6f);
        this.size(110f, moduleNameHeight + 5f + descriptionHeight + 7.5f);
    }

    public SliderSettingComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        SliderSetting sliderSetting = this.getSettingLayer();
        if (sliderSetting.getDragging().booleanValue()) {
            this.update((double)mouseX);
        }
        ((BuiltText)Api.text().size(7f).color(ColorUtility.applyOpacity(-1, 95)).text(this.getSettingLayer().getName().getString()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        if (Objects.nonNull(sliderSetting.getValue())) {
            Object[] tmp0 = new Object[1];
            tmp0[0] = sliderSetting.getValue();
            String valueString = String.format("%.1f", tmp0);
            float valueWidth = 10f + Api.inter().getWidth(valueString, 6f);
            ((BuiltBorder)Api.border().size(new SizeState(valueWidth, 9f)).radius(new QuadRadiusState(2f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 25))).thickness(-1f).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + this.getWidth() - valueWidth, this.getY());
            ((BuiltText)Api.text().font(Api.inter()).text(valueString).color(-1).size(6f).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + this.getWidth() - valueWidth + 5f, this.getY() + 0.5f);
        }
        if (!((String)this.descriptionText.get()).isEmpty()) {
            ((BuiltText)Api.text().size(6f).color(ColorUtility.applyOpacity(-1, 50)).text((String)this.descriptionText.get()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() + Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f) + 5f);
        }
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), 5f)).radius(new QuadRadiusState(1f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 25))).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() + this.getHeight() - 5f);
        if (Objects.nonNull(sliderSetting.getValue())) {
            float sliderWidth = this.getWidth() * ((sliderSetting.getValue().floatValue() - sliderSetting.getMin().floatValue()) / (sliderSetting.getMax().floatValue() - sliderSetting.getMin().floatValue()));
            ((BuiltRectangle)Api.rectangle().size(new SizeState(sliderWidth, 5f)).radius(new QuadRadiusState(1f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 100))).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() + this.getHeight() - 5f);
        }
        return null;
    }

    void update(double mouseX) {
        SliderSetting sliderSetting = this.getSettingLayer();
        float clampedMouseX = class_3532.method_15350(mouseX, (double)this.getX(), (double)(this.getX() + this.getWidth()));
        float newValue = sliderSetting.getMin().floatValue() + (clampedMouseX - this.getX()) / (this.getX() + this.getWidth() - this.getX()) * (sliderSetting.getMax().floatValue() - sliderSetting.getMin().floatValue());
        newValue = Math.round(newValue / sliderSetting.getIncrements().floatValue()) * sliderSetting.getIncrements().floatValue();
        newValue = Math.max(sliderSetting.getMin().floatValue(), Math.min(sliderSetting.getMax().floatValue(), newValue));
        sliderSetting.set(newValue);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
            if (Math.isHover(mouseX, mouseY, this.getX(), this.getY() + this.getHeight() - 5f, this.getWidth(), 5f)) {
                SliderSetting sliderSetting = this.getSettingLayer();
                sliderSetting.setDragging(true);
            }
            return true;
        }
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        SliderSetting sliderSetting = this.getSettingLayer();
        sliderSetting.setDragging(false);
        return false;
    }
}
