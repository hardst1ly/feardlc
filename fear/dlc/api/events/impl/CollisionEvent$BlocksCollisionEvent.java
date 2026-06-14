/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.impl.CollisionEvent;
import net.minecraft.class_243;

public static class CollisionEvent.BlocksCollisionEvent
extends CollisionEvent {
    class_243 motion;

    public CollisionEvent.BlocksCollisionEvent(class_243 motion) {
        this.motion = motion;
    }
}
