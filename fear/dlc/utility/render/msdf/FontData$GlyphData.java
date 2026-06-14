/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.msdf;

import fear.dlc.utility.render.msdf.FontData;

public static final class FontData.GlyphData {
    private int unicode;
    private float advance;
    private FontData.BoundsData planeBounds;
    private FontData.BoundsData atlasBounds;

    public int unicode() {
        return this.unicode;
    }

    public float advance() {
        return this.advance;
    }

    public FontData.BoundsData planeBounds() {
        return this.planeBounds;
    }

    public FontData.BoundsData atlasBounds() {
        return this.atlasBounds;
    }
}
