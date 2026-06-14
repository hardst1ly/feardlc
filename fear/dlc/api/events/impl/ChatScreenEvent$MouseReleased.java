/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.impl.ChatScreenEvent;

public static class ChatScreenEvent.MouseReleased
extends ChatScreenEvent {
    private final double mouseX;
    private final double mouseY;
    private final int button;

    public double getMouseX() {
        return this.mouseX;
    }

    public double getMouseY() {
        return this.mouseY;
    }

    public int getButton() {
        return this.button;
    }

    public ChatScreenEvent.MouseReleased(double mouseX, double mouseY, int button) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.button = button;
    }
}
