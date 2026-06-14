/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.FearDCP;
import fear.dlc.modules.impl.render.Ambience;
import net.minecraft.class_4184;
import net.minecraft.class_6854;
import net.minecraft.class_758;
import net.minecraft.class_9958;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_758.class})
public class FogDistanceMixin {
    @Inject(method={"applyFog"}, at={@At(value="RETURN")}, cancellable=true)
    private static void onApplyFog(class_4184 camera, class_758.class_4596 fogType, Vector4f color, float viewDistance, boolean thickenFog, float tickDelta, CallbackInfoReturnable<class_9958> cir) {
        Ambience ambience = FearDCP.getInstance().getModuleRepository().find(Ambience.class);
        if (ambience == null || !ambience.getEnabled().booleanValue()) {
            return;
        }
        if (!ambience.fogChanger.getEnabled().booleanValue()) {
            return;
        }
        class_9958 original = cir.getReturnValue();
        if (original == null) {
            return;
        }
        float dist = Ambience.currentFogDistance;
        class_9958 modified = new class_9958(dist * 0.1f, dist, class_6854.field_36350, original.comp_3012(), original.comp_3013(), original.comp_3014(), original.comp_3015());
        cir.setReturnValue(modified);
    }
}
