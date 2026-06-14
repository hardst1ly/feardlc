/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.inventory;

import fear.dlc.Api;
import fear.dlc.utility.movement.MoveUtil;
import fear.dlc.utility.network.PacketUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_2596;
import net.minecraft.class_2813;

public final class InventoryMovePackets
implements Api {
    private static final List<DelayedPacket> delayed = new ArrayList();

    private InventoryMovePackets() {
    }

    public static void queueClickSlot(class_2813 packet, int delayTicks) {
        if (mc.field_1724 == null) {
            return;
        }
        int delay = Math.max(1, delayTicks);
        delayed.add(new DelayedPacket(packet, mc.field_1724.field_6012 + delay));
    }

    public static void tickRelease() {
        if (mc.field_1724 == null || delayed.isEmpty()) {
            return;
        }
        int age = mc.field_1724.field_6012;
        boolean stopped = !MoveUtil.hasPlayerMovement();
        Iterator<DelayedPacket> iterator = delayed.iterator();
        while (iterator.hasNext()) {
            DelayedPacket entry = iterator.next();
            if (stopped || age >= entry.releaseTick) {
                PacketUtil.sendWithoutEvent(entry.packet);
                iterator.remove();
            }
        }
    }

    public static void flushAll() {
        for (DelayedPacket entry : delayed) {
            PacketUtil.sendWithoutEvent(entry.packet);
        }
        delayed.clear();
    }

    public static boolean hasDelayed() {
        return !delayed.isEmpty();
    }

    public static void clear() {
        delayed.clear();
    }

    public static void sendAll(List<class_2596<?>> packets) {
        packets.forEach(PacketUtil::sendWithoutEvent);
        packets.clear();
    }
}
