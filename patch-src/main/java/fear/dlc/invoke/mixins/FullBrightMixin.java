package fear.dlc.invoke.mixins;

import fear.dlc.FearDCP;
import fear.dlc.modules.impl.render.FullBright;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapTextureManager.class)
public class FullBrightMixin {
    @Inject(method = "update", at = @At("HEAD"))
    private void updateFullBright(float delta, CallbackInfo ci) {
        FearDCP client = FearDCP.getInstance();
        if (client == null || client.getModuleRepository() == null) {
            return;
        }
        FullBright fullBright = (FullBright) client.getModuleRepository().find(FullBright.class);
        if (fullBright != null) {
            fullBright.updateEffect();
        }
    }
}
