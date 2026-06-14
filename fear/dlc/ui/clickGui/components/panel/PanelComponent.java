/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.panel;

import fear.dlc.modules.more.Category;
import fear.dlc.ui.clickGui.Component;
import fear.dlc.ui.clickGui.Helper;
import fear.dlc.ui.clickGui.components.BackgroundComponent;
import fear.dlc.ui.clickGui.components.module.ModuleLayerComponent;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.utility.Scissors;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.class_332;
import net.minecraft.class_3532;

public class PanelComponent
extends Component {
    private static final float ROW_PADDING_X = 8f;
    private static final float ROW_GAP = 0f;
    private List<ModuleLayerComponent> componentsList = new ArrayList();
    private final BackgroundComponent backgroundComponent;
    private float scroll;
    private float animationScroll = 0f;
    private final Category category;

    public PanelComponent(Category category) {
        this.category = category;
        this.backgroundComponent = new BackgroundComponent(category);
        this.init();
    }

    public void init() {
        this.componentsList = Helper.moduleLayers(this.category, PanelComponent::lambda$init$0);
    }

    public PanelComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        this.animationScroll = class_3532.method_16439(0.02f, this.animationScroll, this.scroll);
        this.backgroundComponent.position(this.getX(), this.getY()).size(this.getWidth(), this.getHeight()).render(context, mouseX, mouseY, delta);
        float headerHeight = 26f;
        float listX = this.getX() + 8f;
        float listY = this.getY() + headerHeight + 4f;
        float listWidth = this.getWidth() - 16f;
        float listHeight = this.getHeight() - headerHeight - 8f;
        Scissors.push(this.getX() + 1f, listY, this.getWidth() - 2f, listHeight);
        AtomicReference<Float> offset = new AtomicReference(0f);
        this.componentsList.forEach(this::lambda$render$1 /* captured: listX, listY, offset, listWidth, context, mouseX, mouseY, delta */);
        Scissors.pop();
        this.scroll = Math.clamp(this.scroll, Math.min(listHeight - ((Float)offset.get()).floatValue(), 0f), 0f);
        return null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
            this.componentsList.forEach(PanelComponent::lambda$mouseClicked$2 /* captured: mouseX, mouseY, button */);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.componentsList.forEach(PanelComponent::lambda$mouseReleased$3 /* captured: mouseX, mouseY, button */);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
            this.scroll = ((double)this.scroll + verticalAmount * 12);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.componentsList.forEach(PanelComponent::lambda$keyPressed$4 /* captured: keyCode, scanCode, modifiers */);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.componentsList.forEach(PanelComponent::lambda$keyReleased$5 /* captured: keyCode, scanCode, modifiers */);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    public boolean charTyped(char chr, int modifiers) {
        this.componentsList.forEach(PanelComponent::lambda$charTyped$6 /* captured: chr, modifiers */);
        return super.charTyped(chr, modifiers);
    }
}
