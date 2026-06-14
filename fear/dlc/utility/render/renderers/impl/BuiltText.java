/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.renderers.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.utility.render.msdf.MsdfFont;
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

public final record BuiltText
implements IRenderer {
    private final MsdfFont font;
    private final String text;
    private final float size;
    private final float thickness;
    private final int color;
    private final float Smoothness;
    private final float spacing;
    private final int outlineColor;
    private final float outlineThickness;
    private static final class_10156 MSDF_FONT_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("msdf_font"), class_290.field_1575, class_10149.field_53930);

    public BuiltText(MsdfFont font, String text, float size, float thickness, int color, float Smoothness, float spacing, int outlineColor, float outlineThickness) {
        this.font = font;
        this.text = text;
        this.size = size;
        this.thickness = thickness;
        this.color = color;
        this.Smoothness = Smoothness;
        this.spacing = spacing;
        this.outlineColor = outlineColor;
        this.outlineThickness = outlineThickness;
    }

    public void render(Matrix4f matrix, float x, float y, float z) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShaderTexture(0, this.font.getTextureId());
        boolean outlineEnabled = this.outlineThickness > 0f;
        class_5944 shader = RenderSystem.setShader(MSDF_FONT_SHADER_KEY);
        shader.method_34582("Range").method_1251(this.font.getAtlas().range());
        shader.method_34582("Thickness").method_1251(this.thickness);
        shader.method_34582("Smoothness").method_1251(this.Smoothness);
        shader.method_34582("Outline").method_35649(outlineEnabled ? 1 : 0);
        if (outlineEnabled) {
            shader.method_34582("OutlineThickness").method_1251(this.outlineThickness);
            float[] outlineComponents = ColorProvider.normalize(this.outlineColor);
            shader.method_34582("OutlineColor").method_35657(outlineComponents[0], outlineComponents[1], outlineComponents[2], outlineComponents[3]);
        }
        if (this.text == null || this.text.isEmpty()) {
            return;
        }
        class_287 builder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
        this.font.applyGlyphs(matrix, builder, this.text, this.size, (this.thickness + this.outlineThickness * 0.5f) * 0.5f * this.size, this.spacing, x, y + this.font.getMetrics().baselineHeight() * this.size, z, this.color);
        try {
            class_286.method_43433(builder.method_60800());
        }
        catch (IllegalStateException e8) {
            RenderSystem.setShaderTexture(0, 0);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            return;
        }
        RenderSystem.setShaderTexture(0, 0);
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

    public MsdfFont font() {
        return this.font;
    }

    public String text() {
        return this.text;
    }

    public float size() {
        return this.size;
    }

    public float thickness() {
        return this.thickness;
    }

    public int color() {
        return this.color;
    }

    public float Smoothness() {
        return this.Smoothness;
    }

    public float spacing() {
        return this.spacing;
    }

    public int outlineColor() {
        return this.outlineColor;
    }

    public float outlineThickness() {
        return this.outlineThickness;
    }
}
