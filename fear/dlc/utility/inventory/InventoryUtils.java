/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.inventory;

import fear.dlc.Api;
import java.util.Objects;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1799;

public final class InventoryUtils
implements Api {
    public static void moveTo(int syncId, class_1799 stack, int slot) {
        if (Objects.isNull(stack)) {
            return;
        }
        mc.field_1761.method_2906(syncId, mc.field_1724.method_31548().method_7395(stack), 0, class_1713.field_7790, mc.field_1724);
        mc.field_1761.method_2906(syncId, slot, 0, class_1713.field_7790, mc.field_1724);
    }

    public static class_1799 byItem(class_1792 item) {
        for (int i = 0; i < mc.field_1724.method_31548().method_5439(); i++) {
            class_1799 itemStack = mc.field_1724.method_31548().method_5438(i);
            if (!itemStack.method_7909().equals(item)) continue;
            return itemStack;
        }
        return null;
    }

    public static boolean quickMoveFromTo(int from, int to) {
        if (from > 8) {
            mc.field_1761.method_2906(0, from, 0, class_1713.field_7790, mc.field_1724);
            mc.field_1761.method_2906(0, to, 0, class_1713.field_7790, mc.field_1724);
            mc.field_1761.method_2906(0, from, 0, class_1713.field_7790, mc.field_1724);
        } else {
            mc.field_1761.method_2906(0, from + 36, 0, class_1713.field_7790, mc.field_1724);
            mc.field_1761.method_2906(0, to, 0, class_1713.field_7790, mc.field_1724);
            mc.field_1761.method_2906(0, from + 36, 0, class_1713.field_7790, mc.field_1724);
        }
        return true;
    }
}
