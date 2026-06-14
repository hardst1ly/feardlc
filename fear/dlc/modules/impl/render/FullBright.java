/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import net.minecraft.class_1294;
import net.minecraft.class_2561;

public class FullBright
extends ModuleLayer {
    public FullBright() {
        super(class_2561.method_30163("FullBright"), class_2561.method_30163("\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0438\u0434\u0435\u0442\u044c \u0432 \u0442\u0435\u043c\u043d\u043e\u0442\u0435."), Category.Render);
    }

    public void activate() {
    }

    public void deactivate() {
        this.removeNightVision();
    }

    private void removeNightVision() {
        try {
            if (mc.field_1724 == null) {
                return;
            }
        }
        catch (Exception e1) {
            return;
        }
        try {
            mc.field_1724.method_6016(class_1294.field_5925);
        }
        catch (Exception e1) {
            return;
        }
    }
}
