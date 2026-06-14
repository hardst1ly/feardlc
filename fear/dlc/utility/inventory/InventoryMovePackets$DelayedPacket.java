/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.inventory;

import net.minecraft.class_2813;

private static final record InventoryMovePackets.DelayedPacket {
    private final class_2813 packet;
    private final int releaseTick;

    private InventoryMovePackets.DelayedPacket(class_2813 packet, int releaseTick) {
        this.packet = packet;
        this.releaseTick = releaseTick;
    }

    public final String toString() {
        return /* lambda: toString */ this;
    }

    public final int hashCode() {
        return /* lambda: hashCode */ this;
    }

    public final boolean equals(Object o) {
        return /* lambda: equals */ this, o;
    }

    public class_2813 packet() {
        return this.packet;
    }

    public int releaseTick() {
        return this.releaseTick;
    }
}
