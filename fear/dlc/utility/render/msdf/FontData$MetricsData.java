/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.msdf;

public static final class FontData.MetricsData {
    private float lineHeight;
    private float ascender;
    private float descender;

    public float lineHeight() {
        return this.lineHeight;
    }

    public float ascender() {
        return this.ascender;
    }

    public float descender() {
        return this.descender;
    }

    public float baselineHeight() {
        return this.lineHeight + this.descender;
    }
}
