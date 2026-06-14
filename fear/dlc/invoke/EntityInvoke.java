/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.PlayerEvent;
import net.minecraft.class_1297;
import net.minecraft.class_243;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={class_1297.class})
public class EntityInvoke {
    @Shadow
    protected static class_243 method_18795(class_243 movementInput, float speed, float yaw) {
        return null;
    }

    @Redirect(method={"updateVelocity"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;movementInputToVelocity(Lnet/minecraft/util/math/Vec3d;FF)Lnet/minecraft/util/math/Vec3d;"))
    public class_243 hookVelocity(class_243 movementInput, float speed, float yaw) {
        if (this == class_310.method_1551().field_1724) {
            VelocityEvent event = new PlayerEvent.VelocityEvent(movementInput, speed, yaw, EntityInvoke.method_18795(movementInput, speed, yaw));
            EventManager.call(event);
            return event.getVelocity();
        }
        return EntityInvoke.method_18795(movementInput, speed, yaw);
    }
}
