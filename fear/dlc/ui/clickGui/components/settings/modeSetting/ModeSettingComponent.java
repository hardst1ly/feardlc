/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.modeSetting;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.FearDCP;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import fear.dlc.ui.clickGui.components.settings.modeSetting.window.ModeSettingWindowComponent;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import fear.dlc.utility.render.utility.MsdfUtil;
import fear.dlc.utility.windows.WindowLayer;
import fear.dlc.utility.windows.WindowRepository;
import java.util.function.Supplier;
import net.minecraft.class_332;

public class ModeSettingComponent
extends SettingComponent {
    Supplier<ModeSetting> modeSetting;
    WindowLayer windowLayer;

    public ModeSettingComponent(SettingLayer settingLayer) {
        super(settingLayer);
        this.modeSetting = Suppliers.memoize(this::lambda$new$0);
        this.windowLayer = new ModeSettingWindowComponent((ModeSetting)this.modeSetting.get());
    }

    public void init() {
        String descriptionText = MsdfUtil.cutString(this.getSettingLayer().getDescription().getString(), 6, 110f - this.windowLayer.getWidth() - 10f);
        this.windowLayer.init();
        this.windowLayer.position(this.getX() + this.getWidth() - this.windowLayer.getWidth(), this.getY() + this.getHeight() / 2f);
        float moduleNameHeight = Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f);
        float descriptionHeight = Api.inter().getHeight(descriptionText, 6f);
        this.size(110f, moduleNameHeight + 5f + descriptionHeight);
    }

    public ModeSettingComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        String descriptionText = MsdfUtil.cutString(this.getSettingLayer().getDescription().getString(), 6, 110f - this.windowLayer.getWidth() - 10f);
        ((BuiltText)Api.text().size(7f).color(ColorUtility.applyOpacity(-1, 95)).text(this.getSettingLayer().getName().getString()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() - 1f);
        if (!descriptionText.isEmpty()) {
            ((BuiltText)Api.text().size(6f).color(ColorUtility.applyOpacity(-1, 50)).text(descriptionText).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() + Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7f) + 4f);
        }
        String valueText = ((ModeSetting)this.modeSetting.get()).getValue() == null ? "N/A" : ((ModeSetting)this.modeSetting.get()).getValue();
        float valueWidth = Api.inter().getWidth(valueText, 6f) + 10f;
        ((BuiltRectangle)Api.rectangle().radius(new QuadRadiusState(2f)).size(new SizeState(valueWidth, 9f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 10))).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + this.getWidth() - valueWidth, this.getY());
        ((BuiltText)Api.text().size(6f).color(ColorUtility.applyOpacity(-1, 100)).text(valueText).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), (double)(this.getX() + this.getWidth() - valueWidth + 5f), (double)this.getY() + 0.5);
        return null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        WindowRepository windowRepository = FearDCP.getInstance().getClickGuiScreen().getWindowRepository();
        windowRepository.push(this.windowLayer);
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight()) && !windowRepository.contains(this.windowLayer)) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
