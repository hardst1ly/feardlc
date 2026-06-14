/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.renderers.impl;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.providers.ResourceProvider;
import fear.dlc.utility.render.renderers.IRenderer;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_276;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_310;
import net.minecraft.class_5944;
import net.minecraft.class_6367;
import org.joml.Matrix4f;

public final record BuiltBlur
implements IRenderer {
    private final SizeState size;
    private final QuadRadiusState radius;
    private final QuadColorState color;
    private final float Smoothness;
    private final float blurRadius;
    private static final class_10156 BLUR_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("blur"), class_290.field_1576, class_10149.field_53930);
    private static final Supplier<class_6367> TEMP_FBO_SUPPLIER = Suppliers.memoize(BuiltBlur::lambda$static$0);
    private static final class_276 MAIN_FBO = class_310.method_1551().method_1522();

    public BuiltBlur(SizeState size, QuadRadiusState radius, QuadColorState color, float Smoothness, float blurRadius) {
        this.size = size;
        this.radius = radius;
        this.color = color;
        this.Smoothness = Smoothness;
        this.blurRadius = blurRadius;
    }

    public void render(Matrix4f matrix, float x, float y, float z) {
        class_6367 fbo = TEMP_FBO_SUPPLIER.get();
        if (fbo.field_1482 != MAIN_FBO.field_1482 || fbo.field_1481 != MAIN_FBO.field_1481) {
            fbo.method_1234(MAIN_FBO.field_1482, MAIN_FBO.field_1481);
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        fbo.method_1235(false);
        MAIN_FBO.method_1237(fbo.field_1482, fbo.field_1481);
        MAIN_FBO.method_1235(false);
        RenderSystem.setShaderTexture(0, fbo.method_30277());
        float width = this.size.width();
        float height = this.size.height();
        class_5944 shader = RenderSystem.setShader(BLUR_SHADER_KEY);
        shader.method_34582("Size").method_1255(width, height);
        shader.method_34582("Radius").method_35657(this.radius.radius1(), this.radius.radius2(), this.radius.radius3(), this.radius.radius4());
        shader.method_34582("Smoothness").method_1251(this.Smoothness);
        shader.method_34582("BlurRadius").method_1251(this.blurRadius);
        class_287 builder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        builder.method_22918(matrix, x, y, z).method_39415(this.color.color1());
        builder.method_22918(matrix, x, y + height, z).method_39415(this.color.color2());
        builder.method_22918(matrix, x + width, y + height, z).method_39415(this.color.color3());
        builder.method_22918(matrix, x + width, y, z).method_39415(this.color.color4());
        class_286.method_43433(builder.method_60800());
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

    public SizeState size() {
        return this.size;
    }

    public QuadRadiusState radius() {
        return this.radius;
    }

    public QuadColorState color() {
        return this.color;
    }

    public float Smoothness() {
        return this.Smoothness;
    }

    public float blurRadius() {
        return this.blurRadius;
    }
}
