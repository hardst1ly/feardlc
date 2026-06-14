/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.movement;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.ClickSlotEvent;
import fear.dlc.api.events.impl.CloseScreenEvent;
import fear.dlc.api.events.impl.InputEvent;
import fear.dlc.api.events.impl.PacketEvent;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.ui.clickGui.ClickGuiScreen;
import fear.dlc.utility.movement.MoveUtil;
import fear.dlc.utility.movement.MovementController;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_10185;
import net.minecraft.class_1713;
import net.minecraft.class_2561;
import net.minecraft.class_2596;
import net.minecraft.class_2645;
import net.minecraft.class_2813;
import net.minecraft.class_2815;
import net.minecraft.class_3675;
import net.minecraft.class_408;
import net.minecraft.class_463;
import net.minecraft.class_471;
import net.minecraft.class_497;
import net.minecraft.class_498;

public class GuiWalkModule
extends ModuleLayer {
    private final List<class_2596<?>> packets = new ArrayList();
    public final ModeSetting mode;
    public final BooleanSetting grimBypass;
    final MovementController movement;
    MovePhase movePhase;
    long actionStartTime;
    int currentDelay;
    boolean wasForwardPressed;
    boolean wasBackPressed;
    boolean wasLeftPressed;
    boolean wasRightPressed;
    boolean wasJumpPressed;
    boolean keysOverridden;
    boolean inventoryOpened;
    boolean packetsHeld;
    boolean pendingClose;
    int closeScreenSyncId;

    public GuiWalkModule() {
        super(class_2561.method_30163("GuiWalk"), class_2561.method_30163("\u0425\u043e\u0434\u044c\u0431\u0430 \u0432 GUI"), Category.Movement);
        String[] tmp0 = new String[2];
        tmp0[0] = "Normal";
        tmp0[1] = "Legit";
        this.mode = new ModeSetting(class_2561.method_30163("\u0420\u0435\u0436\u0438\u043c"), null, GuiWalkModule::lambda$new$0).set(tmp0).set("Legit").register(this);
        this.grimBypass = new BooleanSetting(class_2561.method_30163("Grim Bypass"), class_2561.method_30163("\u041e\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f \u043f\u0440\u0438 \u0437\u0430\u043a\u0440\u044b\u0442\u0438\u0438 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044f"), this::lambda$new$1).set(true).register(this);
        this.movement = new MovementController();
        this.movePhase = MovePhase.READY;
        this.actionStartTime = 0L;
        this.currentDelay = 0;
        this.keysOverridden = false;
        this.inventoryOpened = false;
        this.packetsHeld = false;
        this.pendingClose = false;
        this.closeScreenSyncId = -1;
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        this.resetState();
        super.deactivate();
    }

    @Subscribe
    public void onInput(InputEvent e) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        if (mc.field_1724 == null) {
            return;
        }
        if (this.movement.isBlocked()) {
            e.setDirectionalLow(false, false, false, false);
            e.setJumping(false);
        }
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        if (this.mode.getValue().equals("Legit")) {
            this.handleLegitPackets(e);
        }
    }

    private void handleLegitPackets(PacketEvent e) {
        if (e.getPacketEventType() != PacketEvent.PacketEventType.SEND) {
            return;
        }
        class_2596 var4 = e.getPacket();
        if (var4 instanceof class_2813) {
            class_2813 slot = var4;
            if (!(this.packetsHeld)) {
                if (MoveUtil.hasPlayerMovement()) {
                    if (this.shouldSkipExecution()) {
                        this.packets.add(slot);
                        e.cancel();
                        this.packetsHeld = true;
                    }
                }
            }
        } else {
            var4 = e.getPacket();
            if (var4 instanceof class_2645) {
                class_2645 screen = var4;
                if (screen.method_36148() == 0) {
                    e.cancel();
                }
            }
        }
    }

    @Subscribe
    public void onTick(TickEvent e) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        if (mc.field_1724 == null) {
            return;
        }
        if (this.mode.getValue().equals("Legit")) {
            this.processLegitMovement();
        } else {
            if (!this.isServerScreen()) {
                if (this.shouldSkipExecution()) {
                    this.updateMoveKeys();
                }
            }
        }
    }

    private void processLegitMovement() {
        boolean hasOpenScreen = mc.field_1755 != null;
        this.startLegitMovement();
        if (hasOpenScreen && !this.inventoryOpened && this.movePhase == MovePhase.READY) {
            this.inventoryOpened = true;
        }
        if (this.pendingClose) {
            this.handlePendingClose();
            return;
        }
        if (!hasOpenScreen) {
            if (this.inventoryOpened) {
                if (this.movePhase != MovePhase.READY) {
                    this.inventoryOpened = false;
                    if (this.movePhase == MovePhase.ALLOW_MOVEMENT) {
                        this.resetState();
                    }
                    return;
                }
            }
        }
        if (this.movePhase != MovePhase.READY) {
            this.handleMovementStates();
        }
    }

    private void handlePendingClose() {
        long elapsed = System.currentTimeMillis() - this.actionStartTime;
        switch (this.movePhase.ordinal()) {
            case 2:
                this.movement.block();
                this.movement.stopSprint();
                this.movePhase = MovePhase.WAIT_STOP;
                this.actionStartTime = System.currentTimeMillis();
            case 3:
                this.movement.block();
                boolean stopped = this.isPlayerStopped();
                if (!(stopped)) {
                    if (!(elapsed < 100L)) {
                    }
                }
                if (this.packetsHeld) {
                    this.movePhase = MovePhase.SEND_PACKETS;
                } else {
                    this.movePhase = MovePhase.CLOSE_INVENTORY;
                }
                this.actionStartTime = System.currentTimeMillis();
            case 4:
                this.blockMovementInput();
                if (!(elapsed <= 1L)) {
                    this.movePhase = MovePhase.SEND_PACKETS;
                }
                this.actionStartTime = System.currentTimeMillis();
            case 5:
                this.sendHeldPackets();
                this.movePhase = MovePhase.CLOSE_INVENTORY;
                this.actionStartTime = System.currentTimeMillis();
            case 8:
                this.closeInventoryNow();
                this.movePhase = MovePhase.RESUMING;
                this.currentDelay = 20 + (int)(Math.random() * 30);
                this.actionStartTime = System.currentTimeMillis();
            case 7:
                if (!(elapsed < (long)this.currentDelay)) {
                    if (this.movement.isBlocked()) {
                        this.movement.restoreFromCurrent();
                    }
                }
                if (this.keysOverridden) {
                    this.restoreKeyStates();
                }
                this.movePhase = MovePhase.FINISHED;
            case 9:
                this.resetState();
            case 6:
            default:
                return;
        }
    }

    private boolean isPlayerStopped() {
        if (mc.field_1724 == null) {
            return true;
        }
        double vx = Math.abs(mc.field_1724.method_18798().field_1352);
        double vz = Math.abs(mc.field_1724.method_18798().field_1350);
        return vx < 0.03 && vz < 0.03;
    }

    private void blockMovementInput() {
        if (mc.field_1724 != null) {
            if (mc.field_1724.field_3913 != null) {
                mc.field_1724.field_3913.field_54155 = new class_10185(false, false, false, false, mc.field_1724.field_3913.field_54155.comp_3163(), mc.field_1724.field_3913.field_54155.comp_3164(), mc.field_1724.field_3913.field_54155.comp_3165());
            }
        }
        if (!this.keysOverridden) {
            mc.field_1690.field_1894.method_23481(false);
            mc.field_1690.field_1881.method_23481(false);
            mc.field_1690.field_1913.method_23481(false);
            mc.field_1690.field_1849.method_23481(false);
            mc.field_1690.field_1903.method_23481(false);
            this.keysOverridden = true;
        }
    }

    private void sendHeldPackets() {
        if (!this.packets.isEmpty()) {
            this.packets.forEach(GuiWalkModule::lambda$sendHeldPackets$2);
            this.packets.clear();
            this.updateSlots();
        }
        this.packetsHeld = false;
    }

    private void closeInventoryNow() {
        if (mc.field_1724 == null || mc.method_1562() == null) {
            return;
        }
        if (this.closeScreenSyncId != -1) {
            mc.method_1562().method_52787(new class_2815(this.closeScreenSyncId));
        }
        if (mc.field_1755 != null) {
            mc.field_1755.method_25419();
        }
        this.pendingClose = false;
        this.inventoryOpened = false;
        this.closeScreenSyncId = -1;
    }

    private void startLegitMovement() {
        this.wasForwardPressed = this.isKeyPressed(mc.field_1690.field_1894.method_1429().method_1444());
        this.wasBackPressed = this.isKeyPressed(mc.field_1690.field_1881.method_1429().method_1444());
        this.wasLeftPressed = this.isKeyPressed(mc.field_1690.field_1913.method_1429().method_1444());
        this.wasRightPressed = this.isKeyPressed(mc.field_1690.field_1849.method_1429().method_1444());
        this.wasJumpPressed = this.isKeyPressed(mc.field_1690.field_1903.method_1429().method_1444());
        this.movePhase = MovePhase.ALLOW_MOVEMENT;
        this.keysOverridden = false;
        this.packetsHeld = false;
        this.pendingClose = false;
        this.closeScreenSyncId = -1;
    }

    private void handleMovementStates() {
        long elapsed = System.currentTimeMillis() - this.actionStartTime;
        switch (this.movePhase.ordinal()) {
            case 1:
                if (!(this.isServerScreen()) && this.shouldSkipExecution()) {
                    this.updateMoveKeys();
                }
            case 6:
                if (this.keysOverridden) {
                    this.restoreKeyStates();
                }
                if (mc.field_1724 != null) {
                    if (elapsed > 1L) {
                        if (this.isKeyPressed(mc.field_1690.field_1894.method_1429().method_1444())) {
                            if (!mc.field_1724.method_5624()) {
                                mc.field_1724.method_5728(true);
                            }
                        }
                    }
                }
                if (!(elapsed <= 1L)) {
                    this.movePhase = MovePhase.FINISHED;
                }
            case 7:
                if (!(elapsed < (long)this.currentDelay)) {
                    this.movement.restoreFromCurrent();
                }
                this.movePhase = MovePhase.FINISHED;
            case 9:
                this.resetState();
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            default:
                return;
        }
    }

    private void restoreKeyStates() {
        mc.field_1690.field_1894.method_23481(this.wasForwardPressed && this.isKeyPressed(mc.field_1690.field_1894.method_1429().method_1444()));
        mc.field_1690.field_1881.method_23481(this.wasBackPressed && this.isKeyPressed(mc.field_1690.field_1881.method_1429().method_1444()));
        mc.field_1690.field_1913.method_23481(this.wasLeftPressed && this.isKeyPressed(mc.field_1690.field_1913.method_1429().method_1444()));
        mc.field_1690.field_1849.method_23481(this.wasRightPressed && this.isKeyPressed(mc.field_1690.field_1849.method_1429().method_1444()));
        mc.field_1690.field_1903.method_23481(this.wasJumpPressed && this.isKeyPressed(mc.field_1690.field_1903.method_1429().method_1444()));
        this.keysOverridden = false;
    }

    private void resetState() {
        if (this.movement.isBlocked()) {
            this.movement.restoreFromCurrent();
        }
        if (this.keysOverridden) {
            this.restoreKeyStates();
        }
        this.movePhase = MovePhase.READY;
        this.inventoryOpened = false;
        this.packetsHeld = false;
        this.pendingClose = false;
        this.closeScreenSyncId = -1;
        this.packets.clear();
    }

    @Subscribe
    public void onClickSlot(ClickSlotEvent e) {
        if (this.mode.getValue().equals("Legit")) {
            class_1713 actionType = e.getActionType();
            if (this.packetsHeld || MoveUtil.hasPlayerMovement()) {
                if (e.getButton() != 1 || !(actionType.equals(class_1713.field_7791))) {
                    if (actionType.equals(class_1713.field_7793)) {
                        e.cancel();
                    }
                }
            }
        }
    }

    @Subscribe
    public void onCloseScreen(CloseScreenEvent e) {
        if (!this.mode.getValue().equals("Legit")) {
            return;
        }
        if (this.movePhase != MovePhase.ALLOW_MOVEMENT) {
            return;
        }
        if (!this.shouldSkipExecution()) {
            return;
        }
        boolean needsStop = this.grimBypass.getEnabled().booleanValue() && MoveUtil.hasPlayerMovement();
        boolean hasPackets = this.packetsHeld;
        if (!(needsStop)) {
            if (hasPackets) {
            }
        }
        e.cancel();
        this.pendingClose = true;
        this.closeScreenSyncId = mc.field_1724 != null ? mc.field_1724.field_7512.field_7763 : 0;
        if (needsStop) {
            this.movePhase = MovePhase.STOPPING;
        } else {
            this.movePhase = MovePhase.SLOWING_DOWN;
        }
        this.actionStartTime = System.currentTimeMillis();
    }

    private boolean isKeyPressed(int keyCode) {
        return class_3675.method_15987(mc.method_22683().method_4490(), keyCode);
    }

    private void updateMoveKeys() {
        mc.field_1690.field_1894.method_23481(this.isKeyPressed(mc.field_1690.field_1894.method_1429().method_1444()));
        mc.field_1690.field_1881.method_23481(this.isKeyPressed(mc.field_1690.field_1881.method_1429().method_1444()));
        mc.field_1690.field_1913.method_23481(this.isKeyPressed(mc.field_1690.field_1913.method_1429().method_1444()));
        mc.field_1690.field_1849.method_23481(this.isKeyPressed(mc.field_1690.field_1849.method_1429().method_1444()));
        mc.field_1690.field_1903.method_23481(this.isKeyPressed(mc.field_1690.field_1903.method_1429().method_1444()));
    }

    private boolean shouldSkipExecution() {
        return mc.field_1755 != null && !(mc.field_1755 instanceof class_408) && !(mc.field_1755 instanceof ClickGuiScreen) && !(mc.field_1755 instanceof class_498) && !(mc.field_1755 instanceof class_471) && !(mc.field_1755 instanceof class_463) && !(mc.field_1755 instanceof class_497);
    }

    private boolean isServerScreen() {
        return mc.field_1724 != null && mc.field_1724.field_7512.field_7761.size() != 46;
    }

    private void updateSlots() {
        if (mc.field_1724 == null || mc.field_1761 == null) {
            return;
        }
        mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 0, 0, class_1713.field_7793, mc.field_1724);
    }
}
