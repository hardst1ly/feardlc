/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import net.minecraft.class_2561;

public class NoRender
extends ModuleLayer {
    public static boolean onSelfDestruct = false;
    public BooleanSetting fire;
    public BooleanSetting water;
    public BooleanSetting wall;

    public void onSelfDestruct() {
        super.deactivate();
    }

    public NoRender() {
        super(class_2561.method_30163("NoRender"), class_2561.method_30163("\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u043b\u0438\u0448\u043d\u0438\u0435 \u043e\u0432\u0435\u0440\u043b\u0435\u0439."), Category.Render);
        this.fire = new BooleanSetting(class_2561.method_30163("Fire"), null, NoRender::lambda$new$0).set(true).register(this);
        this.water = new BooleanSetting(class_2561.method_30163("Water"), null, NoRender::lambda$new$1).set(true).register(this);
        this.wall = new BooleanSetting(class_2561.method_30163("Wall"), null, NoRender::lambda$new$2).set(false).register(this);
    }

    public void activate() {
        super.activate();
    }

    public void deactivate() {
        super.deactivate();
    }
}
