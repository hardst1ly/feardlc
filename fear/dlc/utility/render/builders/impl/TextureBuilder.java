/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.builders.impl;

import fear.dlc.utility.render.builders.AbstractBuilder;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltTexture;
import net.minecraft.class_1044;

public final class TextureBuilder
extends AbstractBuilder<BuiltTexture> {
    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float Smoothness;
    private float u;
    private float v;
    private float texWidth;
    private float texHeight;
    private int textureId;

    public TextureBuilder size(SizeState size) {
        this.size = size;
        return this;
    }

    public TextureBuilder radius(QuadRadiusState radius) {
        this.radius = radius;
        return this;
    }

    public TextureBuilder color(QuadColorState color) {
        this.color = color;
        return this;
    }

    public TextureBuilder Smoothness(float Smoothness) {
        this.Smoothness = Smoothness;
        return this;
    }

    public TextureBuilder texture(float u, float v, float texWidth, float texHeight, class_1044 texture) {
        return this.texture(u, v, texWidth, texHeight, texture.method_4624());
    }

    public TextureBuilder texture(float u, float v, float texWidth, float texHeight, int textureId) {
        this.u = u;
        this.v = v;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.textureId = textureId;
        return this;
    }

    protected BuiltTexture _build() {
        return new BuiltTexture(this.size, this.radius, this.color, this.Smoothness, this.u, this.v, this.texWidth, this.texHeight, this.textureId);
    }

    protected void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.WHITE;
        this.Smoothness = 1f;
        this.u = 0f;
        this.v = 0f;
        this.texWidth = 0f;
        this.texHeight = 0f;
        this.textureId = 0;
    }
}
