/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.builders.impl;

import fear.dlc.utility.render.builders.AbstractBuilder;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;

public final class RectangleBuilder
extends AbstractBuilder<BuiltRectangle> {
    private SizeState size;
    private QuadRadiusState radius;
    private QuadColorState color;
    private float Smoothness;

    public RectangleBuilder size(SizeState size) {
        this.size = size;
        return this;
    }

    public RectangleBuilder radius(QuadRadiusState radius) {
        this.radius = radius;
        return this;
    }

    public RectangleBuilder color(QuadColorState color) {
        this.color = color;
        return this;
    }

    public RectangleBuilder Smoothness(float Smoothness) {
        this.Smoothness = Smoothness;
        return this;
    }

    protected BuiltRectangle _build() {
        return new BuiltRectangle(this.size, this.radius, this.color, this.Smoothness);
    }

    protected void reset() {
        this.size = SizeState.NONE;
        this.radius = QuadRadiusState.NO_ROUND;
        this.color = QuadColorState.TRANSPARENT;
        this.Smoothness = 1f;
    }
}
