/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.panel;

import fear.dlc.modules.more.Category;
import fear.dlc.ui.clickGui.Component;
import fear.dlc.ui.clickGui.components.panel.PanelComponent;
import fear.dlc.utility.math.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.class_332;

public class PanelsLayer
extends Component {
    private static final float GAP = 6f;
    List<PanelComponent> componentsList = new ArrayList();

    public void init() {
        this.componentsList.clear();
        Objects.requireNonNull(this.componentsList);
        Arrays.stream(Category.values()).map(PanelComponent::new).forEach(this.componentsList::add);
        this.initModules();
    }

    public void initModules() {
        this.componentsList.forEach(PanelComponent::init);
    }

    public PanelsLayer render(class_332 context, int mouseX, int mouseY, float delta) {
        int count = this.componentsList.size();
        if (count == 0) {
            return this;
        }
        float panelWidth = (this.getWidth() - 6f * (float)(count - 1)) / (float)count;
        AtomicReference<Float> offset = new AtomicReference(0f);
        this.componentsList.forEach(this::lambda$render$0 /* captured: offset, panelWidth, context, mouseX, mouseY, delta */);
        return null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
            this.componentsList.forEach(PanelsLayer::lambda$mouseClicked$1 /* captured: mouseX, mouseY, button */);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.componentsList.forEach(PanelsLayer::lambda$mouseReleased$2 /* captured: mouseX, mouseY, button */);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        this.componentsList.forEach(PanelsLayer::lambda$mouseScrolled$3 /* captured: mouseX, mouseY, horizontalAmount, verticalAmount */);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.componentsList.forEach(PanelsLayer::lambda$keyPressed$4 /* captured: keyCode, scanCode, modifiers */);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.componentsList.forEach(PanelsLayer::lambda$keyReleased$5 /* captured: keyCode, scanCode, modifiers */);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    public boolean charTyped(char chr, int modifiers) {
        this.componentsList.forEach(PanelsLayer::lambda$charTyped$6 /* captured: chr, modifiers */);
        return super.charTyped(chr, modifiers);
    }
}
