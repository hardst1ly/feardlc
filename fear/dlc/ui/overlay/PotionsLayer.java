/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.api.draggable.DraggableLayer;
import fear.dlc.modules.impl.render.HudModule;
import fear.dlc.ui.overlay.OverlayRenderer;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1921;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4074;
import net.minecraft.class_408;
import net.minecraft.class_9779;

public class PotionsLayer
extends DraggableLayer {
    static Supplier<HudModule> module = Suppliers.memoize(PotionsLayer::lambda$static$0);
    private static final float ROW_HEIGHT = 20f;
    private static final float ICON_SIZE = 16f;
    private static final float PADDING = 6f;
    private static final float ROW_GAP = 3f;
    private static final float NAME_DURATION_GAP = 14f;

    public PotionsLayer() {
        super(10f, 60f, 110f, 24f, PotionsLayer::lambda$new$1);
    }

    public static boolean shouldOverrideVanilla() {
        try {
            HudModule m = module.get();
            return m != null && m.getEnabled().booleanValue() && m.getVisible().get("Potions").getEnabled().booleanValue();
        }
        catch (Exception e) {
            return false;
        }
    }

    private static boolean hasEffects() {
        return mc.field_1724 != null && !mc.field_1724.method_6026().isEmpty();
    }

    private boolean inChatPreview() {
        return mc.field_1755 instanceof class_408;
    }

    public void render(class_332 context, double mouseX, double mouseY, class_9779 tickCounter) {
        if (mc.field_1724 == null && !this.inChatPreview()) {
            return;
        }
        List<Row> rows = this.collectRows();
        if (rows.isEmpty()) {
            return;
        }
        float nameFontSize = 7f;
        float durationFontSize = 7f;
        float maxRowContentWidth = 0f;
        for (Row r : rows) {
            float nameW = Api.inter().getWidth(r.name, nameFontSize);
            float durW = Api.inter().getWidth(r.duration, durationFontSize);
            float rowW = 22f + nameW + 14f + durW;
            if (rowW <= maxRowContentWidth) continue;
            maxRowContentWidth = rowW;
        }
        totalWidth = 12f + maxRowContentWidth;
        float totalHeight = 12f + (float)rows.size() * 20f + (float)Math.max(0, rows.size() - 1) * 3f;
        this.setWidth(totalWidth);
        this.setHeight(totalHeight);
        OverlayRenderer.rect(context, this.getX().floatValue(), this.getY().floatValue(), this.getWidth().floatValue(), this.getHeight().floatValue());
        class_4074 sprites = mc.method_18505();
        float y = this.getY().floatValue() + 6f;
        for (Row r : rows) {
            this.renderRow(context, sprites, r, this.getX().floatValue() + 6f, y, nameFontSize, durationFontSize);
            y = y + 23f;
        }
    }

    private List<Row> collectRows() {
        List<Row> rows = new ArrayList();
        Collection<class_1293> effects = mc.field_1724 != null ? mc.field_1724.method_6026() : List.of();
        if (!(effects.isEmpty())) {
            List<class_1293> sorted = new ArrayList(effects);
            sorted.sort(Comparator.comparingInt(PotionsLayer::lambda$collectRows$2));
            class_4074 sprites = mc.method_18505();
            for (class_1293 e : sorted) {
                rows.add(new Row(sprites.method_18663(e.method_5579()), this.effectLabel(e), this.durationText(e), !e.method_48559() && e.method_48557(200)));
            }
            return rows;
        }
        if (this.inChatPreview()) {
            class_4074 sprites = mc.method_18505();
            rows.add(new Row(sprites.method_18663(class_1294.field_5910), "\u0421\u0438\u043b\u0430 3", "8:00", false));
            rows.add(new Row(sprites.method_18663(class_1294.field_5904), "\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c 3", "8:00", false));
            rows.add(new Row(sprites.method_18663(class_1294.field_5924), "\u0420\u0435\u0433\u0435\u043d\u0435\u0440\u0430\u0446\u0438\u044f 2", "0:30", false));
        }
        return rows;
    }

    private void renderRow(class_332 context, class_4074 sprites, Row row, float rowX, float rowY, float nameFontSize, float durationFontSize) {
        float iconY = rowY + 2f;
        if (row.sprite != null) {
            context.method_52710(class_1921::method_62277, row.sprite, (int)rowX, (int)iconY, 16, 16, -1);
        }
        int alpha = 220;
        if (row.blinking) {
            float blink = (System.currentTimeMillis() % 600L) / 600f;
            alpha = class_3532.method_48781(blink, 110, 220);
        }
        int textColorMain = ColorUtility.applyOpacity(-1, alpha * 100 / 255);
        int textColorDuration = ColorUtility.applyOpacity(-1, 60);
        float nameHeight = Api.inter().getHeight(row.name, nameFontSize);
        float nameTextY = rowY + (20f - nameHeight) / 2f;
        float nameTextX = rowX + 16f + 6f;
        ((BuiltText)Api.text().size(nameFontSize).font(Api.inter()).color(textColorMain).text(row.name).build()).render(context.method_51448().method_23760().method_23761(), nameTextX, nameTextY);
        float durationWidth = Api.inter().getWidth(row.duration, durationFontSize);
        float durationHeight = Api.inter().getHeight(row.duration, durationFontSize);
        float durationX = this.getX().floatValue() + this.getWidth().floatValue() - 6f - durationWidth;
        float durationY = rowY + (20f - durationHeight) / 2f;
        ((BuiltText)Api.text().size(durationFontSize).font(Api.inter()).color(textColorDuration).text(row.duration).build()).render(context.method_51448().method_23760().method_23761(), durationX, durationY);
    }

    private String effectLabel(class_1293 e) {
        String name = ((class_1291)e.method_5579().comp_349()).method_5560().getString();
        int amp = e.method_5578();
        if (amp > 0) {
            return name + " " + amp + 1;
        }
        return name;
    }

    private String durationText(class_1293 e) {
        if (e.method_48559()) {
            return "\u221e";
        }
        int totalSec = e.method_5584() / 20;
        int minutes = totalSec / 60;
        int seconds = totalSec % 60;
        Object[] tmp0 = new Object[2];
        tmp0[0] = minutes;
        tmp0[1] = seconds;
        return String.format("%d:%02d", tmp0);
    }
}
