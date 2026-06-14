/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.FearDCP;
import fear.dlc.modules.impl.render.NoRender;
import net.minecraft.class_1058;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4603;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_4603.class})
public class InGameOverlayRendererMixin {
    @Inject(method={"renderFireOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private static void renderFireOverlay(class_4587 matrices, class_4597 vertexConsumers, CallbackInfo ci) {
        NoRender noRender = FearDCP.getInstance().getModuleRepository().find(NoRender.class);
        if (noRender.getEnabled().booleanValue()) {
            if (noRender.fire.getEnabled().booleanValue()) {
                ci.cancel();
            }
        }
    }

    @Inject(method={"renderUnderwaterOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private static void renderUnderwaterOverlay(class_310 client, class_4587 matrices, class_4597 vertexConsumers, CallbackInfo ci) {
        NoRender noRender = FearDCP.getInstance().getModuleRepository().find(NoRender.class);
        if (noRender.getEnabled().booleanValue()) {
            if (noRender.water.getEnabled().booleanValue()) {
                ci.cancel();
            }
        }
    }

    @Inject(method={"renderInWallOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private static void renderInWallOverlay(class_1058 sprite, class_4587 matrices, class_4597 vertexConsumers, CallbackInfo ci) {
        NoRender noRender = FearDCP.getInstance().getModuleRepository().find(NoRender.class);
        if (noRender.getEnabled().booleanValue()) {
            if (noRender.wall.getEnabled().booleanValue()) {
                ci.cancel();
            }
        }
    }
}
