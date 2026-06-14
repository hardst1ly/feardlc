/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.impl.RenderEvent;
import net.minecraft.class_10017;
import net.minecraft.class_1297;

public static class RenderEvent.RenderLabelsEvent<T extends class_1297, S extends class_10017>
extends RenderEvent {
    S state;

    public S getState() {
        return this.state;
    }

    public RenderEvent.RenderLabelsEvent(S state) {
        this.state = state;
    }
}
