/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.builders.states;

public final record SizeState {
    private final float width;
    private final float height;
    public static final SizeState NONE = new SizeState(0f, 0f);

    public SizeState(double width, double height) {
        this((float)width, (float)height);
    }

    public SizeState(float width, float height) {
        this.width = width;
        this.height = height;
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

    public float width() {
        return this.width;
    }

    public float height() {
        return this.height;
    }
}
