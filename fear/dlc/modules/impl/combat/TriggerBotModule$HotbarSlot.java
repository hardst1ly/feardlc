/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

import net.minecraft.class_1799;

private static class TriggerBotModule.HotbarSlot {
    private final int slotId;
    private final class_1799 stack;

    public TriggerBotModule.HotbarSlot(int slotId, class_1799 stack) {
        this.slotId = slotId;
        this.stack = stack;
    }

    public int getSlotId() {
        return this.slotId;
    }
}
