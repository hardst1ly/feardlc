/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.renderers.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.providers.ColorProvider;
import fear.dlc.utility.render.providers.ResourceProvider;
import fear.dlc.utility.render.renderers.IRenderer;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_5944;
import org.joml.Matrix4f;

public final record BuiltBorder
implements IRenderer {
    private final SizeState size;
    private final QuadRadiusState radius;
    private final QuadColorState color;
    private final float thickness;
    private final float internalSmoothness;
    private final float externalSmoothness;
    private static final class_10156 BORDER_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("border"), class_290.field_1576, class_10149.field_53930);

    public BuiltBorder(SizeState size, QuadRadiusState radius, QuadColorState color, float thickness, float internalSmoothness, float externalSmoothness) {
        this.size = size;
        this.radius = radius;
        this.color = color;
        this.thickness = thickness;
        this.internalSmoothness = internalSmoothness;
        this.externalSmoothness = externalSmoothness;
    }

    public void render(Matrix4f matrix, float x, float y, float z) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        float width = this.size.width();
        float height = this.size.height();
        class_5944 shader = RenderSystem.setShader(BORDER_SHADER_KEY);
        shader.method_34582("Size").method_1255(width, height);
        shader.method_34582("Radius").method_35657(this.radius.radius1(), this.radius.radius2(), this.radius.radius3(), this.radius.radius4());
        shader.method_34582("color1").method_1253(ColorProvider.colorToArray(this.color.color1()));
        shader.method_34582("color2").method_1253(ColorProvider.colorToArray(this.color.color2()));
        shader.method_34582("color3").method_1253(ColorProvider.colorToArray(this.color.color3()));
        shader.method_34582("color4").method_1253(ColorProvider.colorToArray(this.color.color4()));
        shader.method_34582("Thickness").method_1251(this.thickness);
        shader.method_34582("Smoothness").method_1255(this.internalSmoothness, this.externalSmoothness);
        class_287 builder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        builder.method_22918(matrix, x, y, z).method_39415(this.color.color1());
        builder.method_22918(matrix, x, y + height, z).method_39415(this.color.color2());
        builder.method_22918(matrix, x + width, y + height, z).method_39415(this.color.color3());
        builder.method_22918(matrix, x + width, y, z).method_39415(this.color.color4());
        class_286.method_43433(builder.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    public final String toString() {
        return /* lambda: toString */ this;
    }

    public final int hashCode() {
        return /* lambda: hashCode */ this;
    }

    public final boolean equals(Object o) {
        return /* lambda: equals */ this, o;
    }

    public SizeState size() {
        return this.size;
    }

    public QuadRadiusState radius() {
        return this.radius;
    }

    public QuadColorState color() {
        return this.color;
    }

    public float thickness() {
        return this.thickness;
    }

    public float internalSmoothness() {
        return this.internalSmoothness;
    }

    public float externalSmoothness() {
        return this.externalSmoothness;
    }
}
