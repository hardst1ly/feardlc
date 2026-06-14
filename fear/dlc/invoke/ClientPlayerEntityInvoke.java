/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.CloseScreenEvent;
import fear.dlc.api.events.impl.CollisionEvent;
import fear.dlc.api.events.impl.PlayerEvent;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.impl.movement.FreeCamModule;
import fear.dlc.utility.movement.MovementFreeze;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_746.class})
public class ClientPlayerEntityInvoke {
    @Inject(method={"tick"}, at={@At(value="HEAD")})
    private void tick(CallbackInfo ci) {
        EventManager.call(new TickEvent());
    }

    @Inject(method={"tick"}, at={@At(value="TAIL")})
    private void tickTail(CallbackInfo ci) {
        MovementFreeze.tick();
    }

    @Inject(method={"closeHandledScreen"}, at={@At(value="HEAD")}, cancellable=true)
    private void closeHandledScreen(CallbackInfo ci) {
        CloseScreenEvent event = new CloseScreenEvent(class_310.method_1551().field_1755);
        EventManager.call(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"tickMovement"}, at={@At(value="HEAD")})
    private void tickMovement(CallbackInfo ci) {
        EventManager.call(new PlayerEvent.MovementEvent());
    }

    @Inject(method={"pushOutOfBlocks"}, at={@At(value="HEAD")}, cancellable=true)
    private void hookPushEvent(double x, double z, CallbackInfo ci) {
        if (FreeCamModule.isFreeCamActive()) {
            FreeCamModule freeCam = FreeCamModule.getInstance();
            if (freeCam != null) {
                if (freeCam.noClip.getEnabled().booleanValue()) {
                    ci.cancel();
                    return;
                }
            }
        }
        BlocksCollisionEvent event = new CollisionEvent.BlocksCollisionEvent(new class_243(x, 0, z));
        EventManager.call(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
