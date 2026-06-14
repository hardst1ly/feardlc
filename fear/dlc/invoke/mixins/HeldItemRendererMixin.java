/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.FearDCP;
import fear.dlc.invoke.mixins.HeldItemRendererAccessor;
import fear.dlc.modules.impl.render.SwingAnimationModule;
import fear.dlc.modules.impl.render.ViewModelModule;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_759;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_759.class})
public abstract class HeldItemRendererMixin {
    @Unique
    private static final ThreadLocal<class_1268> fearDlc$currentHand;

    @Inject(method={"renderFirstPersonItem"}, at={@At(value="HEAD")})
    private void fearDlc$captureHand(class_742 player, float tickDelta, float pitch, class_1268 hand, float swingProgress, class_1799 item, float equipProgress, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
        fearDlc$currentHand.set(hand);
        ViewModelModule view = this.fearDlc$getViewModel();
        if (view != null) {
            if (view.getEnabled().booleanValue()) {
                view.applyTransformations(matrices, hand);
            }
        }
    }

    @Inject(method={"renderFirstPersonItem"}, at={@At(value="RETURN")})
    private void fearDlc$undoViewModel(class_742 player, float tickDelta, float pitch, class_1268 hand, float swingProgress, class_1799 item, float equipProgress, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
        ViewModelModule view = this.fearDlc$getViewModel();
        if (view != null) {
            if (view.getEnabled().booleanValue()) {
                view.undoTransformations(matrices);
            }
        }
    }

    @Redirect(method={"renderFirstPersonItem"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/item/HeldItemRenderer;applySwingOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V"), require=0)
    private void fearDlc$replaceSwing(class_759 self, class_4587 matrices, class_1306 arm, float swingProgress) {
        SwingAnimationModule swing = this.fearDlc$getSwing();
        class_1268 hand = fearDlc$currentHand.get();
        if (swing != null && swing.getEnabled().booleanValue() && swing.applySwing(matrices, hand, swingProgress)) {
            return;
        }
        ((HeldItemRendererAccessor)self).fearDlc$applySwingOffset(matrices, arm, swingProgress);
    }

    @Unique
    private SwingAnimationModule fearDlc$getSwing() {
        try {
            return FearDCP.getInstance().getModuleRepository().find(SwingAnimationModule.class);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Unique
    private ViewModelModule fearDlc$getViewModel() {
        try {
            return FearDCP.getInstance().getModuleRepository().find(ViewModelModule.class);
        }
        catch (Exception e) {
            return null;
        }
    }

    static {
        fearDlc$currentHand = ThreadLocal.withInitial(HeldItemRendererMixin::lambda$static$0);
    }
}
