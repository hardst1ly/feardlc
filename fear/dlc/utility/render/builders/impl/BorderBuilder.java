/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.builders.impl;

import fear.dlc.utility.render.builders.AbstractBuilder;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBorder;

public final class BorderBuilder
extends AbstractBuilder<BuiltBorder> {
    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float thickness;
    private float internalSmoothness;
    private float externalSmoothness;

    public BorderBuilder size(SizeState size) {
        this.size = size;
        return this;
    }

    public BorderBuilder radius(QuadRadiusState radius) {
        this.radius = radius;
        return this;
    }

    public BorderBuilder color(QuadColorState color) {
        this.color = color;
        return this;
    }

    public BorderBuilder thickness(float thickness) {
        this.thickness = thickness;
        return this;
    }

    public BorderBuilder Smoothness(float internalSmoothness, float externalSmoothness) {
        this.internalSmoothness = internalSmoothness;
        this.externalSmoothness = externalSmoothness;
        return this;
    }

    protected BuiltBorder _build() {
        return new BuiltBorder(this.size, this.radius, this.color, this.thickness, this.internalSmoothness, this.externalSmoothness);
    }

    protected void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.TRANSPARENT;
        this.thickness = 0f;
        this.internalSmoothness = 1f;
        this.externalSmoothness = 1f;
    }
}
