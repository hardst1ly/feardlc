/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.KeyEvent;
import net.minecraft.class_312;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_312.class})
public class MouseInvoke {
    @Inject(method={"onMouseButton"}, at={@At(value="HEAD")})
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        EventManager.call(new KeyEvent(window, button - 100, 0, action, mods));
    }
}
