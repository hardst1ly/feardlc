/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.FearDCP;
import fear.dlc.modules.impl.render.Ambience;
import net.minecraft.class_2761;
import net.minecraft.class_634;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_634.class})
public class TimeUpdateMixin {
    @Inject(method={"onWorldTimeUpdate"}, at={@At(value="HEAD")}, cancellable=true)
    private void onTimeUpdate(class_2761 packet, CallbackInfo ci) {
        Ambience ambience = FearDCP.getInstance().getModuleRepository().find(Ambience.class);
        if (ambience != null) {
            if (ambience.getEnabled().booleanValue()) {
                if (ambience.changeTime.getEnabled().booleanValue()) {
                    ci.cancel();
                }
            }
        }
    }
}
