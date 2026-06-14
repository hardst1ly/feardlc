/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.PlayerEvent;
import net.minecraft.class_418;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_418.class})
public class DeathScreenInvoke {
    @Inject(method={"init"}, at={@At(value="HEAD")})
    private void init(CallbackInfo ci) {
        DeathEvent playerDeathEvent = new PlayerEvent.DeathEvent();
        EventManager.call(playerDeathEvent);
    }
}
