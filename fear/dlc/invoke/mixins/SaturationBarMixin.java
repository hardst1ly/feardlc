/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.FearDCP;
import fear.dlc.modules.impl.render.SaturationDisplayModule;
import net.minecraft.class_1657;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_329;
import net.minecraft.class_332;
import net.minecraft.class_9848;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_329.class})
public abstract class SaturationBarMixin {
    @Shadow
    @Final
    private class_310 field_2035;
    private static final class_2960 FOOD_EMPTY = class_2960.method_60656("hud/food_empty");
    private static final class_2960 FOOD_HALF = class_2960.method_60656("hud/food_half");
    private static final class_2960 FOOD_FULL = class_2960.method_60656("hud/food_full");

    @Inject(method={"renderStatusBars"}, at={@At(value="RETURN")})
    private void fearDlc$renderSaturationBar(class_332 context, CallbackInfo ci) {
        SaturationDisplayModule module = FearDCP.getInstance().getModuleRepository().find(SaturationDisplayModule.class);
        if (module == null || !module.getEnabled().booleanValue()) {
            return;
        }
        class_1657 player = this.field_2035.field_1724;
        if (player == null) {
            return;
        }
        float saturation = player.method_7344().method_7589();
        int foodLevel = player.method_7344().method_7586();
        if (foodLevel <= 0 && saturation <= 0f) {
            return;
        }
        int x = this.field_2035.method_22683().method_4486() / 2 + 91;
        int y = this.field_2035.method_22683().method_4502() - 39 + (int)module.yOffset.getValue().floatValue();
        float opacity = module.opacity.getValue().floatValue();
        int alpha = (opacity * 255f);
        int tint = class_9848.method_61324(alpha, 255, 200, 60);
        for (int i = 0; i < 10; i++) {
            int iconX = x - i * 8 - 9;
            int iconY = y;
            float satForIcon = saturation - (float)i * 2f;
            if (!(satForIcon <= 0f)) {
                if (satForIcon >= 2f) {
                    class_2960 sprite = FOOD_FULL;
                } else {
                    if (satForIcon >= 1f) {
                        class_2960 sprite = FOOD_HALF;
                    } else {
                        class_2960 sprite = FOOD_HALF;
                    }
                }
                context.method_52707(class_1921::method_62277, sprite, iconX, iconY, 9, 9, tint);
            }
        }
        if (module.showNumbers.getEnabled().booleanValue()) {
            Object[] tmp0 = new Object[1];
            tmp0[0] = saturation;
            String text = String.format("%.1f", tmp0);
            int textX = x - 81;
            int textY = y - 10;
            context.method_25303(this.field_2035.field_1772, text, textX, textY, tint);
        }
    }
}
