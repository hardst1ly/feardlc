/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.menu;

import fear.dlc.Api;
import fear.dlc.api.animations.Animation;
import fear.dlc.api.animations.Direction;
import fear.dlc.api.animations.implement.DecelerateAnimation;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import net.minecraft.class_332;
import org.joml.Matrix4f;

public class CustomMenuButton {
    private final String text;
    private final Runnable action;
    private float x;
    private float y;
    private float width;
    private float height;
    private final Animation hoverAnimation = new DecelerateAnimation().setMs(250).setValue(1);
    private static final int BUTTON_COLOR = -13821123;
    private static final int BUTTON_HOVER_COLOR = -11915925;
    private static final int TEXT_COLOR = -2043649;

    public CustomMenuButton(String text, Runnable action) {
        this.text = text;
        this.action = action;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void render(class_332 context, int mouseX, int mouseY, float delta) {
        Matrix4f matrix = context.method_51448().method_23760().method_23761();
        boolean hovered = Math.isHover((double)mouseX, (double)mouseY, this.x, this.y, this.width, this.height);
        this.hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        float hoverProgress = this.hoverAnimation.getOutput().floatValue();
        int r1 = 45;
        int g1 = 27;
        int b1 = 61;
        int r2 = 74;
        int g2 = 45;
        int b2 = 107;
        int r = ((float)r1 + (float)(r2 - r1) * hoverProgress);
        int g = ((float)g1 + (float)(g2 - g1) * hoverProgress);
        int b = ((float)b1 + (float)(b2 - b1) * hoverProgress);
        int buttonColor = -16777216 | r << 16 | g << 8 | b;
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.width, this.height)).radius(new QuadRadiusState(4f, 4f, 4f, 4f)).color(new QuadColorState(buttonColor)).build()).render(matrix, this.x, this.y);
        if (hoverProgress > 0.01f) {
            ((BuiltRectangle)Api.rectangle().size(new SizeState(this.width, this.height)).radius(new QuadRadiusState(4f, 4f, 4f, 4f)).color(new QuadColorState(ColorUtility.applyOpacity(-7643175, (int)(hoverProgress * 100f)))).build()).render(matrix, this.x - 1f, this.y - 1f);
        }
        float textWidth = Api.inter().getWidth(this.text, 6f);
        float textHeight = Api.inter().getHeight(this.text, 6f);
        ((BuiltText)Api.text().text(this.text).size(6f).font(Api.inter()).color(ColorUtility.applyOpacity(-2043649, (int)(255f * (0.8f + hoverProgress * 0.2f)))).build()).render(matrix, this.x + (this.width - textWidth) / 2f, this.y + (this.height - textHeight) / 2f);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.action.run();
        if (button == 0 && Math.isHover(mouseX, mouseY, this.x, this.y, this.width, this.height)) {
            return true;
        }
        return false;
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
