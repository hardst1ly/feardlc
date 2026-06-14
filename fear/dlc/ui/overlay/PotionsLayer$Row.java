/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

import net.minecraft.class_1058;

private static final record PotionsLayer.Row {
    private final class_1058 sprite;
    private final String name;
    private final String duration;
    private final boolean blinking;

    private PotionsLayer.Row(class_1058 sprite, String name, String duration, boolean blinking) {
        this.sprite = sprite;
        this.name = name;
        this.duration = duration;
        this.blinking = blinking;
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

    public class_1058 sprite() {
        return this.sprite;
    }

    public String name() {
        return this.name;
    }

    public String duration() {
        return this.duration;
    }

    public boolean blinking() {
        return this.blinking;
    }
}
