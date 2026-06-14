/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.EventLayer;
import net.minecraft.class_437;

public class CloseScreenEvent
extends EventLayer {
    private final class_437 screen;

    public CloseScreenEvent(class_437 screen) {
        this.screen = screen;
    }

    public class_437 getScreen() {
        return this.screen;
    }
}
