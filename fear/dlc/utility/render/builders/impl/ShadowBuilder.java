/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.builders.impl;

import fear.dlc.utility.render.builders.AbstractBuilder;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltShadow;

public class ShadowBuilder
extends AbstractBuilder<BuiltShadow> {
    SizeState size;
    QuadColorState color;
    QuadRadiusState radius;
    float shadowRadius;
    float softness;

    public ShadowBuilder size(SizeState size) {
        this.size = size;
        return this;
    }

    public ShadowBuilder color(QuadColorState color) {
        this.color = color;
        return this;
    }

    public ShadowBuilder radius(QuadRadiusState radiusState) {
        this.radius = radiusState;
        return this;
    }

    public ShadowBuilder shadow(float radius) {
        this.shadowRadius = radius;
        return this;
    }

    public ShadowBuilder softness(float softness) {
        this.softness = softness;
        return this;
    }

    protected void reset() {
        this.size = SizeState.NONE;
        this.color = QuadColorState.TRANSPARENT;
        this.radius = QuadRadiusState.NO_ROUND;
        this.softness = 0f;
        this.shadowRadius = 0f;
    }

    protected BuiltShadow _build() {
        return new BuiltShadow(this.size, this.color, this.radius, this.softness, this.shadowRadius);
    }
}
