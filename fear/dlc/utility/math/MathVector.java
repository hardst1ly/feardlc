/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.math;

import fear.dlc.Api;
import net.minecraft.class_1297;
import net.minecraft.class_243;
import net.minecraft.class_3532;

public class MathVector
implements Api {
    public static float rotationDifference(class_1297 entity) {
        if (mc.field_1724 == null || entity == null) {
            return 0f;
        }
        double x = MathVector.interpolate(entity.field_6014, entity.method_19538().field_1352) - MathVector.interpolate(mc.field_1724.field_6014, mc.field_1724.method_19538().field_1352);
        double z = MathVector.interpolate(entity.field_5969, entity.method_19538().field_1350) - MathVector.interpolate(mc.field_1724.field_5969, mc.field_1724.method_19538().field_1350);
        return -(Math.atan2(x, z) * 57.29577951308232);
    }

    public static class_243 lerpPosition(class_1297 entity) {
        float tickDelta = mc.method_61966().method_60637(true);
        return new class_243(entity.field_6014 + (entity.method_23317() - entity.field_6014) * (double)tickDelta, entity.field_6036 + (entity.method_23318() - entity.field_6036) * (double)tickDelta, entity.field_5969 + (entity.method_23321() - entity.field_5969) * (double)tickDelta);
    }

    public static double interpolate(double d, double d2) {
        return d + (d2 - d) * (double)mc.method_61966().method_60637(true);
    }

    public static float distanceTo(class_243 from, class_243 to) {
        float f = (from.method_10216() - to.method_10216());
        float g = (from.method_10214() - to.method_10214());
        float h = (from.method_10215() - to.method_10215());
        return class_3532.method_15355(f * f + g * g + h * h);
    }
}
