/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.RenderEvent;
import net.minecraft.class_10017;
import net.minecraft.class_10055;
import net.minecraft.class_1297;
import net.minecraft.class_2561;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_897;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_897.class})
public class EntityRendererInvoke<T extends class_1297, S extends class_10017> {
    @Inject(method={"renderLabelIfPresent"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderLabelIfPresentInvoke(S state, class_2561 text, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
        if (state instanceof class_10055) {
            RenderEvent.RenderLabelsEvent<T, S> renderLabelsEvent = new RenderEvent.RenderLabelsEvent(state);
            EventManager.call(renderLabelsEvent);
            if (renderLabelsEvent.isCanceled()) {
                ci.cancel();
            }
        }
    }
}
