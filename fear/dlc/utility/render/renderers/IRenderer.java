/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.renderers;

import fear.dlc.utility.render.builders.states.QuadColorState;
import net.minecraft.class_287;
import org.joml.Matrix4f;

public interface IRenderer {
    public static final Matrix4f DEFAULT_MATRIX = new Matrix4f();

    default public void render(double x, double y) {
        this.render((float)x, (float)y);
    }

    default public void render(float x, float y) {
        this.render(DEFAULT_MATRIX, x, y);
    }

    default public void render(Matrix4f matrix, double x, double y) {
        this.render(matrix, (float)x, (float)y);
    }

    default public void render(Matrix4f matrix, float x, float y) {
        this.render(matrix, x, y, 0f);
    }

    default public void render(double x, double y, double z) {
        this.render((float)x, (float)y, (float)z);
    }

    default public void render(float x, float y, float z) {
        this.render(DEFAULT_MATRIX, x, y, z);
    }

    default public void render(Matrix4f matrix, double x, double y, double z) {
        this.render(matrix, (float)x, (float)y, (float)z);
    }

    default public void quad(class_287 builder, Matrix4f matrix4f, float x, float y, float z, float width, float height, QuadColorState color) {
        builder.method_22918(matrix4f, x, y, z).method_39415(color.color1());
        builder.method_22918(matrix4f, x, y + height, z).method_39415(color.color2());
        builder.method_22918(matrix4f, x + width, y + height, z).method_39415(color.color3());
        builder.method_22918(matrix4f, x + width, y, z).method_39415(color.color4());
    }

    public void render(Matrix4f var1, float var2, float var3, float var4);
}
