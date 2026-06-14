/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.math;

import fear.dlc.utility.math.Math;
import net.minecraft.class_310;

public class RotationUtils {
    private static final class_310 mc = class_310.method_1551();

    public static double getMouseGCD() {
        double d0 = ((Double)mc.field_1690.method_42495().method_41753()).doubleValue();
        double d1 = d0 * 0.6 + 0.2;
        return d1 * d1 * d1 * 1.2;
    }

    public static double applyGCD(double d0, double d1) {
        return d1 <= 0.0001 ? d0 : d0 - d0 % d1;
    }

    public static double wrapDegrees(double d0) {
        d0 = d0 % 360;
        if (d0 > 180) {
            d0 = d0 - 360;
        }
        if (d0 < -180) {
            d0 = d0 + 360;
        }
        return d0;
    }

    public static double lerp(double current, double target, double delta) {
        return current + (target - current) * delta;
    }

    public static float lerp(float current, float target, float delta) {
        return current + (target - current) * delta;
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
