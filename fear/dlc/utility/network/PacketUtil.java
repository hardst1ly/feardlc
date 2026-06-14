/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.network;

import fear.dlc.Api;
import net.minecraft.class_2596;

public final class PacketUtil
implements Api {
    private PacketUtil() {
    }

    public static void sendWithoutEvent(class_2596<?> packet) {
        if (mc.method_1562() == null) {
            return;
        }
        mc.method_1562().method_48296().method_10752(packet, null);
    }
}
