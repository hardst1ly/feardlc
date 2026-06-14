/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui;

import fear.dlc.Api;
import fear.dlc.ui.clickGui.ComponentBuilder;
import java.util.ArrayList;
import java.util.List;

public abstract class Component
implements ComponentBuilder,
Api {
    final List<Component> componentList = new ArrayList();
    float x;
    float y;
    float width;
    float height;

    public Component position(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Component size(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean charTyped(char chr, int modifiers) {
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return false;
    }

    public void init() {
    }

    public List<Component> getComponentList() {
        return this.componentList;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}
