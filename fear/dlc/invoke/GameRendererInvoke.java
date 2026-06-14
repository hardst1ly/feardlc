/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.Api;
import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.utility.math.MathProjection;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_7833;
import net.minecraft.class_9779;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_757.class})
public class GameRendererInvoke {
    @Inject(at={@At(value="FIELD", target="Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode=180, ordinal=0)}, method={"renderWorld"})
    void render3dHook(class_9779 tickCounter, CallbackInfo ci) {
        class_4184 camera = Api.mc.field_1773.method_19418();
        class_4587 matrixStack = new class_4587();
        RenderSystem.getModelViewStack().pushMatrix().mul(matrixStack.method_23760().method_23761());
        matrixStack.method_22907(class_7833.field_40714.rotationDegrees(camera.method_19329()));
        matrixStack.method_22907(class_7833.field_40716.rotationDegrees(camera.method_19330() + 180f));
        MathProjection.lastModMat.set(RenderSystem.getModelViewMatrix());
        MathProjection.lastProjMat.set(RenderSystem.getProjectionMatrix());
        MathProjection.lastWorldSpaceMatrix.set(matrixStack.method_23760().method_23761());
        EventManager.call(new RenderEvent.AfterHand(matrixStack, tickCounter));
        RenderSystem.getModelViewStack().popMatrix();
    }
}
