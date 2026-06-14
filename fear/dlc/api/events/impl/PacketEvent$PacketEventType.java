/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

public static enum PacketEvent.PacketEventType {
    public static final PacketEvent.PacketEventType SEND = new PacketEvent.PacketEventType("SEND", 0);
    public static final PacketEvent.PacketEventType RECEIVE = new PacketEvent.PacketEventType("RECEIVE", 1);
    private static final /* synthetic */ PacketEvent.PacketEventType[] $VALUES;

    public static PacketEvent.PacketEventType[] values() {
        return (PacketEvent.PacketEventType[])$VALUES.clone();
    }

    public static PacketEvent.PacketEventType valueOf(String name) {
        return (PacketEvent.PacketEventType)Enum.valueOf(PacketEvent.PacketEventType.class, name);
    }

    private PacketEvent.PacketEventType() {
        super(var1, var2);
    }

    static {
        $VALUES = PacketEvent.PacketEventType.$values();
    }
}
