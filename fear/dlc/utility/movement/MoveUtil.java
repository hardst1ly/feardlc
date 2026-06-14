/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.movement;

import fear.dlc.Api;
import net.minecraft.class_241;

public final class MoveUtil
implements Api {
    private MoveUtil() {
    }

    public static boolean hasPlayerMovement() {
        if (mc.field_1724 == null || mc.field_1724.field_3913 == null) {
            return false;
        }
        if (mc.field_1724.field_3913.method_20622()) {
            return true;
        }
        class_241 movement = mc.field_1724.field_3913.method_3128();
        return movement.field_1343 != 0f || movement.field_1342 != 0f;
    }
}
