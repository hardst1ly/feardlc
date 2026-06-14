/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.windows;

import fear.dlc.api.animations.Animation;
import fear.dlc.api.animations.implement.DecelerateAnimation;
import fear.dlc.ui.clickGui.ComponentBuilder;

public abstract class WindowLayer
implements ComponentBuilder {
    private float x;
    private float y;
    private float width;
    private float height;
    private Animation animation = new DecelerateAnimation().setMs(250).setValue(1);

    public WindowLayer position(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public WindowLayer size(float width, float height) {
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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
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

    public Animation getAnimation() {
        return this.animation;
    }
}
