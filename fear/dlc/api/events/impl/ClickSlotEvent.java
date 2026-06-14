/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.EventLayer;
import net.minecraft.class_1713;

public class ClickSlotEvent
extends EventLayer {
    private final int windowId;
    private final int slotId;
    private final int button;
    private final class_1713 actionType;

    public int getWindowId() {
        return this.windowId;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public int getButton() {
        return this.button;
    }

    public class_1713 getActionType() {
        return this.actionType;
    }

    public ClickSlotEvent(int windowId, int slotId, int button, class_1713 actionType) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.button = button;
        this.actionType = actionType;
    }
}
