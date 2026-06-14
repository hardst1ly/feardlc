/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.movement;

import fear.dlc.Api;
import fear.dlc.api.events.impl.InputEvent;
import fear.dlc.utility.keyboard.KeyBoardUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_10185;
import net.minecraft.class_304;

public final class MovementFreeze
implements Api {
    private static int freezeTicks = 0;
    private static List<class_304> movementKeys;

    private MovementFreeze() {
    }

    public static void request(int ticks) {
        if (ticks <= 0 || mc.field_1724 == null || mc.field_1690 == null) {
            return;
        }
        freezeTicks = Math.max(freezeTicks, ticks);
        MovementFreeze.applyBlock();
    }

    public static void tick() {
        if (freezeTicks <= 0) {
            return;
        }
        MovementFreeze.applyBlock();
        freezeTicks = freezeTicks - 1;
        if (freezeTicks == 0) {
            MovementFreeze.syncMovementKeysFromKeyboard();
        }
    }

    public static void onInput(InputEvent event) {
        if (freezeTicks <= 0) {
            return;
        }
        event.setDirectionalLow(false, false, false, false);
        event.setJumping(false);
    }

    public static boolean isActive() {
        return freezeTicks > 0;
    }

    private static void applyBlock() {
        MovementFreeze.blockMovementKeys();
        class_10185 current = mc.field_1724.field_3913.field_54155;
        if (mc.field_1724 != null && mc.field_1724.field_3913 != null) {
            mc.field_1724.field_3913.field_54155 = new class_10185(false, false, false, false, false, current.comp_3164(), current.comp_3165());
        }
    }

    private static void ensureMovementKeys() {
        if (movementKeys != null) {
            return;
        }
        movementKeys = new ArrayList();
        movementKeys.add(mc.field_1690.field_1894);
        movementKeys.add(mc.field_1690.field_1881);
        movementKeys.add(mc.field_1690.field_1913);
        movementKeys.add(mc.field_1690.field_1849);
        movementKeys.add(mc.field_1690.field_1903);
        movementKeys.add(mc.field_1690.field_1832);
    }

    private static void syncMovementKeysFromKeyboard() {
        MovementFreeze.ensureMovementKeys();
        for (class_304 key : movementKeys) {
            key.method_23481(KeyBoardUtil.isKeyPressed(key));
        }
    }

    private static void blockMovementKeys() {
        MovementFreeze.ensureMovementKeys();
        for (class_304 key : movementKeys) {
            key.method_23481(false);
        }
    }
}
