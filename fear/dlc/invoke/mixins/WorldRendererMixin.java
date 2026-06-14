/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.modules.impl.render.PlayerOutlineModule;
import net.minecraft.class_276;
import net.minecraft.class_310;
import net.minecraft.class_761;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_761.class})
public abstract class WorldRendererMixin {
    @Shadow
    @Final
    private class_310 field_4088;
    @Shadow
    private class_276 field_53080;

    @Shadow
    protected abstract boolean method_3270();

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void onRenderEnd(CallbackInfo ci) {
        PlayerOutlineModule module = PlayerOutlineModule.getInstance();
        if (module != null) {
            if (!module.getEnabled().booleanValue()) { /* goto @25; */ }
        }
    }
}
