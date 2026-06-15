package fear.dlc.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class ElytraHelper extends ModuleLayer {
    private static final int CHEST_ARMOR_SLOT = 6;
    private static final int DEFAULT_BIND = GLFW.GLFW_KEY_P;

    public final BooleanSetting autoStart;
    public final BooleanSetting useFirework;
    public final BooleanSetting debug;

    private int launchDelayTicks;
    private int fireworkDelayTicks;

    public ElytraHelper() {
        super(
                Text.literal("Elytra Helper"),
                Text.literal("Swaps elytra/chestplate, starts flying and can use a firework."),
                Category.Player
        );
        this.setKey(DEFAULT_BIND);

        this.autoStart = new BooleanSetting(
                Text.literal("Auto Start"),
                Text.literal("Jump and start fall flying after swapping to elytra."),
                () -> true
        ).set(true).register(this);

        this.useFirework = new BooleanSetting(
                Text.literal("Use Firework"),
                Text.literal("Use one firework after auto start."),
                () -> this.autoStart.getEnabled()
        ).set(true).register(this);

        this.debug = new BooleanSetting(
                Text.literal("Debug"),
                Text.literal("Show Elytra Helper messages in chat."),
                () -> true
        ).set(true).register(this);
    }

    /**
     * This module is a momentary helper: pressing P executes the action once.
     * It should not stay enabled like combat/render modules.
     */
    @Override
    public void toggleEnabled() {
        this.runHelper();
    }

    @Override
    public void activate() {
        this.runHelper();
    }

    @Subscribe
    private void onTick(TickEvent event) {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) {
            this.launchDelayTicks = 0;
            this.fireworkDelayTicks = 0;
            return;
        }

        if (this.launchDelayTicks > 0) {
            this.launchDelayTicks--;
            if (this.launchDelayTicks == 0) {
                this.startFlying();
                if (this.useFirework.getEnabled()) {
                    this.fireworkDelayTicks = 3;
                }
            }
        }

        if (this.fireworkDelayTicks > 0) {
            this.fireworkDelayTicks--;
            if (this.fireworkDelayTicks == 0) {
                this.useFirework();
            }
        }
    }

    private void runHelper() {
        if (mc.currentScreen != null || mc.player == null || mc.world == null || mc.interactionManager == null) {
            return;
        }

        boolean swappedToElytra = this.swapChestSlot();
        if (swappedToElytra && this.autoStart.getEnabled()) {
            this.launchDelayTicks = 4;
        }
    }

    private boolean swapChestSlot() {
        ItemStack equippedChest = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        boolean wearingElytra = equippedChest.isOf(Items.ELYTRA);

        int targetSlot = wearingElytra ? this.findBestChestplateSlot() : this.findItemSlot(Items.ELYTRA);
        if (targetSlot == -1) {
            this.message(wearingElytra ? "Chestplate not found" : "Elytra not found");
            return false;
        }

        if (!mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
            this.message("Clear your cursor item first");
            return false;
        }

        int screenSlot = this.toScreenSlot(targetSlot);
        int syncId = mc.player.currentScreenHandler.syncId;

        mc.interactionManager.clickSlot(syncId, screenSlot, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, CHEST_ARMOR_SLOT, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, screenSlot, 0, SlotActionType.PICKUP, mc.player);

        this.message(wearingElytra ? "Swapped to chestplate" : "Swapped to elytra");
        return !wearingElytra;
    }

    private void startFlying() {
        if (mc.player == null || mc.player.networkHandler == null) {
            return;
        }
        if (!mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
            return;
        }

        if (mc.player.isOnGround()) {
            mc.player.jump();
            this.launchDelayTicks = 3;
            return;
        }

        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(
                mc.player,
                ClientCommandC2SPacket.Mode.START_FALL_FLYING
        ));
        this.message("Started flying");
    }

    private void useFirework() {
        int fireworkSlot = this.findItemSlot(Items.FIREWORK_ROCKET);
        if (fireworkSlot == -1) {
            this.message("Firework not found");
            return;
        }

        int previousSlot = mc.player.getInventory().selectedSlot;
        boolean movedFromInventory = fireworkSlot >= 9;

        if (movedFromInventory) {
            mc.interactionManager.clickSlot(
                    mc.player.currentScreenHandler.syncId,
                    this.toScreenSlot(fireworkSlot),
                    previousSlot,
                    SlotActionType.SWAP,
                    mc.player
            );
        } else {
            this.selectSlot(fireworkSlot);
        }

        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        mc.player.swingHand(Hand.MAIN_HAND);

        if (movedFromInventory) {
            mc.interactionManager.clickSlot(
                    mc.player.currentScreenHandler.syncId,
                    this.toScreenSlot(fireworkSlot),
                    previousSlot,
                    SlotActionType.SWAP,
                    mc.player
            );
        } else {
            this.selectSlot(previousSlot);
        }

        this.message("Firework used");
    }

    private int findBestChestplateSlot() {
        Item[] chestplates = new Item[]{
                Items.NETHERITE_CHESTPLATE,
                Items.DIAMOND_CHESTPLATE,
                Items.IRON_CHESTPLATE,
                Items.GOLDEN_CHESTPLATE,
                Items.CHAINMAIL_CHESTPLATE,
                Items.LEATHER_CHESTPLATE
        };

        for (Item chestplate : chestplates) {
            int slot = this.findItemSlot(chestplate);
            if (slot != -1) {
                return slot;
            }
        }
        return -1;
    }

    private int findItemSlot(Item item) {
        for (int slot = 0; slot < 36; slot++) {
            if (mc.player.getInventory().getStack(slot).isOf(item)) {
                return slot;
            }
        }
        return -1;
    }

    private int toScreenSlot(int inventorySlot) {
        return inventorySlot < 9 ? inventorySlot + 36 : inventorySlot;
    }

    private void selectSlot(int slot) {
        mc.player.getInventory().selectedSlot = slot;
        mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(slot));
    }

    private void message(String text) {
        if (this.debug.getEnabled()) {
            this.printInfo("Elytra Helper: " + text);
        }
    }
}
