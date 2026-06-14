/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.msdf;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public final class FontData {
    private AtlasData atlas;
    private MetricsData metrics;
    private List<GlyphData> glyphs;
    @SerializedName(value="kerning")
    private List<KerningData> kernings;

    public AtlasData atlas() {
        return this.atlas;
    }

    public MetricsData metrics() {
        return this.metrics;
    }

    public List<GlyphData> glyphs() {
        return this.glyphs;
    }

    public List<KerningData> kernings() {
        return this.kernings;
    }
}
