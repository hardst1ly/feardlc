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
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.MathProjection;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.awt.Color;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.class_10017;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_3532;

public class NameTagsModule
extends ModuleLayer {
    private static final WeakHashMap<UUID, float[]> smoothStats = new WeakHashMap();
    private final BooleanSetting armor;
    private final BooleanSetting health;
    private final BooleanSetting showMainHand;
    private final SliderSetting maxDistance;

    public NameTagsModule() {
        super(class_2561.method_30163("Name Tags"), null, Category.Render);
        this.armor = new BooleanSetting(class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u0431\u0440\u043e\u043d\u044e"), null, NameTagsModule::lambda$new$0).set(true).register(this);
        this.health = new BooleanSetting(class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u0437\u0434\u043e\u0440\u043e\u0432\u044c\u0435"), null, NameTagsModule::lambda$new$1).set(true).register(this);
        this.showMainHand = new BooleanSetting(class_2561.method_30163("\u041f\u0440\u0435\u0434\u043c\u0435\u0442 \u0432 \u0440\u0443\u043a\u0435"), null, NameTagsModule::lambda$new$2).set(true).register(this);
        this.maxDistance = new SliderSetting(class_2561.method_30163("\u041c\u0430\u043a\u0441. \u0434\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f"), class_2561.method_30163("\u0418\u0433\u0440\u043e\u043a\u0438 \u0434\u0430\u043b\u044c\u0448\u0435 \u2014 \u043d\u0435 \u0440\u0438\u0441\u0443\u044e\u0442\u0441\u044f"), NameTagsModule::lambda$new$3).set(8f, 256f, 1f).set(96f).register(this);
    }

    @Subscribe
    public void renderLabelsReceived(RenderEvent.RenderLabelsEvent<? extends class_1297, ? extends class_10017> event) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        event.cancel();
    }

    @Subscribe
    public void onAfterHud(RenderEvent.AfterHud event) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        if (mc.field_1687 == null || mc.field_1724 == null) {
            return;
        }
        class_332 context = event.getContext();
        float tickDelta = mc.method_61966().method_60637(false);
        class_243 cameraPos = mc.field_1773.method_19418().method_19326();
        float maxDist = this.maxDistance.getValue().floatValue();
        float maxDistSq = maxDist * maxDist;
        for (class_1657 p : mc.field_1687.method_18456()) {
            while (p == mc.field_1724) {
            }
            while (!p.method_5805()) {
            }
            double dx = p.method_23317() - cameraPos.field_1352;
            double dy = p.method_23318() - cameraPos.field_1351;
            double dz = p.method_23321() - cameraPos.field_1350;
            double distSq = dx * dx + dy * dy + dz * dz;
            while (distSq > (double)maxDistSq) {
            }
            double x = class_3532.method_16436((double)tickDelta, p.field_6014, p.method_23317());
            double y = class_3532.method_16436((double)tickDelta, p.field_6036, p.method_23318());
            double z = class_3532.method_16436((double)tickDelta, p.field_5969, p.method_23321());
            class_243 headPos = new class_243(x, y + (double)p.method_17682() + 0.35, z);
            class_243 head = MathProjection.projectCoordinates(headPos);
            if (head == null) continue;
            if (head.field_1350 < 0) continue;
            while (head.field_1350 > 1) {
            }
            double dist = Math.sqrt(distSq);
            float scale = (5 / Math.max(dist, 5));
            scale = class_3532.method_15363(scale, 0.5f, 2f);
            context.method_51448().method_22903();
            context.method_51448().method_22904(head.field_1352, head.field_1351, 0);
            context.method_51448().method_22905(scale, scale, 1f);
            this.renderNameTag(context, p);
            if (!this.armor.getEnabled().booleanValue()) continue;
            this.drawArmor(context, p);
            if (!this.showMainHand.getEnabled().booleanValue()) continue;
            this.drawMainHand(context, p);
            context.method_51448().method_22909();
        }
    }

    private float[] getOrCreateStats(class_1657 p) {
        return smoothStats.computeIfAbsent(p.method_5667(), NameTagsModule::lambda$getOrCreateStats$4 /* captured: p */);
    }

    private void renderNameTag(class_332 context, class_1657 entity) {
        float[] stats = this.getOrCreateStats(entity);
        stats[0] = stats[0] + (entity.method_6032() - stats[0]) * 0.1f;
        stats[1] = stats[1] + ((float)entity.method_6096() - stats[1]) * 0.1f;
        float currentHp = stats[0];
        String playerName = NameProtect.replaceName(entity.method_5477().getString());
        String prefix = "";
        if (entity.method_5781() != null) {
            if (entity.method_5781().method_1144() != null) {
                if (!entity.method_5781().method_1144().getString().isEmpty()) {
                    prefix = entity.method_5781().method_1144().getString();
                }
            }
        }
        Object[] tmp0 = new Object[1];
        tmp0[0] = currentHp;
        String hpText = this.health.getEnabled().booleanValue() ? " [" + String.format("%.1f", tmp0) + "]" : "";
        float fontSize = 6f;
        float prefixWidth = prefix.isEmpty() ? 0f : (float)mc.field_1772.method_1727(prefix);
        float nameWidth = Api.inter().getWidth(playerName, fontSize, 0.05f, 0f);
        float hpWidth = hpText.isEmpty() ? 0f : Api.inter().getWidth(hpText, fontSize, 0.05f, 0f);
        float spaceWidth = hpText.isEmpty() ? 0f : 4f;
        float totalWidth = prefixWidth + nameWidth + spaceWidth + hpWidth + 12f;
        float h = 16.5f;
        if (this.health.getEnabled().booleanValue()) {
            h = h + 5f;
            if (entity.method_6096() > 0) {
                h = h + 4.5f;
            }
        }
        ((BuiltRectangle)Api.rectangle().size(new SizeState(totalWidth, h)).radius(new QuadRadiusState(4f)).color(new QuadColorState(new Color(0, 0, 0, 153))).build()).render(context.method_51448().method_23760().method_23761(), -totalWidth / 2f, -h / 2f);
        float textX = -totalWidth / 2f + 6f;
        float textY = -h / 2f + 5f;
        if (!prefix.isEmpty()) {
            context.method_51433(mc.field_1772, prefix, (int)textX, (int)textY, -1, true);
            textX = textX + prefixWidth;
        }
        ((BuiltText)Api.text().size(fontSize).color(-1).text(playerName).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), textX, textY);
        textX = textX + (nameWidth + spaceWidth);
        if (!hpText.isEmpty()) {
            int hpColor = this.healthColor(currentHp / Math.max(0.0001f, entity.method_6063()));
            ((BuiltText)Api.text().size(fontSize).color(hpColor).text(hpText).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), textX, textY);
        }
        if (this.health.getEnabled().booleanValue()) {
            this.renderHealthBar(context, entity, totalWidth, h, currentHp);
        }
    }

    private void renderHealthBar(class_332 context, class_1657 entity, float totalWidth, float h, float currentHp) {
        float maxHp = Math.max(0.0001f, entity.method_6063());
        float pct = class_3532.method_15363(currentHp / maxHp, 0f, 1f);
        float barY = -h / 2f + 12f;
        float barX = -totalWidth / 2f + 4f;
        float barW = totalWidth - 8f;
        float barH = 3f;
        ((BuiltRectangle)Api.rectangle().size(new SizeState(barW, barH)).radius(new QuadRadiusState(1.5f)).color(new QuadColorState(ColorUtility.applyOpacity(-16777216, 100))).build()).render(context.method_51448().method_23760().method_23761(), barX, barY);
        if (pct > 0f) {
            ((BuiltRectangle)Api.rectangle().size(new SizeState(barW * pct, barH)).radius(new QuadRadiusState(1.5f)).color(new QuadColorState(this.healthColor(pct))).build()).render(context.method_51448().method_23760().method_23761(), barX, barY);
        }
    }

    private void drawArmor(class_332 context, class_1657 p) {
        class_1304[] tmp0 = new class_1304[4];
        tmp0[0] = class_1304.field_6166;
        tmp0[1] = class_1304.field_6172;
        tmp0[2] = class_1304.field_6174;
        tmp0[3] = class_1304.field_6169;
        class_1304[] slots = tmp0;
        int count = 0;
        float spacing = slots;
        float total = spacing.length;
        for (float startX = 0; startX < total; startX++) {
            class_1304 s = spacing[startX];
            if (p.method_6118(s).method_7960()) continue;
            count++;
        }
        if (count == 0) {
            return;
        }
        spacing = 18f;
        total = count * spacing - 2f;
        startX = -total / 2f;
        float armorY = -30f;
        float curX = startX;
        for (class_1304 s : slots) {
            class_1799 stack = p.method_6118(s);
            if (!(stack.method_7960())) {
                context.method_51448().method_22903();
                context.method_51448().method_46416(curX, armorY, 0f);
                context.method_51423(p, stack, 0, 0, 0);
                context.method_51448().method_22909();
                if (!stack.method_7963()) { /* goto L426; */ }
                float dPct = 1f - (float)stack.method_7919() / (float)stack.method_7936();
                int dColor = dPct > 0.6f ? -16711936 : dPct > 0.3f ? -256 : -65536;
                float bx = curX + 1f;
                float by = armorY + 17f;
                float bw = 14f;
                float bh = 1.5f;
                ((BuiltRectangle)Api.rectangle().size(new SizeState(bw, bh)).radius(new QuadRadiusState(0.5f)).color(new QuadColorState(ColorUtility.applyOpacity(-16777216, 150))).build()).render(context.method_51448().method_23760().method_23761(), bx, by);
                if (dPct <= 0f) continue;
                ((BuiltRectangle)Api.rectangle().size(new SizeState(bw * dPct, bh)).radius(new QuadRadiusState(0.5f)).color(new QuadColorState(dColor)).build()).render(context.method_51448().method_23760().method_23761(), bx, by);
                curX = curX + spacing;
            }
        }
    }

    private void drawMainHand(class_332 context, class_1657 p) {
        class_1799 stack = p.method_6047();
        if (stack == null || stack.method_7960()) {
            return;
        }
        float bgSize = 20f;
        float leftItemOffset = 55f;
        ((BuiltRectangle)Api.rectangle().size(new SizeState(bgSize, bgSize)).radius(new QuadRadiusState(3f)).color(new QuadColorState(new Color(0, 0, 0, 120))).build()).render(context.method_51448().method_23760().method_23761(), -leftItemOffset - bgSize / 2f, -bgSize / 2f);
        context.method_51448().method_22903();
        context.method_51448().method_46416(-leftItemOffset, 0f, 0f);
        context.method_51448().method_22905(1.2f, 1.2f, 1f);
        context.method_51423(p, stack, -8, -8, 0);
        context.method_51448().method_22909();
    }

    private int healthColor(float pct) {
        if (pct > 0.6f) {
            return -16711936;
        }
        if (pct > 0.3f) {
            return -256;
        }
        return -65536;
    }
}
