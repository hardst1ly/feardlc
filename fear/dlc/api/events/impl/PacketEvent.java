/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.EventLayer;
import net.minecraft.class_2596;

public class PacketEvent
extends EventLayer {
    class_2596<?> packet;
    PacketEventType packetEventType;

    public class_2596<?> getPacket() {
        return this.packet;
    }

    public PacketEventType getPacketEventType() {
        return this.packetEventType;
    }

    public PacketEvent(class_2596<?> packet, PacketEventType packetEventType) {
        this.packet = packet;
        this.packetEventType = packetEventType;
    }
}
