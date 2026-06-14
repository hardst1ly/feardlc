/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.windows;

import fear.dlc.api.animations.Direction;
import fear.dlc.utility.windows.WindowLayer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_332;

public class WindowRepository {
    List<WindowLayer> windowLayerList = new ArrayList();

    public void push(WindowLayer windowLayer) {
        this.windowLayerList.add(windowLayer);
        windowLayer.getAnimation().setDirection(Direction.FORWARDS);
        windowLayer.getAnimation().reset();
    }

    public void pop(WindowLayer windowLayer) {
        if (windowLayer.getAnimation().getDirection().equals(Direction.BACKWARDS)) {
            return;
        }
        windowLayer.getAnimation().setDirection(Direction.BACKWARDS);
        windowLayer.getAnimation().reset();
    }

    public void close() {
        this.windowLayerList.forEach(this::pop);
    }

    public boolean contains(WindowLayer windowLayer) {
        return this.windowLayerList.contains(windowLayer);
    }

    public void render(class_332 context, int mouseX, int mouseY, float delta) {
        this.windowLayerList.removeIf(WindowRepository::lambda$render$0);
        this.windowLayerList.forEach(WindowRepository::lambda$render$2 /* captured: context, mouseX, mouseY, delta */);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.windowLayerList.stream().anyMatch(WindowRepository::lambda$mouseClicked$3 /* captured: mouseX, mouseY, button */);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return this.windowLayerList.stream().anyMatch(WindowRepository::lambda$mouseReleased$4 /* captured: mouseX, mouseY, button */);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.windowLayerList.stream().anyMatch(WindowRepository::lambda$keyPressed$5 /* captured: keyCode, scanCode, modifiers */);
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return this.windowLayerList.stream().anyMatch(WindowRepository::lambda$keyReleased$6 /* captured: keyCode, scanCode, modifiers */);
    }

    public boolean charTyped(char chr, int modifiers) {
        return this.windowLayerList.stream().anyMatch(WindowRepository::lambda$charTyped$7 /* captured: chr, modifiers */);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.windowLayerList.stream().anyMatch(WindowRepository::lambda$mouseScrolled$8 /* captured: mouseX, mouseY, horizontalAmount, verticalAmount */);
    }
}
