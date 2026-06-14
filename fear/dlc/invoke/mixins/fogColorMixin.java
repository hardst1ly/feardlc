/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.FearDCP;
import fear.dlc.modules.impl.render.Ambience;
import net.minecraft.class_4184;
import net.minecraft.class_638;
import net.minecraft.class_758;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_758.class})
public class fogColorMixin {
    @Inject(method={"getFogColor"}, at={@At(value="RETURN")}, cancellable=true)
    private static void modifyFogColor(class_4184 camera, float tickDelta, class_638 world, int clampedViewDistance, float skyDarkness, CallbackInfoReturnable<Vector4f> cir) {
        Ambience ambience = FearDCP.getInstance().getModuleRepository().find(Ambience.class);
        if (!ambience.getEnabled().booleanValue()) {
            return;
        }
        boolean isEnabled = ambience.fogChanger.getEnabled().booleanValue();
        if (!isEnabled) {
            return;
        }
        int color = ambience.fogColor.getColorRGB();
        float r = (color >> 16 & 255) / 255f;
        float g = (color >> 8 & 255) / 255f;
        float b = (color & 255) / 255f;
        Vector4f customFogColor = new Vector4f(r, g, b, 1f);
        cir.setReturnValue(customFogColor);
    }
}
