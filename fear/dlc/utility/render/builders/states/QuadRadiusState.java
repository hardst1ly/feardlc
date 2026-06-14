/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.builders.states;

public final record QuadRadiusState {
    private final float radius1;
    private final float radius2;
    private final float radius3;
    private final float radius4;
    public static final QuadRadiusState NO_ROUND = new QuadRadiusState(0f, 0f, 0f, 0f);

    public QuadRadiusState(double radius1, double radius2, double radius3, double radius4) {
        this((float)radius1, (float)radius2, (float)radius3, (float)radius4);
    }

    public QuadRadiusState(double radius) {
        this(radius, radius, radius, radius);
    }

    public QuadRadiusState(float radius) {
        this(radius, radius, radius, radius);
    }

    public QuadRadiusState(float radius1, float radius2, float radius3, float radius4) {
        this.radius1 = radius1;
        this.radius2 = radius2;
        this.radius3 = radius3;
        this.radius4 = radius4;
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

    public float radius1() {
        return this.radius1;
    }

    public float radius2() {
        return this.radius2;
    }

    public float radius3() {
        return this.radius3;
    }

    public float radius4() {
        return this.radius4;
    }
}
