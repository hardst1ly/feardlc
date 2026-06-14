/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.module;

import fear.dlc.Api;
import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.ModuleEvent;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.ui.clickGui.Component;
import fear.dlc.ui.clickGui.ComponentBuilder;
import fear.dlc.ui.clickGui.Helper;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.keyboard.KeyBoardUtil;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.class_332;

public class ModuleLayerComponent
extends Component {
    private static final float ROW_HEIGHT = 18f;
    private static final float ROW_INNER_PADDING = 8f;
    private final ModuleLayer moduleLayer;
    private final List<SettingComponent> components = new ArrayList();

    public ModuleLayerComponent(ModuleLayer moduleLayer) {
        this.moduleLayer = moduleLayer;
        this.components.addAll(Helper.settingComponents(moduleLayer));
    }

    public float getAnimatedHeight() {
        float expandAnimation = this.moduleLayer.getExpandAnimation().getOutput().floatValue();
        float baseHeight = 18f;
        float topPadding = 6f;
        if (expandAnimation > 0.01f) {
            float settingsHeight = ((Float)this.components.stream().filter(ModuleLayerComponent::lambda$getAnimatedHeight$0).map(ModuleLayerComponent::lambda$getAnimatedHeight$1).reduce(0f, Float::sum)).floatValue();
            return baseHeight + (topPadding + settingsHeight + 4f) * expandAnimation;
        }
        return baseHeight;
    }

    private boolean hasVisibleSettings() {
        return this.components.stream().anyMatch(ModuleLayerComponent::lambda$hasVisibleSettings$2);
    }

    public ComponentBuilder render(class_332 context, int mouseX, int mouseY, float delta) {
        float toggleAnim = this.moduleLayer.getAnimation().getOutput().floatValue();
        float expandAnim = this.moduleLayer.getExpandAnimation().getOutput().floatValue();
        boolean hovered = Math.isHover((double)mouseX, (double)mouseY, this.getX(), this.getY(), this.getWidth(), 18f);
        if (hovered) {
            ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), 18f)).radius(new QuadRadiusState(3f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 6))).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        }
        int baseAlpha = (55f + 45f * toggleAnim);
        int textColor = ColorUtility.applyOpacity(-1, baseAlpha);
        String text = this.moduleLayer.getBinding().booleanValue() ? this.moduleLayer.getKey().intValue() != -1 ? "[" + KeyBoardUtil.translate(this.moduleLayer.getKey().intValue()) + "]" : "Press any key" : this.moduleLayer.getModuleName().getString();
        float fontSize = 8f;
        float textHeight = Api.inter().getHeight(text, fontSize);
        ((BuiltText)Api.text().size(fontSize).font(Api.inter()).color(textColor).text(text).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + 8f, this.getY() + (18f - textHeight) / 2f);
        float iconSize = 7f;
        ((BuiltText)Api.text().text("B").size(iconSize).font(Api.icons()).color(ColorUtility.applyOpacity(-1, (int)(100f * toggleAnim))).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + this.getWidth() - 8f - iconSize - 5f * toggleAnim, this.getY() + (18f - iconSize) / 2f);
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), 0.5f)).radius(new QuadRadiusState(0f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 4))).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() + 18f - 0.5f);
        if (expandAnim > 0.01f) {
            float topPadding = 6f;
            AtomicReference<Float> offset = new AtomicReference(topPadding);
            this.components.stream().filter(ModuleLayerComponent::lambda$render$3).forEach(this::lambda$render$4 /* captured: offset, expandAnim, context, mouseX, mouseY, delta */);
        }
        return null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.moduleLayer.getBinding().booleanValue()) {
            this.moduleLayer.setKey(button);
            this.moduleLayer.setBinding(false);
            return true;
        }
        if (this.moduleLayer.getExpanded().booleanValue()) {
            for (SettingComponent component : this.components) {
                if (((Boolean)component.getSettingLayer().getVisible().get()).booleanValue()) {
                    if (!component.mouseClicked(mouseX, mouseY, button)) continue;
                    return true;
                }
            }
        }
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), 18f)) {
            if (button == 0) {
                EventManager.call(new ModuleEvent.ToggleEvent(this.moduleLayer));
                return true;
            }
        }
        if (button == 1) {
            if (this.hasVisibleSettings()) {
                this.moduleLayer.toggleExpanded();
            }
            return true;
        }
        if (button == 2) {
            this.moduleLayer.setBinding(!this.moduleLayer.getBinding().booleanValue());
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.components.forEach(ModuleLayerComponent::lambda$mouseReleased$5 /* captured: mouseX, mouseY, button */);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.moduleLayer.getBinding().booleanValue()) return super.keyPressed(keyCode, scanCode, modifiers);
        if (keyCode != 261) {
            this.moduleLayer.setKey(keyCode == 256 ? -1 : keyCode);
            this.moduleLayer.setBinding(false);
            return true;
        }
        this.components.forEach(ModuleLayerComponent::lambda$keyPressed$6 /* captured: keyCode, scanCode, modifiers */);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.components.forEach(ModuleLayerComponent::lambda$keyReleased$7 /* captured: keyCode, scanCode, modifiers */);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    public boolean charTyped(char chr, int modifiers) {
        this.components.forEach(ModuleLayerComponent::lambda$charTyped$8 /* captured: chr, modifiers */);
        return super.charTyped(chr, modifiers);
    }

    public ModuleLayer getModuleLayer() {
        return this.moduleLayer;
    }

    public List<SettingComponent> getComponents() {
        return this.components;
    }
}
