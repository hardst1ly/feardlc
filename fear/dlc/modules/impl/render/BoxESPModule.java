/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ColorSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_10142;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_4587;
import org.joml.Matrix4f;

public class BoxESPModule
extends ModuleLayer {
    private final Map<class_1297, Long> lastDamageTime = new HashMap();
    private final Map<class_1297, Float> lastHealth = new HashMap();
    private final BooleanSetting players;
    private final ModeSetting glowMode;
    private final ColorSetting color;
    private final BooleanSetting hurtOnDamage;
    private final SliderSetting glowSize;
    private final SliderSetting glowIntensity;
    private final BooleanSetting throughWalls;
    private final BooleanSetting outline;

    public BoxESPModule() {
        super(class_2561.method_30163("Box ESP"), class_2561.method_30163("\u041a\u0440\u0430\u0441\u0438\u0432\u043e\u0435 \u0441\u0432\u0435\u0447\u0435\u043d\u0438\u0435"), Category.Render);
        this.players = new BooleanSetting(class_2561.method_30163("\u0418\u0433\u0440\u043e\u043a\u0438"), null, BoxESPModule::lambda$new$0).set(true).register(this);
        String[] tmp0 = new String[5];
        tmp0[0] = "\u041a\u043e\u043d\u0442\u0443\u0440";
        tmp0[1] = "\u0417\u0430\u043b\u0438\u0432\u043a\u0430";
        tmp0[2] = "\u0417\u0430\u043b\u0438\u0432\u043a\u0430+\u041a\u043e\u043d\u0442\u0443\u0440";
        tmp0[3] = "\u041f\u0443\u043b\u044c\u0441\u0430\u0446\u0438\u044f";
        tmp0[4] = "\u0420\u0430\u0434\u0443\u0433\u0430";
        this.glowMode = new ModeSetting(class_2561.method_30163("\u0420\u0435\u0436\u0438\u043c \u0441\u0432\u0435\u0447\u0435\u043d\u0438\u044f"), null, BoxESPModule::lambda$new$1).set(tmp0).set("\u0417\u0430\u043b\u0438\u0432\u043a\u0430").register(this);
        this.color = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442"), class_2561.method_30163("\u0426\u0432\u0435\u0442 \u0441\u0432\u0435\u0447\u0435\u043d\u0438\u044f"), this::lambda$new$2).set(-16711681).register(this);
        this.hurtOnDamage = new BooleanSetting(class_2561.method_30163("\u041a\u0440\u0430\u0441\u043d\u044b\u0439 \u043f\u0440\u0438 \u0443\u0440\u043e\u043d\u0435"), class_2561.method_30163("\u041f\u043b\u0430\u0432\u043d\u043e \u043a\u0440\u0430\u0441\u043d\u0435\u0435\u0442 \u043f\u0440\u0438 \u043f\u043e\u043b\u0443\u0447\u0435\u043d\u0438\u0438 \u0443\u0440\u043e\u043d\u0430"), BoxESPModule::lambda$new$3).set(true).register(this);
        this.glowSize = new SliderSetting(class_2561.method_30163("\u0420\u0430\u0437\u043c\u0435\u0440 \u0441\u0432\u0435\u0447\u0435\u043d\u0438\u044f"), null, BoxESPModule::lambda$new$4).set(0.05f, 1f, 0.05f).set(0.06f).register(this);
        this.glowIntensity = new SliderSetting(class_2561.method_30163("\u0418\u043d\u0442\u0435\u043d\u0441\u0438\u0432\u043d\u043e\u0441\u0442\u044c"), null, BoxESPModule::lambda$new$5).set(50f, 255f, 5f).set(100f).register(this);
        this.throughWalls = new BooleanSetting(class_2561.method_30163("\u0427\u0435\u0440\u0435\u0437 \u0441\u0442\u0435\u043d\u044b"), null, BoxESPModule::lambda$new$6).set(true).register(this);
        this.outline = new BooleanSetting(class_2561.method_30163("\u041e\u0431\u0432\u043e\u0434\u043a\u0430"), null, this::lambda$new$7).set(true).register(this);
    }

    public void deactivate() {
        super.deactivate();
        this.lastDamageTime.clear();
        this.lastHealth.clear();
    }

    @Subscribe
    public void onRender(RenderEvent.AfterHand event) {
        if (!this.getEnabled().booleanValue() || mc.field_1724 == null || mc.field_1687 == null) {
            return;
        }
        for (class_1297 entity : mc.field_1687.method_18112()) {
            while (entity == mc.field_1724) {
            }
            while (!this.shouldRender(entity)) {
            }
            if (this.hurtOnDamage.getEnabled().booleanValue()) {
                if (entity instanceof class_1309) {
                    class_1309 livingEntity = entity;
                    float currentHealth = livingEntity.method_6032();
                    if (this.lastHealth.containsKey(entity)) {
                        float previousHealth = ((Float)this.lastHealth.get(entity)).floatValue();
                        if (currentHealth >= previousHealth) continue;
                        this.lastDamageTime.put(entity, System.currentTimeMillis());
                    }
                    this.lastHealth.put(entity, currentHealth);
                }
            }
            this.renderGlowESP(event, entity);
        }
        this.lastDamageTime.keySet().removeIf(BoxESPModule::lambda$onRender$8);
        this.lastHealth.keySet().removeIf(BoxESPModule::lambda$onRender$9);
    }

    private boolean shouldRender(class_1297 entity) {
        return entity instanceof class_1657 && this.players.getEnabled().booleanValue();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void renderGlowESP(RenderEvent.AfterHand event, class_1297 entity) {
        class_243 cameraPos = mc.field_1773.method_19418().method_19326();
        double x = entity.field_6014 + (entity.method_23317() - entity.field_6014) * (double)event.getTickCounter().method_60637(true);
        double y = entity.field_6036 + (entity.method_23318() - entity.field_6036) * (double)event.getTickCounter().method_60637(true);
        double z = entity.field_5969 + (entity.method_23321() - entity.field_5969) * (double)event.getTickCounter().method_60637(true);
        class_238 box = entity.method_5829();
        class_238 renderBox = new class_238(x + box.field_1323 - entity.method_23317(), y + box.field_1322 - entity.method_23318(), z + box.field_1321 - entity.method_23321(), x + box.field_1320 - entity.method_23317(), y + box.field_1325 - entity.method_23318(), z + box.field_1324 - entity.method_23321());
        double expand = this.glowSize.getValue().doubleValue();
        renderBox = renderBox.method_1014(expand);
        int color = this.getGlowColor(entity);
        this.setupGlowRender();
        class_4587 stack = event.getStack();
        stack.method_22903();
        try {
            String var16 = this.glowMode.getValue();
            int var17 = -1;
            switch (var16.hashCode()) {
                case 1032186918:
                    if (var16.equals("\u041a\u043e\u043d\u0442\u0443\u0440")) {
                        var17 = 0;
                    }
                case -1132456174:
                    if (var16.equals("\u0417\u0430\u043b\u0438\u0432\u043a\u0430")) {
                        var17 = 1;
                    }
                case -594603841:
                    if (var16.equals("\u0417\u0430\u043b\u0438\u0432\u043a\u0430+\u041a\u043e\u043d\u0442\u0443\u0440")) {
                        var17 = 2;
                    }
                case 396427801:
                    if (var16.equals("\u041f\u0443\u043b\u044c\u0441\u0430\u0446\u0438\u044f")) {
                        var17 = 3;
                    }
                case 1190764860:
                    if (var16.equals("\u0420\u0430\u0434\u0443\u0433\u0430")) {
                        var17 = 4;
                    }
                default:
                    switch (var17) {
                        case 0:
                            this.renderGlowOutline(stack, renderBox, cameraPos, color);
                            break;
                        case 1:
                            this.renderGlowFilled(stack, renderBox, cameraPos, color);
                            break;
                        case 2:
                            this.renderGlowFilledWithOutline(stack, renderBox, cameraPos, color);
                            break;
                        case 3:
                            this.renderPulsingGlow(stack, renderBox, cameraPos, color);
                            break;
                        case 4:
                            this.renderRainbowGlow(stack, renderBox, cameraPos);
                        default:
                    }
            }
        }
        finally {
            stack.method_22909();
            this.restoreGlowRender();
            throw var18;
        }
    }

    private void renderGlowOutline(class_4587 stack, class_238 box, class_243 cameraPos, int color) {
        class_289 tessellator = class_289.method_1348();
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_29344, class_290.field_1576);
        Matrix4f matrix = stack.method_23760().method_23761();
        for (int layer = 0; layer < 3; layer++) {
            double layerExpand = layer * 0.05;
            class_238 layerBox = box.method_1014(layerExpand);
            int layerAlpha = (this.glowIntensity.getValue().floatValue() / (float)(layer + 1));
            int layerColor = color & 16777215 | layerAlpha << 24;
            this.drawBoxOutline(buffer, matrix, layerBox, cameraPos, layerColor);
        }
        class_286.method_43433(buffer.method_60800());
    }

    private void renderGlowFilled(class_4587 stack, class_238 box, class_243 cameraPos, int color) {
        class_289 tessellator = class_289.method_1348();
        Matrix4f matrix = stack.method_23760().method_23761();
        int alpha = (this.glowIntensity.getValue().floatValue() * 0.3f);
        int fillColor = color & 16777215 | alpha << 24;
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        this.drawBoxFilled(buffer, matrix, box, cameraPos, fillColor);
        class_286.method_43433(buffer.method_60800());
        if (this.outline.getEnabled().booleanValue()) {
            int outlineColor = this.darkenColor(color, 0.6f);
            int outlineAlpha = (this.glowIntensity.getValue().floatValue() * 0.8f);
            outlineColor = outlineColor & 16777215 | outlineAlpha << 24;
            buffer = tessellator.method_60827(class_293.class_5596.field_29344, class_290.field_1576);
            this.drawBoxOutline(buffer, matrix, box, cameraPos, outlineColor);
            class_286.method_43433(buffer.method_60800());
        }
    }

    private void renderGlowFilledWithOutline(class_4587 stack, class_238 box, class_243 cameraPos, int color) {
        class_289 tessellator = class_289.method_1348();
        Matrix4f matrix = stack.method_23760().method_23761();
        int fillAlpha = (this.glowIntensity.getValue().floatValue() * 0.3f);
        int fillColor = color & 16777215 | fillAlpha << 24;
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        this.drawBoxFilled(buffer, matrix, box, cameraPos, fillColor);
        class_286.method_43433(buffer.method_60800());
        this.renderGlowOutline(stack, box, cameraPos, color);
        int outlineColor = this.darkenColor(color, 0.4f);
        int outlineAlpha = (this.glowIntensity.getValue().floatValue() * 0.9f);
        outlineColor = outlineColor & 16777215 | outlineAlpha << 24;
        buffer = tessellator.method_60827(class_293.class_5596.field_29344, class_290.field_1576);
        this.drawBoxOutline(buffer, matrix, box, cameraPos, outlineColor);
        class_286.method_43433(buffer.method_60800());
    }

    private void renderPulsingGlow(class_4587 stack, class_238 box, class_243 cameraPos, int color) {
        long time = System.currentTimeMillis();
        double pulse = Math.sin((double)(time % 2000L) / 2000 * 3.141592653589793 * 2) * 0.5 + 0.5;
        double pulseExpand = pulse * (double)this.glowSize.getValue().floatValue() * 0.5;
        class_238 pulseBox = box.method_1014(pulseExpand);
        int pulseAlpha = ((double)this.glowIntensity.getValue().floatValue() * pulse);
        int pulseColor = color & 16777215 | pulseAlpha << 24;
        class_289 tessellator = class_289.method_1348();
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_29344, class_290.field_1576);
        Matrix4f matrix = stack.method_23760().method_23761();
        this.drawBoxOutline(buffer, matrix, pulseBox, cameraPos, pulseColor);
        class_286.method_43433(buffer.method_60800());
    }

    private void renderRainbowGlow(class_4587 stack, class_238 box, class_243 cameraPos) {
        long time = System.currentTimeMillis();
        float hue = (time % 3000L) / 3000f;
        int rainbowColor = Color.HSBtoRGB(hue, 1f, 1f);
        int alpha = this.glowIntensity.getValue().intValue();
        int color = rainbowColor & 16777215 | alpha << 24;
        this.renderGlowOutline(stack, box, cameraPos, color);
    }

    private void drawBoxOutline(class_287 buffer, Matrix4f matrix, class_238 box, class_243 cameraPos, int color) {
        float x1 = (box.field_1323 - cameraPos.field_1352);
        float y1 = (box.field_1322 - cameraPos.field_1351);
        float z1 = (box.field_1321 - cameraPos.field_1350);
        float x2 = (box.field_1320 - cameraPos.field_1352);
        float y2 = (box.field_1325 - cameraPos.field_1351);
        float z2 = (box.field_1324 - cameraPos.field_1350);
        this.drawLine(buffer, matrix, x1, y1, z1, x2, y1, z1, color);
        this.drawLine(buffer, matrix, x2, y1, z1, x2, y1, z2, color);
        this.drawLine(buffer, matrix, x2, y1, z2, x1, y1, z2, color);
        this.drawLine(buffer, matrix, x1, y1, z2, x1, y1, z1, color);
        this.drawLine(buffer, matrix, x1, y2, z1, x2, y2, z1, color);
        this.drawLine(buffer, matrix, x2, y2, z1, x2, y2, z2, color);
        this.drawLine(buffer, matrix, x2, y2, z2, x1, y2, z2, color);
        this.drawLine(buffer, matrix, x1, y2, z2, x1, y2, z1, color);
        this.drawLine(buffer, matrix, x1, y1, z1, x1, y2, z1, color);
        this.drawLine(buffer, matrix, x2, y1, z1, x2, y2, z1, color);
        this.drawLine(buffer, matrix, x2, y1, z2, x2, y2, z2, color);
        this.drawLine(buffer, matrix, x1, y1, z2, x1, y2, z2, color);
    }

    private void drawBoxFilled(class_287 buffer, Matrix4f matrix, class_238 box, class_243 cameraPos, int color) {
        float x1 = (box.field_1323 - cameraPos.field_1352);
        float y1 = (box.field_1322 - cameraPos.field_1351);
        float z1 = (box.field_1321 - cameraPos.field_1350);
        float x2 = (box.field_1320 - cameraPos.field_1352);
        float y2 = (box.field_1325 - cameraPos.field_1351);
        float z2 = (box.field_1324 - cameraPos.field_1350);
        this.addQuad(buffer, matrix, x1, y1, z1, x2, y1, z1, x2, y1, z2, x1, y1, z2, color);
        this.addQuad(buffer, matrix, x1, y2, z2, x2, y2, z2, x2, y2, z1, x1, y2, z1, color);
        this.addQuad(buffer, matrix, x1, y1, z1, x1, y2, z1, x2, y2, z1, x2, y1, z1, color);
        this.addQuad(buffer, matrix, x2, y1, z2, x2, y2, z2, x1, y2, z2, x1, y1, z2, color);
        this.addQuad(buffer, matrix, x1, y1, z2, x1, y2, z2, x1, y2, z1, x1, y1, z1, color);
        this.addQuad(buffer, matrix, x2, y1, z1, x2, y2, z1, x2, y2, z2, x2, y1, z2, color);
    }

    private void addQuad(class_287 buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, int color) {
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color);
        buffer.method_22918(matrix, x2, y2, z2).method_39415(color);
        buffer.method_22918(matrix, x3, y3, z3).method_39415(color);
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color);
        buffer.method_22918(matrix, x3, y3, z3).method_39415(color);
        buffer.method_22918(matrix, x4, y4, z4).method_39415(color);
    }

    private void drawLine(class_287 buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, int color) {
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color);
        buffer.method_22918(matrix, x2, y2, z2).method_39415(color);
    }

    private int getGlowColor(class_1297 entity) {
        int baseColor = this.color.getColorRGB();
        if (this.hurtOnDamage.getEnabled().booleanValue()) {
            if (this.lastDamageTime.containsKey(entity)) {
                long timeSinceDamage = System.currentTimeMillis() - ((Long)this.lastDamageTime.get(entity)).longValue();
                long transitionDuration = 1000L;
                if (timeSinceDamage < transitionDuration) {
                    float progress = timeSinceDamage / (float)transitionDuration;
                    progress = (1 - Math.pow(1 - (double)progress, 3));
                    int redColor = 16711680;
                    int r1 = redColor >> 16 & 255;
                    int g1 = redColor >> 8 & 255;
                    int b1 = redColor & 255;
                    int r2 = baseColor >> 16 & 255;
                    int g2 = baseColor >> 8 & 255;
                    int b2 = baseColor & 255;
                    int r = ((float)r1 + (float)(r2 - r1) * progress);
                    int g = ((float)g1 + (float)(g2 - g1) * progress);
                    int b = ((float)b1 + (float)(b2 - b1) * progress);
                    return r << 16 | g << 8 | b;
                }
                this.lastDamageTime.remove(entity);
            }
        }
        return baseColor;
    }

    private int darkenColor(int color, float factor) {
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;
        red = ((float)red * factor);
        green = ((float)green * factor);
        blue = ((float)blue * factor);
        return red << 16 | green << 8 | blue;
    }

    private void setupGlowRender() {
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        if (this.throughWalls.getEnabled().booleanValue()) {
            RenderSystem.disableDepthTest();
        }
        RenderSystem.setShader(class_10142.field_53876);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.setShaderTexture(0, 0);
    }

    private void restoreGlowRender() {
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(class_10142.field_53876);
    }
}
