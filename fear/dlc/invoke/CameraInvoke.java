/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.FearDCP;
import fear.dlc.modules.impl.movement.FreeCamModule;
import fear.dlc.modules.more.ModuleLayer;
import net.minecraft.class_243;
import net.minecraft.class_4184;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_4184.class})
public abstract class CameraInvoke {
    @Shadow
    private class_243 field_18712;
    @Shadow
    private float field_18718;
    @Shadow
    private float field_18717;

    @Inject(method={"update"}, at={@At(value="RETURN")})
    private void onCameraUpdate(CallbackInfo ci) {
        try {
            ModuleLayer module = FearDCP.getInstance().getModuleRepository().find(FreeCamModule.class);
            if (module instanceof FreeCamModule) {
                FreeCamModule freeCam = module;
                if (freeCam.getEnabled().booleanValue()) {
                    class_243 cameraPos = freeCam.getCameraPos();
                    if (cameraPos != null) {
                        this.field_18712 = cameraPos;
                    }
                }
            }
        }
        catch (Exception module) {
            return;
        }
    }
}
