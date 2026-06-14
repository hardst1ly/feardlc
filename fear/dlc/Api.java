/*
 * Decompiled with https://jar.tools
 */
package fear.dlc;

import fear.dlc.utility.render.builders.Builder;
import fear.dlc.utility.render.builders.impl.BlurBuilder;
import fear.dlc.utility.render.builders.impl.BorderBuilder;
import fear.dlc.utility.render.builders.impl.RectangleBuilder;
import fear.dlc.utility.render.builders.impl.ShadowBuilder;
import fear.dlc.utility.render.builders.impl.TextBuilder;
import fear.dlc.utility.render.builders.impl.TextureBuilder;
import fear.dlc.utility.render.msdf.MsdfFont;
import java.awt.Color;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_310;
import net.minecraft.class_5250;

public interface Api {
    public static final class_310 mc = class_310.method_1551();

    default public void print(Object o) {
        if (mc == null) {
            return;
        }
        mc.field_1705.method_1743().method_1812(class_2561.method_30163(o.toString()));
    }

    default public void printWithPrefix(String message) {
        class_310 client = class_310.method_1551();
        if (client == null || client.field_1705 == null) {
            return;
        }
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470(message).method_10862(class_2583.field_24360.method_36139(-1));
        client.field_1705.method_1743().method_1812(prefix.method_10852(msg));
    }

    default public void printSuccess(String message) {
        class_310 client = class_310.method_1551();
        if (client == null || client.field_1705 == null) {
            return;
        }
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470(message).method_10862(class_2583.field_24360.method_36139(-11141291));
        client.field_1705.method_1743().method_1812(prefix.method_10852(msg));
    }

    default public void printError(String message) {
        class_310 client = class_310.method_1551();
        if (client == null || client.field_1705 == null) {
            return;
        }
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470(message).method_10862(class_2583.field_24360.method_36139(-43691));
        client.field_1705.method_1743().method_1812(prefix.method_10852(msg));
    }

    default public void printWarning(String message) {
        class_310 client = class_310.method_1551();
        if (client == null || client.field_1705 == null) {
            return;
        }
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470(message).method_10862(class_2583.field_24360.method_36139(-22016));
        client.field_1705.method_1743().method_1812(prefix.method_10852(msg));
    }

    default public void printInfo(String message) {
        class_310 client = class_310.method_1551();
        if (client == null || client.field_1705 == null) {
            return;
        }
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470(message).method_10862(class_2583.field_24360.method_36139(-11162881));
        client.field_1705.method_1743().method_1812(prefix.method_10852(msg));
    }

    default public int getGradientColor(int offset) {
        long time = System.currentTimeMillis() + (long)(offset * 100);
        float hue = (time % 3000L) / 3000f;
        return Color.HSBtoRGB(hue, 0.8f, 1f);
    }

    public static RectangleBuilder rectangle() {
        return Builder.RECTANGLE_BUILDER;
    }

    public static BorderBuilder border() {
        return Builder.BORDER_BUILDER;
    }

    public static TextureBuilder texture() {
        return Builder.TEXTURE_BUILDER;
    }

    public static TextBuilder text() {
        return Builder.TEXT_BUILDER;
    }

    public static BlurBuilder blur() {
        return Builder.BLUR_BUILDER;
    }

    public static ShadowBuilder shadow() {
        return Builder.SHADOW_BUILDER;
    }

    public static MsdfFont inter() {
        return Builder.INTER.get();
    }

    public static MsdfFont icons() {
        return Builder.ICONS.get();
    }

    public static MsdfFont hudIcons() {
        return Builder.HUD_ICONS.get();
    }
}
