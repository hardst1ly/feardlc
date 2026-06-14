/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.FearDCP;
import fear.dlc.modules.impl.render.AspectRatio;
import net.minecraft.class_310;
import net.minecraft.class_757;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_757.class})
public abstract class AspectRatioMixin {
    @Shadow
    @Final
    class_310 field_4015;
    @Shadow
    private float field_4025;

    @Inject(method={"getBasicProjectionMatrix"}, at={@At(value="RETURN")}, cancellable=true)
    private void onGetProjectionMatrix(float fov, CallbackInfoReturnable<Matrix4f> cir) {
        if (this.field_4015 == null || this.field_4015.field_1687 == null || this.field_4015.field_1724 == null) {
            return;
        }
        AspectRatio module = FearDCP.getInstance().getModuleRepository().find(AspectRatio.class);
        if (module == null || !module.getEnabled().booleanValue()) {
            return;
        }
        float customAspect = module.ratio.getValue().floatValue();
        if (customAspect < 0.1f || customAspect > 10f) {
            return;
        }
        float windowAspect = this.field_4015.method_22683().method_4489() / (float)this.field_4015.method_22683().method_4506();
        if (Math.abs(customAspect - windowAspect) < 0.01f) {
            return;
        }
        Matrix4f matrix = cir.getReturnValue();
        float fovRad = Math.toRadians((double)fov);
        float near = 0.05f;
        float far = this.field_4025 * 4f;
        Matrix4f newMatrix = new Matrix4f().setPerspective(fovRad, customAspect, near, far);
        cir.setReturnValue(newMatrix);
    }
}
