/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import net.minecraft.class_1297;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1297.class})
public abstract class EntityOutlineColorMixin {
    @Inject(method={"getTeamColorValue"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetTeamColorValue(CallbackInfoReturnable<Integer> cir) {
    }
}
