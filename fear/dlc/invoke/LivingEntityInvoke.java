/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.CollisionEvent;
import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1309.class})
public class LivingEntityInvoke {
    @Inject(method={"isPushable"}, at={@At(value="HEAD")}, cancellable=true)
    private void hookPushEvent(CallbackInfoReturnable<Boolean> cir) {
        PlayerCollisionEvent event = new CollisionEvent.PlayerCollisionEvent();
        EventManager.call(event);
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }
}
