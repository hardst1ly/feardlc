/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.FearDCP;
import fear.dlc.api.draggable.data.DraggableRepository;
import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.RenderEvent;
import net.minecraft.class_310;
import net.minecraft.class_329;
import net.minecraft.class_332;
import net.minecraft.class_408;
import net.minecraft.class_9779;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_329.class})
public class InGameHudInvoke {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void renderBeforeHudInvokeMethod(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
        EventManager.call(new RenderEvent.BeforeHud(context, tickCounter));
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void renderAfterHudInvokeMethod(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
        class_310 mc = class_310.method_1551();
        if (mc.field_1755 instanceof class_408) {
            return;
        }
        DraggableRepository draggableRepository = FearDCP.getInstance().getDraggableRepository();
        double scaledMouseX = mc.field_1729.method_1603() * (double)mc.method_22683().method_4486() / (double)mc.method_22683().method_4480();
        double scaledMouseY = mc.field_1729.method_1604() * (double)mc.method_22683().method_4502() / (double)mc.method_22683().method_4507();
        draggableRepository.render(context, tickCounter, scaledMouseX, scaledMouseY);
        EventManager.call(new RenderEvent.AfterHud(context, tickCounter));
    }
}
