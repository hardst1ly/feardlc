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

public final record BuiltShadow
implements IRenderer {
    private final SizeState size;
    private final QuadColorState color;
    private final QuadRadiusState radiusState;
    private final float softness;
    private final float shadowRadius;
    private static final class_10156 SHADOW_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("shadow"), class_290.field_1576, class_10149.field_53930);

    public BuiltShadow(SizeState size, QuadColorState color, QuadRadiusState radiusState, float softness, float shadowRadius) {
        this.size = size;
        this.color = color;
        this.radiusState = radiusState;
        this.softness = softness;
        this.shadowRadius = shadowRadius;
    }

    public void render(Matrix4f matrix, float x, float y, float z) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        float width = this.size.width();
        float height = this.size.height();
        class_5944 shader = RenderSystem.setShader(SHADOW_SHADER_KEY);
        shader.method_34582("Size").method_1255(width + this.shadowRadius * 2f, height + this.shadowRadius * 2f);
        shader.method_34582("Softness").method_1251(this.softness);
        shader.method_34582("Radius").method_35657(this.radiusState.radius1(), this.radiusState.radius2(), this.radiusState.radius3(), this.radiusState.radius4());
        shader.method_34582("color1").method_1253(ColorProvider.colorToArray(this.color.color1()));
        shader.method_34582("color2").method_1253(ColorProvider.colorToArray(this.color.color2()));
        shader.method_34582("color3").method_1253(ColorProvider.colorToArray(this.color.color3()));
        shader.method_34582("color4").method_1253(ColorProvider.colorToArray(this.color.color4()));
        shader.method_34582("ShadowRadius").method_1251(this.shadowRadius);
        class_287 builder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        this.quad(builder, matrix, x - this.shadowRadius, y - this.shadowRadius, z, width + this.shadowRadius * 2f, height + this.shadowRadius * 2f, this.color);
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

    public QuadColorState color() {
        return this.color;
    }

    public QuadRadiusState radiusState() {
        return this.radiusState;
    }

    public float softness() {
        return this.softness;
    }

    public float shadowRadius() {
        return this.shadowRadius;
    }
}
