/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.msdf;

import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.utility.render.msdf.FontData;
import fear.dlc.utility.render.msdf.MsdfFont;
import fear.dlc.utility.render.msdf.MsdfGlyph;
import fear.dlc.utility.render.providers.ResourceProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.class_1044;
import net.minecraft.class_2960;
import net.minecraft.class_310;

public static class MsdfFont.Builder {
    private String name = "?";
    private class_2960 dataIdentifer;
    private class_2960 atlasIdentifier;

    private MsdfFont.Builder() {
    }

    public MsdfFont.Builder name(String name) {
        this.name = name;
        return this;
    }

    public MsdfFont.Builder data(String dataFileName) {
        this.dataIdentifer = class_2960.method_60655("pasxalka", "fonts/" + dataFileName + ".json");
        return this;
    }

    public MsdfFont.Builder atlas(String atlasFileName) {
        this.atlasIdentifier = class_2960.method_60655("pasxalka", "fonts/" + atlasFileName + ".png");
        return this;
    }

    public MsdfFont build() {
        FontData data = ResourceProvider.fromJsonToInstance(this.dataIdentifer, FontData.class);
        class_1044 texture = class_310.method_1551().method_1531().method_4619(this.atlasIdentifier);
        if (data == null) {
            throw new RuntimeException("Failed to read font data file: " + this.dataIdentifer.toString() + "; Are you sure this is json file? Try to check the correctness of its syntax.");
        }
        RenderSystem.recordRenderCall(MsdfFont.Builder::lambda$build$0 /* captured: texture */);
        float aWidth = data.atlas().width();
        float aHeight = data.atlas().height();
        Map<Integer, MsdfGlyph> glyphs = data.glyphs().stream().collect(Collectors.toMap(MsdfFont.Builder::lambda$build$1, MsdfFont.Builder::lambda$build$2 /* captured: aWidth, aHeight */));
        Map<Integer, Map<Integer, Float>> kernings = new HashMap();
        data.kernings().forEach(MsdfFont.Builder::lambda$build$4 /* captured: kernings */);
        return new MsdfFont(this.name, texture, data.atlas(), data.metrics(), glyphs, kernings);
    }
}
