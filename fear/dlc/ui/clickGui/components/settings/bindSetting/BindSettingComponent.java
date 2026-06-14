/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.bindSetting;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.api.animations.Animation;
import fear.dlc.api.animations.Direction;
import fear.dlc.api.animations.implement.DecelerateAnimation;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.BindSetting;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.keyboard.KeyBoardUtil;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBorder;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.util.function.Supplier;
import net.minecraft.class_332;

public class BindSettingComponent
extends SettingComponent {
    Supplier<BindSetting> bindSetting;
    Supplier<Float> valueWidth;
    Supplier<String> descriptionText;
    private final Animation hoverAnimation;
    private final Animation pressAnimation;
    private final Animation bindingAnimation;
    private boolean isHovered;
    private boolean isPressed;

    public BindSettingComponent(SettingLayer settingLayer) {
        super(settingLayer);
        this.bindSetting = Suppliers.memoize(this::lambda$new$0);
        this.valueWidth = this::lambda$new$1;
        this.descriptionText = Suppliers.memoize(this::lambda$new$2);
        this.hoverAnimation = new DecelerateAnimation().setMs(200).setValue(0);
        this.pressAnimation = new DecelerateAnimation().setMs(150).setValue(0);
        this.bindingAnimation = new DecelerateAnimation().setMs(300).setValue(0);
        this.isHovered = false;
        this.isPressed = false;
    }

    public void init() {
        float moduleNameHeight = Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f);
        float descriptionHeight = Api.inter().getHeight((String)this.descriptionText.get(), 6f);
        this.size(110f, moduleNameHeight + 5f + descriptionHeight);
    }

    public BindSettingComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        float animation = this.getSettingLayer().getAnimation().getOutput().floatValue();
        float bindX = this.getX() + this.getWidth() - ((Float)this.valueWidth.get()).floatValue();
        float bindY = this.getY();
        float bindWidth = ((Float)this.valueWidth.get()).floatValue();
        float bindHeight = 9f;
        boolean currentlyHovered = Math.isHover((double)mouseX, (double)mouseY, bindX, bindY, bindWidth, bindHeight);
        if (currentlyHovered == this.isHovered) return null;
        this.isHovered = currentlyHovered;
        this.hoverAnimation.setDirection(this.isHovered ? Direction.FORWARDS : Direction.BACKWARDS);
        this.bindingAnimation.setDirection(((BindSetting)this.bindSetting.get()).getSelected().booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        float hoverValue = this.hoverAnimation.getOutput().floatValue();
        float pressValue = this.pressAnimation.getOutput().floatValue();
        float bindingValue = this.bindingAnimation.getOutput().floatValue();
        ((BuiltText)Api.text().size(7f).color(ColorUtility.applyOpacity(-1, 95)).text(this.getSettingLayer().getName().getString()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() - 1f);
        if (!((String)this.descriptionText.get()).isEmpty()) {
            ((BuiltText)Api.text().size(6f).color(ColorUtility.applyOpacity(-1, 50)).text((String)this.descriptionText.get()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() + Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f) + 4f);
        }
        if (hoverValue > 0.01f) {
            ((BuiltRectangle)Api.rectangle().size(new SizeState(bindWidth + 2f, bindHeight + 2f)).radius(new QuadRadiusState(3f)).color(new QuadColorState(ColorUtility.applyOpacity(-16777216, (int)(30f * hoverValue)))).build()).render(context.method_51448().method_23760().method_23761(), bindX - 1f, bindY - 1f);
        }
        int baseOpacity = (25f + 25f * animation);
        int hoverOpacity = (15f * hoverValue);
        int pressOpacity = (20f * pressValue);
        int bindingOpacity = (40f * bindingValue);
        ((BuiltRectangle)Api.rectangle().size(new SizeState(bindWidth, bindHeight)).radius(new QuadRadiusState(2f)).color(new QuadColorState(ColorUtility.applyOpacity(((BindSetting)this.bindSetting.get()).getSelected().booleanValue() ? -11890462 : -14013910, baseOpacity + hoverOpacity + bindingOpacity - pressOpacity))).build()).render(context.method_51448().method_23760().method_23761(), bindX, bindY);
        ((BuiltBorder)Api.border().size(new SizeState(bindWidth, bindHeight)).radius(new QuadRadiusState(2f)).color(new QuadColorState(ColorUtility.applyOpacity(((BindSetting)this.bindSetting.get()).getSelected().booleanValue() ? -9718017 : -1, (int)(25f + 25f * animation + 30f * hoverValue + 50f * bindingValue)))).thickness(-1f).build()).render(context.method_51448().method_23760().method_23761(), bindX, bindY);
        String bindText = ((BindSetting)this.bindSetting.get()).getSelected().booleanValue() ? ((BindSetting)this.bindSetting.get()).getKey().intValue() == -1 ? "Press key..." : "Press key..." : KeyBoardUtil.translate(((BindSetting)this.bindSetting.get()).getKey().intValue());
        int textColor = ((BindSetting)this.bindSetting.get()).getSelected().booleanValue() ? ColorUtility.applyOpacity(-1, (int)(200 + 55 * Math.sin((double)System.currentTimeMillis() * 0.005))) : -1;
        ((BuiltText)Api.text().size(6f).color(textColor).text(bindText).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), bindX + 5f, bindY + 1f);
        return null;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!((BindSetting)this.bindSetting.get()).getSelected().booleanValue()) {
            return false;
        }
        if (keyCode == 261) {
            ((BindSetting)this.bindSetting.get()).set(-1);
        } else {
            ((BindSetting)this.bindSetting.get()).set(keyCode);
        }
        ((BindSetting)this.bindSetting.get()).setSelected(false);
        return true;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float bindX = this.getX() + this.getWidth() - ((Float)this.valueWidth.get()).floatValue();
        float bindY = this.getY();
        float bindWidth = ((Float)this.valueWidth.get()).floatValue();
        float bindHeight = 9f;
        if (Math.isHover(mouseX, mouseY, bindX, bindY, bindWidth, bindHeight)) {
            if (button == 0) {
                ((BindSetting)this.bindSetting.get()).setSelected(!((BindSetting)this.bindSetting.get()).getSelected().booleanValue());
                this.isPressed = true;
                this.pressAnimation.setDirection(Direction.FORWARDS);
                this.pressAnimation.reset();
                new Thread(this::lambda$mouseClicked$3).start();
            } else {
                if (((BindSetting)this.bindSetting.get()).getSelected().booleanValue()) {
                    ((BindSetting)this.bindSetting.get()).set(button);
                    ((BindSetting)this.bindSetting.get()).setSelected(false);
                }
            }
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
