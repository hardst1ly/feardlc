/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.InputEvent;
import fear.dlc.utility.movement.MovementFreeze;
import net.minecraft.class_10185;
import net.minecraft.class_743;
import net.minecraft.class_744;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_744.class})
public class KeyboardInputMixin {
    @Shadow
    public class_10185 field_54155;

    @Inject(method={"tick"}, at={@At(value="RETURN")})
    private void onTick(CallbackInfo ci) {
        if (!(this instanceof class_743)) {
            return;
        }
        InputEvent event = new InputEvent(this.field_54155);
        EventManager.call(event);
        MovementFreeze.onInput(event);
        this.field_54155 = event.getInput();
    }
}
