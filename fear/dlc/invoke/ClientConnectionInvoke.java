/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.PacketEvent;
import net.minecraft.class_2535;
import net.minecraft.class_2547;
import net.minecraft.class_2596;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_2535.class})
public class ClientConnectionInvoke {
    @Inject(method={"handlePacket"}, at={@At(value="HEAD")}, cancellable=true)
    private static void handlePacketReceived(class_2596<? extends class_2547> packet, class_2547 listener, CallbackInfo ci) {
        PacketEvent packetEvent = new PacketEvent(packet, PacketEvent.PacketEventType.RECEIVE);
        EventManager.call(packetEvent);
        if (packetEvent.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"send(Lnet/minecraft/network/packet/Packet;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void handlePacketSend(class_2596<?> packet, CallbackInfo ci) {
        PacketEvent packetEvent = new PacketEvent(packet, PacketEvent.PacketEventType.SEND);
        EventManager.call(packetEvent);
        if (packetEvent.isCanceled()) {
            ci.cancel();
        }
    }
}
