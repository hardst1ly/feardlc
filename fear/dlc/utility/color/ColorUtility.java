/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.color;

import fear.dlc.utility.render.builders.states.QuadColorState;
import java.nio.ByteBuffer;
import net.minecraft.class_9848;
import org.lwjgl.opengl.GL11;

public class ColorUtility
extends class_9848 {
    public static int applyOpacity(int hex, int percent) {
        return ColorUtility.applyOpacity(hex, 2.55f * (float)Math.min(percent, 100));
    }

    public static int applyOpacity(int hex, float opacity) {
        return class_9848.method_61324((int)((float)class_9848.method_61320(hex) * (opacity / 255f)), class_9848.method_61327(hex), class_9848.method_61329(hex), class_9848.method_61331(hex));
    }

    public static QuadColorState applyOpacity(QuadColorState colorState, int opacity) {
        return new QuadColorState(class_9848.method_61330(opacity, colorState.color1()), class_9848.method_61330(opacity, colorState.color2()), class_9848.method_61330(opacity, colorState.color3()), class_9848.method_61330(opacity, colorState.color4()));
    }

    public static int lerp(float value, int from, int to) {
        return class_9848.method_61319(value, from, to);
    }

    public static int pixelColor(int x, int y) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
        GL11.glReadPixels(x, y, 1, 1, 6408, 5121, byteBuffer);
        return class_9848.method_61323(ColorUtility.method_61327(byteBuffer.get()), ColorUtility.method_61329(byteBuffer.get()), ColorUtility.method_61331(byteBuffer.get()));
    }
}
