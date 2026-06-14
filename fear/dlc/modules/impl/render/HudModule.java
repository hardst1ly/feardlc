/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.ModeListSetting;
import net.minecraft.class_2561;

public class HudModule
extends ModuleLayer {
    ModeListSetting visible;

    public HudModule() {
        super(class_2561.method_30163("HUD"), null, Category.Render);
        String[] tmp0 = new String[5];
        tmp0[0] = "Watermark";
        tmp0[1] = "Keybinds";
        tmp0[2] = "Target";
        tmp0[3] = "Notifications";
        tmp0[4] = "Potions";
        this.visible = new ModeListSetting(class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c"), null, HudModule::lambda$new$0).set(tmp0).register(this);
    }

    public ModeListSetting getVisible() {
        return this.visible;
    }
}
