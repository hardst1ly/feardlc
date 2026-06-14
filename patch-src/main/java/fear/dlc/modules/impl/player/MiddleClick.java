package fear.dlc.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import fear.dlc.api.events.impl.KeyEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.FriendManager;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class MiddleClick extends ModuleLayer {
    private static final int MIDDLE_MOUSE_KEY = -98;

    public final BooleanSetting friends;
    public final BooleanSetting pearl;

    public MiddleClick() {
        super(Text.literal("Middle Click"), Text.literal("Средняя кнопка: добавить друга или бросить жемчуг."), Category.Player);
        this.friends = new BooleanSetting(Text.literal("Друзья"), null, () -> true).set(true).register(this);
        this.pearl = new BooleanSetting(Text.literal("Жемчуг"), null, () -> true).set(true).register(this);
    }

    @Subscribe
    private void onKey(KeyEvent event) {
        if (!this.getEnabled() || event.getAction() != 1 || event.getKey() != MIDDLE_MOUSE_KEY) {
            return;
        }
        if (mc.currentScreen != null || mc.player == null || mc.interactionManager == null) {
            return;
        }

        if (this.friends.getEnabled() && mc.targetedEntity instanceof PlayerEntity player) {
            this.toggleFriend(player.getName().getString());
            return;
        }
        if (this.pearl.getEnabled()) {
            this.throwPearl();
        }
    }

    private void toggleFriend(String name) {
        if (FriendManager.isFriend(name)) {
            FriendManager.deleteFriend(name);
            this.printInfo(name + " удалён из друзей");
        } else {
            FriendManager.addFriend(name);
            this.printSuccess(name + " добавлен в друзья");
        }
    }

    private void throwPearl() {
        int slot = this.findPearlSlot();
        if (slot == -1) {
            this.printWarning("Жемчуг Края не найден в хотбаре");
            return;
        }

        int previousSlot = mc.player.getInventory().selectedSlot;
        this.selectSlot(slot);
        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        mc.player.swingHand(Hand.MAIN_HAND);
        this.selectSlot(previousSlot);
    }

    private int findPearlSlot() {
        for (int slot = 0; slot < 9; slot++) {
            if (mc.player.getInventory().getStack(slot).isOf(Items.ENDER_PEARL)) {
                return slot;
            }
        }
        return -1;
    }

    private void selectSlot(int slot) {
        mc.player.getInventory().selectedSlot = slot;
        mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(slot));
    }
}
