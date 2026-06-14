/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.player;

import fear.dlc.modules.settings.impl.BindSetting;
import net.minecraft.class_1792;

private static class ServerAssistant.AbilityBind {
    String name;
    class_1792 item;
    String server;
    BindSetting keySetting;

    ServerAssistant.AbilityBind(String name, class_1792 item, String server, BindSetting keySetting) {
        this.name = name;
        this.item = item;
        this.server = server;
        this.keySetting = keySetting;
    }
}
