/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.math;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_4587;

public class Math
extends org.joml.Math {
    public static float stick(float value, float nearest, float threshold) {
        return Math.abs(value - nearest) <= threshold ? nearest : value;
    }

    public static boolean isHover(double mouseX, double mouseY, float x, float y, float width, float height) {
        return mouseX >= (double)x && mouseX <= (double)(x + width) && mouseY >= (double)y && mouseY <= (double)(y + height);
    }

    public static void scale(class_4587 stack, float x, float y, float scale, Runnable data) {
        stack.method_22903();
        stack.method_46416(x, y, 0f);
        stack.method_22905(scale, scale, 1f);
        stack.method_46416(-x, -y, 0f);
        data.run();
        stack.method_22909();
    }

    public static String lerp(float delta, String from, String to) {
        int step = Math.floor(delta * (float)(from.length() + to.length()));
        return step < from.length() ? from.substring(0, Math.max(0, from.length() - step)) : to.substring(0, Math.min(step - from.length(), to.length()));
    }

    public static Integer random(Integer from, Integer to) {
        return ThreadLocalRandom.current().nextInt(from.intValue(), to.intValue());
    }

    public static Float random(Float from, Float to) {
        return ThreadLocalRandom.current().nextFloat(from.floatValue(), to.floatValue());
    }
}
