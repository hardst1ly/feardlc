/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import fear.dlc.modules.impl.combat.Hitboxes;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1297.class})
public abstract class EntityMixin {
    @ModifyExpressionValue(method={"move"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;isControlledByPlayer()Z")})
    private boolean fixFallDistanceCalculation(boolean original) {
        if (this == class_310.method_1551().field_1724) {
            return false;
        }
        return original;
    }

    @Shadow
    public abstract double method_23317();

    @Shadow
    public abstract double method_23318();

    @Shadow
    public abstract double method_23321();

    @Shadow
    public abstract float method_17682();

    @Shadow
    public abstract class_1937 method_37908();

    @Shadow
    public abstract class_243 method_19538();

    @Inject(method={"getBoundingBox"}, at={@At(value="RETURN")}, cancellable=true)
    private void onGetBoundingBox(CallbackInfoReturnable<class_238> cir) {
        if (this.method_37908() == null || !this.method_37908().field_9236) {
            return;
        }
        class_1297 self = this;
        float size = Hitboxes.getCurrentHitbox();
        if (size <= 0.6f) {
            return;
        }
        if (self instanceof class_1657) {
            class_1657 target = self;
        } else {
            return;
        }
        if (!target.method_37908().field_9236) {
            return;
        }
        class_310 mc = class_310.method_1551();
        if (target == mc.field_1724) {
            return;
        }
        if (mc.field_1724 != null) {
            double distance = mc.field_1724.method_19538().method_1022(self.method_19538());
            if (distance > 3.2200000286102295 && Hitboxes.isLegitMode()) {
                return;
            }
            double half = (size / 2f);
            cir.setReturnValue(new class_238(this.method_23317() - half, this.method_23318(), this.method_23321() - half, this.method_23317() + half, this.method_23318() + (double)this.method_17682(), this.method_23321() + half));
        }
    }
}
