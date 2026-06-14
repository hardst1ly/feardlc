/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

import fear.dlc.Api;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBlur;
import java.awt.Color;
import net.minecraft.class_332;

public class OverlayRenderer {
    public static void rect(class_332 context, float x, float y, float width, float height) {
        ((BuiltBlur)Api.blur().blurRadius(8f).radius(new QuadRadiusState(7f)).size(new SizeState(width, height)).build()).render(context.method_51448().method_23760().method_23761(), x, y);
        ((BuiltBlur)Api.blur().blurRadius(14f).radius(new QuadRadiusState(7f)).size(new SizeState(width, height)).color(new QuadColorState(new Color(-652732392, true), new Color(-652469220, true), new Color(-652929771, true), new Color(-652206048, true))).build()).render(context.method_51448().method_23760().method_23761(), x, y);
    }
}
