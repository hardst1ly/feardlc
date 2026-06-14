/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.builders.states;

import java.awt.Color;

public final record QuadColorState {
    private final int color1;
    private final int color2;
    private final int color3;
    private final int color4;
    public static final QuadColorState TRANSPARENT = new QuadColorState(0, 0, 0, 0);
    public static final QuadColorState WHITE = new QuadColorState(-1, -1, -1, -1);

    public QuadColorState(Color color1, Color color2, Color color3, Color color4) {
        this(color1.getRGB(), color2.getRGB(), color3.getRGB(), color4.getRGB());
    }

    public QuadColorState(Color color) {
        this(color, color, color, color);
    }

    public QuadColorState(int color) {
        this(color, color, color, color);
    }

    public QuadColorState(int color1, int color2, int color3, int color4) {
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
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

    public int color1() {
        return this.color1;
    }

    public int color2() {
        return this.color2;
    }

    public int color3() {
        return this.color3;
    }

    public int color4() {
        return this.color4;
    }
}
