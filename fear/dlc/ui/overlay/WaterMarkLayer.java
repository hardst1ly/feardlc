/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.api.draggable.DraggableLayer;
import fear.dlc.modules.impl.render.HudModule;
import fear.dlc.ui.overlay.OverlayRenderer;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import fear.dlc.utility.render.renderers.impl.BuiltTexture;
import java.io.InputStream;
import java.util.function.Supplier;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_332;
import net.minecraft.class_9779;

public class WaterMarkLayer
extends DraggableLayer {
    static Supplier<HudModule> module = Suppliers.memoize(WaterMarkLayer::lambda$static$0);
    private class_1044 logoTexture = null;

    public WaterMarkLayer() {
        super(10f, 10f, 160f, 24f, WaterMarkLayer::lambda$new$1);
    }

    private void loadLogoTexture() {
        if (this.logoTexture != null) {
            return;
        }
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/pasxalka/icon.png");
            if (inputStream == null) {
                System.err.println("Icon.png not found!");
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            class_1011 image = class_1011.method_4309(inputStream);
            inputStream.close();
            this.logoTexture = new class_1043(image);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void render(class_332 context, double mouseX, double mouseY, class_9779 tickCounter) {
        String fpsString = String.valueOf(mc.method_47599());
        String nameString = mc.field_1724 != null ? mc.field_1724.method_5477().getString() : "Player";
        String pingString = mc.method_1562() != null && mc.method_1562().method_2871(mc.field_1724.method_5667()) != null ? mc.method_1562().method_2871(mc.field_1724.method_5667()).method_2959() + " ms" : "0 ms";
        float fpsWidth = Api.inter().getWidth(fpsString, 7f);
        float nameWidth = Api.inter().getWidth(nameString, 7f);
        float pingWidth = Api.inter().getWidth(pingString, 7f);
        float logoSize = 15f;
        float padding = 7f;
        float spacing = 5f;
        float iconWidth = 10f;
        this.setWidth(37f + fpsWidth + 5f + 10f + nameWidth + 5f + 10f + pingWidth + 7f);
        this.setHeight(24f);
        OverlayRenderer.rect(context, this.getX().floatValue(), this.getY().floatValue(), this.getWidth().floatValue(), this.getHeight().floatValue());
        float currentX = this.getX().floatValue() + 7f;
        this.loadLogoTexture();
        if (this.logoTexture != null) {
            ((BuiltTexture)Api.texture().size(new SizeState(15f, 15f)).radius(new QuadRadiusState(6f)).texture(0f, 0f, 1f, 1f, this.logoTexture).color(new QuadColorState(-1)).build()).render(context.method_51448().method_23760().method_23761(), currentX, this.getY().floatValue() + (this.getHeight().floatValue() - 15f) / 2f);
        }
        currentX = currentX + 20f;
        ((BuiltText)Api.text().text("A").font(Api.hudIcons()).color(-3618616).size(8f).build()).render(context.method_51448().method_23760().method_23761(), currentX, this.getY().floatValue() + (this.getHeight().floatValue() - Api.hudIcons().getHeight("A", 8f)) / 2f);
        currentX = currentX + 10f;
        ((BuiltText)Api.text().size(7f).color(-855310).text(fpsString).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), currentX, this.getY().floatValue() + this.getHeight().floatValue() / 2f - Api.inter().getHeight(fpsString, 7f) / 2f);
        currentX = currentX + (fpsWidth + 5f);
        ((BuiltText)Api.text().text("B").font(Api.hudIcons()).color(-3618616).size(8f).build()).render(context.method_51448().method_23760().method_23761(), currentX, this.getY().floatValue() + (this.getHeight().floatValue() - Api.hudIcons().getHeight("B", 8f)) / 2f);
        currentX = currentX + 10f;
        ((BuiltText)Api.text().size(7f).color(-855310).text(nameString).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), currentX, this.getY().floatValue() + this.getHeight().floatValue() / 2f - Api.inter().getHeight(nameString, 7f) / 2f);
        currentX = currentX + (nameWidth + 5f);
        ((BuiltText)Api.text().text("C").font(Api.hudIcons()).color(-3618616).size(8f).build()).render(context.method_51448().method_23760().method_23761(), currentX, this.getY().floatValue() + (this.getHeight().floatValue() - Api.hudIcons().getHeight("C", 8f)) / 2f);
        currentX = currentX + 10f;
        ((BuiltText)Api.text().size(7f).color(-855310).text(pingString).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), currentX, this.getY().floatValue() + this.getHeight().floatValue() / 2f - Api.inter().getHeight(pingString, 7f) / 2f);
    }
}
