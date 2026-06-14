/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.ui.overlay.PotionsLayer;
import net.minecraft.class_329;
import net.minecraft.class_332;
import net.minecraft.class_9779;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_329.class})
public class InGameHudPotionsMixin {
    @Inject(method={"renderStatusEffectOverlay"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    private void fearDlc$cancelVanillaPotions(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
        if (PotionsLayer.shouldOverrideVanilla()) {
            ci.cancel();
        }
    }
}
