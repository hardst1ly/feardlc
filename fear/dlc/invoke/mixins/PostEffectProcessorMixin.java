/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.modules.impl.render.PlayerOutlineModule;
import net.minecraft.class_279;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_279.class})
public abstract class PostEffectProcessorMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void onRenderHead(CallbackInfo ci) {
        PlayerOutlineModule module = PlayerOutlineModule.getInstance();
        if (module != null && module.getEnabled().booleanValue()) {
        }
    }
}
