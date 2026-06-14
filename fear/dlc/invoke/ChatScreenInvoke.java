/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.FearDCP;
import fear.dlc.api.draggable.data.DraggableRepository;
import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.modules.impl.misc.SafeModeModule;
import fear.dlc.utility.render.builders.Builder;
import fear.dlc.utility.render.msdf.MsdfFont;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_408;
import net.minecraft.class_437;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_408.class})
public abstract class ChatScreenInvoke
extends class_437 {
    protected ChatScreenInvoke() {
        super(class_2561.method_43473());
    }

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void renderAfterChatInvokeMethod(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SafeModeModule.isClickGuiPermanentlyBlocked()) {
            return;
        }
        DraggableRepository draggableRepository = FearDCP.getInstance().getDraggableRepository();
        class_310 mc = class_310.method_1551();
        draggableRepository.update(context, delta, mouseX, mouseY);
        draggableRepository.render(context, mc.method_61966(), (double)mouseX, (double)mouseY);
        ((BuiltText)Builder.TEXT_BUILDER.text("\u0417\u0430\u0436\u043c\u0438\u0442\u0435 ALT \u0447\u0442\u043e-\u0431\u044b \u0437\u0430\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043f\u043e Y").size(10f).color(-1).font((MsdfFont)Builder.INTER.get()).thickness(0.1f).outline(-16777216, 0.2f).build()).render(context.method_51448().method_23760().method_23761(), (float)mc.method_22683().method_4486() / 2f - ((MsdfFont)Builder.INTER.get()).getWidth("\u0417\u0430\u0436\u043c\u0438\u0442\u0435 ALT \u0447\u0442\u043e-\u0431\u044b \u0437\u0430\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043f\u043e Y", 10f) / 2f, 10f);
        ((BuiltText)Builder.TEXT_BUILDER.text("\u041d\u0430\u0436\u043c\u0438\u0442\u0435 CTRL + ALT \u0447\u0442\u043e-\u0431\u044b \u043f\u0435\u0440\u0435\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u0441\u0435\u0442\u043a\u0443").size(10f).color(-1).font((MsdfFont)Builder.INTER.get()).thickness(0.1f).outline(-16777216, 0.2f).build()).render(context.method_51448().method_23760().method_23761(), (float)mc.method_22683().method_4486() / 2f - ((MsdfFont)Builder.INTER.get()).getWidth("\u041d\u0430\u0436\u043c\u0438\u0442\u0435 CTRL + ALT \u0447\u0442\u043e-\u0431\u044b \u043f\u0435\u0440\u0435\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u0441\u0435\u0442\u043a\u0443", 10f) / 2f, 22f);
        EventManager.call(new RenderEvent.AfterChat(context, mouseX, mouseY, delta));
    }

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")})
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (SafeModeModule.isClickGuiPermanentlyBlocked()) {
            return;
        }
        DraggableRepository draggableRepository = FearDCP.getInstance().getDraggableRepository();
        draggableRepository.mouseClicked(mouseX, mouseY, button);
    }

    public boolean method_25406(double mouseX, double mouseY, int button) {
        if (!SafeModeModule.isClickGuiPermanentlyBlocked()) {
            DraggableRepository draggableRepository = FearDCP.getInstance().getDraggableRepository();
            draggableRepository.mouseReleased(mouseX, mouseY, button);
        }
        return super.method_25406(mouseX, mouseY, button);
    }

    @Inject(method={"keyPressed"}, at={@At(value="HEAD")}, cancellable=true)
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (SafeModeModule.isClickGuiPermanentlyBlocked()) {
            return;
        }
        DraggableRepository draggableRepository = FearDCP.getInstance().getDraggableRepository();
        if (draggableRepository.keyPressed(keyCode, scanCode, modifiers)) {
            cir.setReturnValue(false);
        }
    }
}
