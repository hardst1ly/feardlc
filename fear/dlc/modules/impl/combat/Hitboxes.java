/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.minecraft.class_2561;

public class Hitboxes
extends ModuleLayer {
    private static Hitboxes instance;
    public SliderSetting hitbox_size;
    public BooleanSetting legit;

    public static float getCurrentHitbox() {
        if (instance != null && instance.getEnabled().booleanValue()) {
            return instance.hitbox_size.getValue().floatValue();
        }
        return 0.6f;
    }

    public static boolean isLegitMode() {
        if (instance != null && instance.getEnabled().booleanValue()) {
            return instance.legit.getEnabled().booleanValue();
        }
        return false;
    }

    public Hitboxes() {
        super(class_2561.method_30163("Hitboxes"), class_2561.method_30163("\u0423\u0432\u0435\u043b\u0438\u0447\u0438\u0432\u0430\u0435\u0442 \u0445\u0438\u0442\u0431\u043e\u043a\u0441 \u0438\u0433\u0440\u043e\u043a\u043e\u0432."), Category.Combat);
        this.hitbox_size = new SliderSetting(class_2561.method_30163("\u0420\u0430\u0437\u043c\u0435\u0440"), null, Hitboxes::lambda$new$0).set(0.6f, 10f, 0.1f).set(0.6f).register(this);
        this.legit = new BooleanSetting(class_2561.method_30163("\u041b\u0435\u0433\u0438\u0442\u043d\u044b\u0439 \u0420\u0435\u0436\u0438\u043c"), null, Hitboxes::lambda$new$1).set(true).register(this);
        instance = this;
    }

    public void deactivate() {
        this.hitbox_size.set(0.6f);
        super.deactivate();
    }

    public void activate() {
        super.activate();
    }
}
