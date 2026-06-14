/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.draggable;

import fear.dlc.Api;
import fear.dlc.api.animations.Animation;
import fear.dlc.api.animations.Direction;
import fear.dlc.api.animations.implement.DecelerateAnimation;
import fear.dlc.modules.settings.SettingLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lombok.NonNull;
import net.minecraft.class_3532;

public abstract class DraggableLayer
implements DraggableLayer,
Api {
    @NonNull
    @NonNull
    private Float x;
    @NonNull
    @NonNull
    private Float y;
    @NonNull
    @NonNull
    private Float width;
    @NonNull
    @NonNull
    private Float height;
    private Float prevX;
    private Float prevY;
    @NonNull
    @NonNull
    private Supplier<Boolean> visible;
    private Boolean dragging = false;
    private Boolean settingOpened = false;
    private Float anchorX = 0.5f;
    private Float anchorY = 0.5f;
    private Float stretchX = 1f;
    private Float stretchY = 1f;
    private Float lastRenderX;
    private Float lastRenderY;
    private final List<SettingLayer> settings = new ArrayList();
    private final Animation animation = new DecelerateAnimation().setMs(250).setValue(1);
    private final Animation settingAnimation = new DecelerateAnimation().setMs(300).setValue(1);

    public void toggleSetting() {
        this.settingOpened = !this.settingOpened.booleanValue();
        this.settingAnimation.setDirection(this.settingOpened.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
    }

    public void position(float x, float y) {
        this.prevX = this.x;
        this.prevY = this.y;
        this.x = x;
        this.y = y;
    }

    public void setGrabAnchor(float mouseX, float mouseY) {
        if (this.width == null || this.height == null || this.width.floatValue() <= 0f || this.height.floatValue() <= 0f) {
            this.anchorX = 0.5f;
            this.anchorY = 0.5f;
            return;
        }
        float ax = (mouseX - this.x.floatValue()) / this.width.floatValue();
        float ay = (mouseY - this.y.floatValue()) / this.height.floatValue();
        this.anchorX = class_3532.method_15363(ax, 0f, 1f);
        this.anchorY = class_3532.method_15363(ay, 0f, 1f);
    }

    public float[] updateJellyStretch() {
        if (this.lastRenderX == null) {
            this.lastRenderX = this.x;
            this.lastRenderY = this.y;
        }
        float dx = this.x.floatValue() - this.lastRenderX.floatValue();
        float dy = this.y.floatValue() - this.lastRenderY.floatValue();
        this.lastRenderX = this.x;
        this.lastRenderY = this.y;
        float velocityScaleX = class_3532.method_15363(dx * 0.012f, -0.18f, 0.18f);
        float velocityScaleY = class_3532.method_15363(dy * 0.012f, -0.18f, 0.18f);
        float targetX = 1f + velocityScaleX;
        float targetY = 1f - velocityScaleX * 0.5f + velocityScaleY;
        float lerpSpeed = this.dragging.booleanValue() ? 0.35f : 0.18f;
        this.stretchX = class_3532.method_16439(lerpSpeed, this.stretchX.floatValue(), targetX);
        this.stretchY = class_3532.method_16439(lerpSpeed, this.stretchY.floatValue(), targetY);
        float[] tmp0 = new float[2];
        tmp0[0] = this.stretchX.floatValue();
        tmp0[1] = this.stretchY.floatValue();
        return tmp0;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean charTyped(char chr, int modifiers) {
        return false;
    }

    public void tick() {
    }

    @NonNull
    @NonNull
    public Float getX() {
        return this.x;
    }

    @NonNull
    @NonNull
    public Float getY() {
        return this.y;
    }

    @NonNull
    @NonNull
    public Float getWidth() {
        return this.width;
    }

    @NonNull
    @NonNull
    public Float getHeight() {
        return this.height;
    }

    public Float getPrevX() {
        return this.prevX;
    }

    public Float getPrevY() {
        return this.prevY;
    }

    @NonNull
    @NonNull
    public Supplier<Boolean> getVisible() {
        return this.visible;
    }

    public Boolean getDragging() {
        return this.dragging;
    }

    public Boolean getSettingOpened() {
        return this.settingOpened;
    }

    public Float getAnchorX() {
        return this.anchorX;
    }

    public Float getAnchorY() {
        return this.anchorY;
    }

    public Float getStretchX() {
        return this.stretchX;
    }

    public Float getStretchY() {
        return this.stretchY;
    }

    public Float getLastRenderX() {
        return this.lastRenderX;
    }

    public Float getLastRenderY() {
        return this.lastRenderY;
    }

    public List<SettingLayer> getSettings() {
        return this.settings;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public Animation getSettingAnimation() {
        return this.settingAnimation;
    }

    public DraggableLayer(@NonNull Float x, @NonNull Float y, @NonNull Float width, @NonNull Float height, @NonNull Supplier<Boolean> visible) {
        if (x == null) {
            throw new NullPointerException("x is marked non-null but is null");
        }
        if (y == null) {
            throw new NullPointerException("y is marked non-null but is null");
        }
        if (width == null) {
            throw new NullPointerException("width is marked non-null but is null");
        }
        if (height == null) {
            throw new NullPointerException("height is marked non-null but is null");
        }
        if (visible == null) {
            throw new NullPointerException("visible is marked non-null but is null");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = visible;
    }

    public void setX(@NonNull Float x) {
        if (x == null) {
            throw new NullPointerException("x is marked non-null but is null");
        }
        this.x = x;
    }

    public void setY(@NonNull Float y) {
        if (y == null) {
            throw new NullPointerException("y is marked non-null but is null");
        }
        this.y = y;
    }

    public void setWidth(@NonNull Float width) {
        if (width == null) {
            throw new NullPointerException("width is marked non-null but is null");
        }
        this.width = width;
    }

    public void setHeight(@NonNull Float height) {
        if (height == null) {
            throw new NullPointerException("height is marked non-null but is null");
        }
        this.height = height;
    }

    public void setPrevX(Float prevX) {
        this.prevX = prevX;
    }

    public void setPrevY(Float prevY) {
        this.prevY = prevY;
    }

    public void setVisible(@NonNull Supplier<Boolean> visible) {
        if (visible == null) {
            throw new NullPointerException("visible is marked non-null but is null");
        }
        this.visible = visible;
    }

    public void setDragging(Boolean dragging) {
        this.dragging = dragging;
    }

    public void setAnchorX(Float anchorX) {
        this.anchorX = anchorX;
    }

    public void setAnchorY(Float anchorY) {
        this.anchorY = anchorY;
    }
}
