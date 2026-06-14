/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.KeyEvent;
import net.minecraft.class_309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_309.class})
public class KeyboardInvoke {
    @Inject(method={"onKey"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/option/InactivityFpsLimiter;onInput()V")})
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        EventManager.call(new KeyEvent(window, key, scancode, action, modifiers));
    }
}
