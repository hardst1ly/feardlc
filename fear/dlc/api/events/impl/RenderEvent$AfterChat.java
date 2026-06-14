/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.impl.RenderEvent;
import net.minecraft.class_332;

public static class RenderEvent.AfterChat
extends RenderEvent {
    class_332 context;
    int mouseX;
    int mouseY;
    float delta;

    public class_332 getContext() {
        return this.context;
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    public float getDelta() {
        return this.delta;
    }

    public RenderEvent.AfterChat(class_332 context, int mouseX, int mouseY, float delta) {
        this.context = context;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.delta = delta;
    }
}
