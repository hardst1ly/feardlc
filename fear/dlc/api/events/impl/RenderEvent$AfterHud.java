/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.impl.RenderEvent;
import net.minecraft.class_332;
import net.minecraft.class_9779;

public static class RenderEvent.AfterHud
extends RenderEvent {
    class_332 context;
    class_9779 tickCounter;

    public class_332 getContext() {
        return this.context;
    }

    public class_9779 getTickCounter() {
        return this.tickCounter;
    }

    public RenderEvent.AfterHud(class_332 context, class_9779 tickCounter) {
        this.context = context;
        this.tickCounter = tickCounter;
    }
}
