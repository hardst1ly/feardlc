/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.inventory;

import fear.dlc.Api;
import fear.dlc.utility.movement.MovementFreeze;
import net.minecraft.class_1713;
import net.minecraft.class_2815;
import net.minecraft.class_2848;

public final class SilentInventoryUtil
implements Api {
    private SilentInventoryUtil() {
    }

    public static void openInventory() {
        if (mc.field_1724 == null || mc.method_1562() == null) {
            return;
        }
        mc.method_1562().method_52787(new class_2848(mc.field_1724, class_2848.class_2849.field_12988));
        MovementFreeze.request(3);
    }

    public static void closeInventory() {
        if (mc.field_1724 == null || mc.method_1562() == null) {
            return;
        }
        mc.method_1562().method_52787(new class_2815(mc.field_1724.field_7512.field_7763));
        MovementFreeze.request(3);
    }

    public static void clickSlot(int slotId, int button, class_1713 type) {
        if (mc.field_1724 == null || mc.field_1761 == null) {
            return;
        }
        mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, slotId, button, type, mc.field_1724);
        MovementFreeze.request(3);
    }

    public static void swapToOffhand(int slotId) {
        SilentInventoryUtil.clickSlot(slotId, 40, class_1713.field_7791);
    }

    public static void swapWithHotbar(int inventorySlot, int hotbarIndex) {
        SilentInventoryUtil.clickSlot(inventorySlot, hotbarIndex, class_1713.field_7791);
    }
}
