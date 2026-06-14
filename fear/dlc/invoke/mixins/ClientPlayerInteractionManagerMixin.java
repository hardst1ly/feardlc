/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.api.events.EventManager;
import fear.dlc.api.events.impl.ClickSlotEvent;
import fear.dlc.modules.impl.player.FakePlayer;
import fear.dlc.utility.inventory.InventoryUtility;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_636;
import net.minecraft.class_745;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_636.class})
public class ClientPlayerInteractionManagerMixin {
    @Inject(method={"clickSlot"}, at={@At(value="HEAD")}, cancellable=true)
    private void onClickSlot(int syncId, int slotId, int button, class_1713 actionType, class_1657 player, CallbackInfo ci) {
        ClickSlotEvent event = new ClickSlotEvent(syncId, slotId, button, actionType);
        EventManager.call(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"attackEntity"}, at={@At(value="HEAD")})
    private void onAttackEntity(class_1657 player, class_1297 target, CallbackInfo ci) {
        class_745 fakePlayer = FakePlayer.getFakePlayer();
        if (fakePlayer == null) { /* goto L81; */ }
        if (target != fakePlayer) { /* goto L81; */ }
        boolean isCritical = !player.method_24828() && player.field_6017 > 0f && !player.method_5624() && !player.method_5765();
        float baseDamage = InventoryUtility.getCriticalDamage(player.method_6047(), fakePlayer, player);
        float finalDamage = InventoryUtility.applyArmorAndResistance(baseDamage, fakePlayer);
        FakePlayer.onAttack(finalDamage, isCritical);
    }
}
