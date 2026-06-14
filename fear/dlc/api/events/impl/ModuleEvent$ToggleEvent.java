/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.impl.ModuleEvent;
import fear.dlc.modules.more.ModuleLayer;

public static class ModuleEvent.ToggleEvent
extends ModuleEvent {
    private final ModuleLayer moduleLayer;

    public ModuleLayer getModuleLayer() {
        return this.moduleLayer;
    }

    public ModuleEvent.ToggleEvent(ModuleLayer moduleLayer) {
        this.moduleLayer = moduleLayer;
    }
}
