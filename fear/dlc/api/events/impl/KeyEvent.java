/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.EventLayer;

public class KeyEvent
extends EventLayer {
    private final long window;
    private final int key;
    private final int scancode;
    private final int action;
    private final int modifiers;

    public long getWindow() {
        return this.window;
    }

    public int getKey() {
        return this.key;
    }

    public int getScancode() {
        return this.scancode;
    }

    public int getAction() {
        return this.action;
    }

    public int getModifiers() {
        return this.modifiers;
    }

    public KeyEvent(long window, int key, int scancode, int action, int modifiers) {
        this.window = window;
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.modifiers = modifiers;
    }
}
