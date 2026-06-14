/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import fear.dlc.FearDCP;
import fear.dlc.modules.impl.render.SeeInvisible;
import net.minecraft.class_10042;
import net.minecraft.class_10055;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value={class_922.class})
public abstract class TransparentInvisiblePlayersMixin {
    private SeeInvisible getAntiInvisible() {
        return FearDCP.getInstance().getModuleRepository().find(SeeInvisible.class);
    }

    @ModifyReturnValue(method={"isVisible"}, at={@At(value="RETURN")})
    private boolean forceVisible(boolean original, class_10042 state) {
        SeeInvisible antiInvisible = this.getAntiInvisible();
        if (!antiInvisible.getEnabled().booleanValue()) {
            return original;
        }
        if (state instanceof class_10055 && state.field_53333) {
            return true;
        }
        return original;
    }

    @Inject(method={"getRenderLayer"}, at={@At(value="HEAD")}, cancellable=true)
    private void forceTranslucent(class_10042 state, boolean showBody, boolean translucent, boolean showOutline, CallbackInfoReturnable<class_1921> cir) {
        SeeInvisible antiInvisible = this.getAntiInvisible();
        if (!antiInvisible.getEnabled().booleanValue()) {
            return;
        }
        if (state instanceof class_10055) {
            class_10055 playerState = state;
            if (state.field_53333) {
                class_2960 skin = playerState.field_53520.comp_1626();
                cir.setReturnValue(class_1921.method_23580(skin));
            }
        }
    }

    @ModifyArgs(method={"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
    private void makeInvisiblePlayersHalfTransparent(Args args, class_10042 state, class_4587 matrices, class_4597 vertexConsumers, int light) {
        SeeInvisible antiInvisible = this.getAntiInvisible();
        if (!antiInvisible.getEnabled().booleanValue()) {
            return;
        }
        int color = ((Integer)args.get(4)).intValue();
        if (state instanceof class_10055 && state.field_53333) {
            int newColor = -2147483648 | color & 16777215;
            args.set(4, newColor);
        }
    }
}
