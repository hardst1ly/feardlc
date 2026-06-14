/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.modules.impl.render.PlayerOutlineModule;
import net.minecraft.class_1297;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4618;
import net.minecraft.class_898;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_898.class})
public class EntityRenderDispatcherOutlineMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void fearDlc$applyOutlineColor(class_1297 entity, double x, double y, double z, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
        if (vertexConsumers instanceof class_4618) {
            class_4618 outline = vertexConsumers;
            PlayerOutlineModule module = PlayerOutlineModule.getInstance();
            if (module != null) {
                if (module.getEnabled().booleanValue()) {
                    if (module.isEntityOutlined(entity)) {
                        int[] rgba = module.getOutlineRGBA();
                        outline.method_23286(rgba[0], rgba[1], rgba[2], rgba[3]);
                    }
                }
            }
        }
    }
}
