/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.msdf;

import fear.dlc.utility.render.msdf.FontData;
import fear.dlc.utility.render.msdf.MsdfGlyph;
import java.util.Arrays;
import java.util.Map;
import net.minecraft.class_1044;
import net.minecraft.class_4588;
import org.joml.Matrix4f;

public final class MsdfFont {
    private final String name;
    private final class_1044 texture;
    private final FontData.AtlasData atlas;
    private final FontData.MetricsData metrics;
    private final Map<Integer, MsdfGlyph> glyphs;
    private final Map<Integer, Map<Integer, Float>> kernings;

    private MsdfFont(String name, class_1044 texture, FontData.AtlasData atlas, FontData.MetricsData metrics, Map<Integer, MsdfGlyph> glyphs, Map<Integer, Map<Integer, Float>> kernings) {
        this.name = name;
        this.texture = texture;
        this.atlas = atlas;
        this.metrics = metrics;
        this.glyphs = glyphs;
        this.kernings = kernings;
    }

    public int getTextureId() {
        return this.texture.method_4624();
    }

    public void applyGlyphs(Matrix4f matrix, class_4588 consumer, String text, float size, float thickness, float spacing, float x, float y, float z, int color) {
        int prevChar = -1;
        float startX = x;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (currentChar == '\n') {
                x = startX;
                y = y + (((MsdfGlyph)this.glyphs.get(prevChar != -1 ? prevChar : 32)).getHeight(size) + 1.5f);
            } else {
                if (currentChar != '&') {
                    if (currentChar != '\u00a7') { /* goto @170; */ }
                }
                if (text.charAt(i + 1) != 108) continue;
                thickness = thickness + 1f;
                i++;
            }
        }
    }

    public float getWidth(String text, float size) {
        return ((Float)Arrays.stream(text.split("\n")).map(this::lambda$getWidth$0 /* captured: size */).max(Float::compareTo).orElse(0f)).floatValue();
    }

    public float getWidth(String text, float size, float thickness, float spacing) {
        return ((Float)Arrays.stream(text.split("\n")).map(this::lambda$getWidth$1 /* captured: size, thickness, spacing */).max(Float::compareTo).orElse(0f)).floatValue();
    }

    public float getWidth(String text, float size, float thickness, float outlineThickness, float spacing) {
        return ((Float)Arrays.stream(text.split("\n")).map(this::lambda$getWidth$2 /* captured: size, thickness, outlineThickness, spacing */).max(Float::compareTo).orElse(0f)).floatValue();
    }

    private float calculateWidth(String text, float size, float thickness, float spacing) {
        int prevChar_ = -1;
        float width = 0f;
        for (int i = 0; i < text.length(); i++) {
            int chr_ = text.charAt(i);
            if (chr_ != 38) {
                if (chr_ != 167) { /* goto @75; */ }
            }
            if (text.charAt(i + 1) != 108) continue;
            thickness = thickness + 1f;
            i++;
        }
        return width;
    }

    public float getHeight(String text, float size) {
        float height = 0f;
        int prevChar = -1;
        for (int i = 0; i < text.length(); i++) {
            int _char = text.charAt(i);
            if (_char != 38 || _char == 167) continue;
            i++;
        }
        return height;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return this.name;
    }

    public FontData.AtlasData getAtlas() {
        return this.atlas;
    }

    public FontData.MetricsData getMetrics() {
        return this.metrics;
    }
}
