/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.colorSetting;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.modules.settings.impl.ColorSetting;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBorder;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.class_332;
import net.minecraft.class_3532;

public class ColorSettingComponent
extends SettingComponent {
    Supplier<String> descriptionText;
    private boolean draggingHue;
    private boolean draggingSaturation;
    private boolean draggingBrightness;
    private boolean draggingAlpha;
    private boolean expanded;

    public ColorSettingComponent(ColorSetting colorSetting) {
        super(colorSetting);
        this.descriptionText = Suppliers.memoize(this::lambda$new$0);
        this.draggingHue = false;
        this.draggingSaturation = false;
        this.draggingBrightness = false;
        this.draggingAlpha = false;
        this.expanded = false;
    }

    public void init() {
        float moduleNameHeight = Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f);
        float descriptionHeight = Api.inter().getHeight((String)this.descriptionText.get(), 6f);
        float baseHeight = moduleNameHeight + 5f + descriptionHeight + 7.5f + 15f;
        if (this.expanded) {
            baseHeight = baseHeight + 60f;
        }
        this.size(110f, baseHeight);
    }

    public ColorSettingComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        ColorSetting colorSetting = this.getSettingLayer();
        if (this.draggingHue) {
            this.updateHue((double)mouseX);
        }
        if (this.draggingSaturation) {
            this.updateSaturation((double)mouseX);
        }
        if (this.draggingBrightness) {
            this.updateBrightness((double)mouseX);
        }
        if (this.draggingAlpha) {
            this.updateAlpha((double)mouseX);
        }
        float currentY = this.getY();
        ((BuiltText)Api.text().size(7f).color(ColorUtility.applyOpacity(-1, 95)).text(this.getSettingLayer().getName().getString()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), currentY);
        currentY = currentY + (Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f) + 5f);
        if (!((String)this.descriptionText.get()).isEmpty()) {
            ((BuiltText)Api.text().size(6f).color(ColorUtility.applyOpacity(-1, 50)).text((String)this.descriptionText.get()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), currentY);
            currentY = currentY + (Api.inter().getHeight((String)this.descriptionText.get(), 6f) + 5f);
        }
        int currentColor = colorSetting.getColor();
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), 12f)).radius(new QuadRadiusState(3f)).color(new QuadColorState(currentColor)).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), currentY);
        ((BuiltBorder)Api.border().size(new SizeState(this.getWidth(), 12f)).radius(new QuadRadiusState(3f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 50))).thickness(1f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), currentY);
        currentY = currentY + 15f;
        if (this.expanded) {
            this.renderHueSlider(context, currentY);
            currentY = currentY + 15f;
            this.renderSaturationSlider(context, currentY, colorSetting);
            currentY = currentY + 15f;
            this.renderBrightnessSlider(context, currentY, colorSetting);
            currentY = currentY + 15f;
            this.renderAlphaSlider(context, currentY, colorSetting);
        }
        return null;
    }

    private void renderHueSlider(class_332 context, float y) {
        ColorSetting colorSetting = this.getSettingLayer();
        float segmentWidth = this.getWidth() / 6f;
        int[] tmp0 = new int[7];
        tmp0[0] = -65536;
        tmp0[1] = -256;
        tmp0[2] = -16711936;
        tmp0[3] = -16711681;
        tmp0[4] = -16776961;
        tmp0[5] = -65281;
        tmp0[6] = -65536;
        int[] rainbowColors = tmp0;
        for (int i = 0; i < 6; i++) {
            ((BuiltRectangle)Api.rectangle().size(new SizeState(segmentWidth + 1f, 5f)).radius(new QuadRadiusState(0f)).color(new QuadColorState(rainbowColors[i], rainbowColors[i], rainbowColors[i + 1], rainbowColors[i + 1])).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + (float)i * segmentWidth, y);
        }
        float huePosition = this.getWidth() * colorSetting.hsb;
        ((BuiltRectangle)Api.rectangle().size(new SizeState(2f, 5f)).radius(new QuadRadiusState(0f)).color(new QuadColorState(-1)).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + huePosition - 1f, y);
        ((BuiltText)Api.text().font(Api.inter()).text("Hue").color(ColorUtility.applyOpacity(-1, 70)).size(5f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), y + 7f);
    }

    private void renderSaturationSlider(class_332 context, float y, ColorSetting colorSetting) {
        int baseColor = Color.HSBtoRGB(colorSetting.hsb, 1f, colorSetting.brightness);
        int whiteColor = Color.HSBtoRGB(colorSetting.hsb, 0f, colorSetting.brightness);
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), 5f)).radius(new QuadRadiusState(1f)).color(new QuadColorState(whiteColor, whiteColor, baseColor, baseColor)).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), y);
        float satPosition = this.getWidth() * colorSetting.saturation;
        ((BuiltRectangle)Api.rectangle().size(new SizeState(2f, 5f)).radius(new QuadRadiusState(0f)).color(new QuadColorState(-1)).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + satPosition - 1f, y);
        ((BuiltText)Api.text().font(Api.inter()).text("Saturation").color(ColorUtility.applyOpacity(-1, 70)).size(5f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), y + 7f);
    }

    private void renderBrightnessSlider(class_332 context, float y, ColorSetting colorSetting) {
        int baseColor = Color.HSBtoRGB(colorSetting.hsb, colorSetting.saturation, 1f);
        int blackColor = -16777216;
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), 5f)).radius(new QuadRadiusState(1f)).color(new QuadColorState(blackColor, blackColor, baseColor, baseColor)).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), y);
        float brightPosition = this.getWidth() * colorSetting.brightness;
        ((BuiltRectangle)Api.rectangle().size(new SizeState(2f, 5f)).radius(new QuadRadiusState(0f)).color(new QuadColorState(-1)).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + brightPosition - 1f, y);
        ((BuiltText)Api.text().font(Api.inter()).text("Brightness").color(ColorUtility.applyOpacity(-1, 70)).size(5f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), y + 7f);
    }

    private void renderAlphaSlider(class_332 context, float y, ColorSetting colorSetting) {
        int baseColor = colorSetting.getColorRGB();
        int transparentColor = baseColor & 16777215;
        int opaqueColor = baseColor | -16777216;
        for (int i = 0; (float)i < this.getWidth() / 4f; i++) {
            int checkerColor = i % 2 == 0 ? -8355712 : -5197648;
            ((BuiltRectangle)Api.rectangle().size(new SizeState(4f, 5f)).radius(new QuadRadiusState(0f)).color(new QuadColorState(checkerColor)).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + (float)(i * 4), y);
        }
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), 5f)).radius(new QuadRadiusState(1f)).color(new QuadColorState(transparentColor, transparentColor, opaqueColor, opaqueColor)).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), y);
        float alphaPosition = this.getWidth() * colorSetting.alpha;
        ((BuiltRectangle)Api.rectangle().size(new SizeState(2f, 5f)).radius(new QuadRadiusState(0f)).color(new QuadColorState(-1)).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + alphaPosition - 1f, y);
        ((BuiltText)Api.text().font(Api.inter()).text("Alpha").color(ColorUtility.applyOpacity(-1, 70)).size(5f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), y + 7f);
    }

    private void updateHue(double mouseX) {
        ColorSetting colorSetting = this.getSettingLayer();
        float clampedMouseX = class_3532.method_15350(mouseX, (double)this.getX(), (double)(this.getX() + this.getWidth()));
        float newHue = (clampedMouseX - this.getX()) / this.getWidth();
        colorSetting.set(newHue, colorSetting.saturation, colorSetting.brightness, colorSetting.alpha);
    }

    private void updateSaturation(double mouseX) {
        ColorSetting colorSetting = this.getSettingLayer();
        float clampedMouseX = class_3532.method_15350(mouseX, (double)this.getX(), (double)(this.getX() + this.getWidth()));
        float newSat = (clampedMouseX - this.getX()) / this.getWidth();
        colorSetting.set(colorSetting.hsb, newSat, colorSetting.brightness, colorSetting.alpha);
    }

    private void updateBrightness(double mouseX) {
        ColorSetting colorSetting = this.getSettingLayer();
        float clampedMouseX = class_3532.method_15350(mouseX, (double)this.getX(), (double)(this.getX() + this.getWidth()));
        float newBright = (clampedMouseX - this.getX()) / this.getWidth();
        colorSetting.set(colorSetting.hsb, colorSetting.saturation, newBright, colorSetting.alpha);
    }

    private void updateAlpha(double mouseX) {
        ColorSetting colorSetting = this.getSettingLayer();
        float clampedMouseX = class_3532.method_15350(mouseX, (double)this.getX(), (double)(this.getX() + this.getWidth()));
        float newAlpha = (clampedMouseX - this.getX()) / this.getWidth();
        colorSetting.set(colorSetting.hsb, colorSetting.saturation, colorSetting.brightness, newAlpha);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight())) return false;
        float currentY = this.getY();
        currentY = currentY + (Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f) + 5f);
        if (!((String)this.descriptionText.get()).isEmpty()) {
            currentY = currentY + (Api.inter().getHeight((String)this.descriptionText.get(), 6f) + 5f);
        }
        if (Math.isHover(mouseX, mouseY, this.getX(), currentY, this.getWidth(), 12f)) {
            this.expanded = !this.expanded;
            this.init();
            return true;
        }
        currentY = currentY + 15f;
        if (this.expanded) {
            if (Math.isHover(mouseX, mouseY, this.getX(), currentY, this.getWidth(), 5f)) {
                this.draggingHue = true;
                this.updateHue(mouseX);
                return true;
            }
            currentY = currentY + 15f;
            if (Math.isHover(mouseX, mouseY, this.getX(), currentY, this.getWidth(), 5f)) {
                this.draggingSaturation = true;
                this.updateSaturation(mouseX);
                return true;
            }
            currentY = currentY + 15f;
            if (Math.isHover(mouseX, mouseY, this.getX(), currentY, this.getWidth(), 5f)) {
                this.draggingBrightness = true;
                this.updateBrightness(mouseX);
                return true;
            }
            currentY = currentY + 15f;
            if (Math.isHover(mouseX, mouseY, this.getX(), currentY, this.getWidth(), 5f)) {
                this.draggingAlpha = true;
                this.updateAlpha(mouseX);
                return true;
            }
            return true;
        }
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.draggingHue = false;
        this.draggingSaturation = false;
        this.draggingBrightness = false;
        this.draggingAlpha = false;
        return false;
    }
}
