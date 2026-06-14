/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.providers;

import java.awt.Color;
import net.minecraft.class_9848;

public final class ColorProvider {
    public static int pack(int red, int green, int blue, int alpha) {
        return (alpha & 255) << 24 | (red & 255) << 16 | (green & 255) << 8 | (blue & 255) << 0;
    }

    public static int[] unpack(int color) {
        int[] tmp0 = new int[4];
        tmp0[0] = color >> 16 & 255;
        tmp0[1] = color >> 8 & 255;
        tmp0[2] = color & 255;
        tmp0[3] = color >> 24 & 255;
        return tmp0;
    }

    public static float[] unpackf(int color) {
        float[] tmp0 = new float[4];
        tmp0[0] = (float)(color >> 16 & 255);
        tmp0[1] = (float)(color >> 8 & 255);
        tmp0[2] = (float)(color & 255);
        tmp0[3] = (float)(color >> 24 & 255);
        return tmp0;
    }

    public static float[] colorToArray(int hex) {
        float[] rgba = new float[4];
        rgba[0] = (float)class_9848.method_61327(hex) / 255f;
        rgba[1] = (float)class_9848.method_61329(hex) / 255f;
        rgba[2] = (float)class_9848.method_61331(hex) / 255f;
        rgba[3] = (float)class_9848.method_61320(hex) / 255f;
        return rgba;
    }

    public static float[] normalize(Color color) {
        float[] tmp0 = new float[4];
        tmp0[0] = (float)color.getRed() / 255f;
        tmp0[1] = (float)color.getGreen() / 255f;
        tmp0[2] = (float)color.getBlue() / 255f;
        tmp0[3] = (float)color.getAlpha() / 255f;
        return tmp0;
    }

    public static float[] normalize(int color) {
        int[] components = ColorProvider.unpack(color);
        float[] tmp0 = new float[4];
        tmp0[0] = (float)components[0] / 255f;
        tmp0[1] = (float)components[1] / 255f;
        tmp0[2] = (float)components[2] / 255f;
        tmp0[3] = (float)components[3] / 255f;
        return tmp0;
    }
}
