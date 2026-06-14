/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.booleanSetting;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.util.function.Supplier;
import net.minecraft.class_332;

public class BooleanSettingComponent
extends SettingComponent {
    Supplier<String> descriptionText;

    public BooleanSettingComponent(SettingLayer settingLayer) {
        super(settingLayer);
        this.descriptionText = Suppliers.memoize(this::lambda$new$0);
    }

    public void init() {
        float moduleNameHeight = Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f);
        float descriptionHeight = Api.inter().getHeight((String)this.descriptionText.get(), 6f);
        this.size(110f, moduleNameHeight + 5f + descriptionHeight);
    }

    public BooleanSettingComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        float animation = this.getSettingLayer().getAnimation().getOutput().floatValue();
        ((BuiltText)Api.text().size(7f).color(ColorUtility.applyOpacity(-1, 95)).text(this.getSettingLayer().getName().getString()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        if (!((String)this.descriptionText.get()).isEmpty()) {
            ((BuiltText)Api.text().size(6f).color(ColorUtility.applyOpacity(-1, 50)).text((String)this.descriptionText.get()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() + Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f) + 5f);
        }
        ((BuiltText)Api.text().text("B").size(8f).font(Api.icons()).color(ColorUtility.applyOpacity(-1, (int)(100f * animation))).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + this.getWidth() - 8f - 5f * animation, this.getY() + (this.getHeight() - 8f) / 2f);
        return null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
            BooleanSetting booleanSetting = this.getSettingLayer();
            booleanSetting.set(!booleanSetting.getEnabled().booleanValue());
            return true;
        }
        return false;
    }
}
