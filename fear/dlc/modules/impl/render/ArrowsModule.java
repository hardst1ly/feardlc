/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import com.google.common.eventbus.Subscribe;
import fear.dlc.Api;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.modules.impl.player.NameProtect;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ColorSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.utility.math.MathVector;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import fear.dlc.utility.render.renderers.impl.BuiltTexture;
import fear.dlc.utility.render.utility.TextureUtil;
import net.minecraft.class_241;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_742;
import net.minecraft.class_7833;

public class ArrowsModule
extends ModuleLayer {
    BooleanSetting showNames;
    ColorSetting color;
    SliderSetting arrowOpacity;
    private float currentScale;

    public ArrowsModule() {
        super(class_2561.method_30163("Arrows"), null, Category.Render);
        this.showNames = new BooleanSetting(class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u0438\u043c\u044f"), null, ArrowsModule::lambda$new$0).register(this);
        this.color = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442"), null, ArrowsModule::lambda$new$1).set(-1).register(this);
        this.arrowOpacity = new SliderSetting(class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u0441\u0442\u0440\u0435\u043b\u043e\u043a"), null, ArrowsModule::lambda$new$2).set(0f, 255f, 1f).set(150f).register(this);
        this.currentScale = 1f;
    }

    @Subscribe
    public void renderEvent(RenderEvent.AfterHud renderEvent) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        class_332 context = renderEvent.getContext();
        class_241 movement = mc.field_1724.field_3913.method_3128();
        boolean moving = movement.field_1343 != 0f || movement.field_1342 != 0f;
        float targetScale = moving ? 1.7f : 1f;
        this.currentScale = this.currentScale + (targetScale - this.currentScale) * 0.05f;
        mc.field_1687.method_18456().forEach(this::lambda$renderEvent$3 /* captured: context */);
    }

    private void drawArrowTo(class_332 context, class_742 player) {
        class_4587 ms = context.method_51448();
        float x = mc.method_22683().method_4486() / 2f;
        float y = mc.method_22683().method_4502() / 2f;
        float size = 18f;
        float offset = 36f * this.currentScale;
        int originalColor = this.color.getColor();
        int opacity = this.arrowOpacity.getValue().intValue();
        int colorWithOpacity = originalColor & 16777215 | opacity << 24;
        ms.method_22903();
        ms.method_46416(x, y, 0f);
        ms.method_22907(class_7833.field_40718.rotationDegrees(MathVector.rotationDifference(player) - mc.field_1724.method_36454()));
        ms.method_46416(-x, -y, 0f);
        ((BuiltTexture)Api.texture().size(new SizeState(size, size)).texture(0f, 0f, 1f, 1f, TextureUtil.of("images/triangle.png")).color(new QuadColorState(colorWithOpacity)).build()).render(context.method_51448().method_23760().method_23761(), x - size / 2f, y - size / 2f - offset);
        if (this.showNames.getEnabled().booleanValue()) {
            String playerName = NameProtect.replaceName(player.method_5477().getString());
            int textColorWithOpacity = this.color.getColor() & 16777215 | opacity << 24;
            ((BuiltText)Api.text().text(playerName).size(6f).color(textColorWithOpacity).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), x - Api.inter().getWidth(playerName, 6f) / 2f, y - size / 2f - offset + size + 5f);
        }
        ms.method_22909();
    }
}
