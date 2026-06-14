/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.Api;
import fear.dlc.FearDCP;
import fear.dlc.modules.impl.render.FullBright;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_310;
import net.minecraft.class_765;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_765.class})
public class FullBrightMixin {
    @Inject(method={"update"}, at={@At(value="HEAD")})
    private void injectFullBrightEffect(float delta, CallbackInfo ci) {
        if (class_310.method_1551().field_1724 == null) {
            return;
        }
        FullBright fullBright = FearDCP.getInstance().getModuleRepository().find(FullBright.class);
        if (fullBright == null) {
            return;
        }
        Api.mc.field_1724.method_6092(new class_1293(class_1294.field_5925, -1, 0, false, false, false));
        if (fullBright.getEnabled().booleanValue() && !Api.mc.field_1724.method_6059(class_1294.field_5925)) {
            return;
        }
        if (!fullBright.getEnabled().booleanValue()) {
            if (Api.mc.field_1724.method_6059(class_1294.field_5925)) {
                class_1293 instance = Api.mc.field_1724.method_6112(class_1294.field_5925);
                if (instance != null) {
                    if (instance.method_48559()) {
                        Api.mc.field_1724.method_6016(class_1294.field_5925);
                    }
                }
            }
        }
    }
}
