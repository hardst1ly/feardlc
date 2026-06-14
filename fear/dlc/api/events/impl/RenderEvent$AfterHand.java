/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.impl.RenderEvent;
import net.minecraft.class_4587;
import net.minecraft.class_9779;

public static class RenderEvent.AfterHand
extends RenderEvent {
    class_4587 stack;
    class_9779 tickCounter;

    public class_4587 getStack() {
        return this.stack;
    }

    public class_9779 getTickCounter() {
        return this.tickCounter;
    }

    public RenderEvent.AfterHand(class_4587 stack, class_9779 tickCounter) {
        this.stack = stack;
        this.tickCounter = tickCounter;
    }
}
