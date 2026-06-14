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
import fear.dlc.utility.render.SpaceShaderRenderer;
import java.awt.Color;
import net.minecraft.class_10142;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_265;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_3965;
import net.minecraft.class_4587;
import org.joml.Matrix4f;

public class BlockOutlineModule
extends ModuleLayer {
    private final ModeSetting mode;
    private final ColorSetting outlineColor;
    private final ColorSetting fillColor;
    private final SliderSetting fillAlpha;
    private final SliderSetting outlineWidth;
    private final BooleanSetting expandBox;
    private final SliderSetting expandAmount;
    private final BooleanSetting throughWalls;
    private final BooleanSetting animateShader;
    private final SliderSetting shaderAlpha;
    private final SliderSetting animationSpeed;
    private long shaderStartTime;

    public BlockOutlineModule() {
        super(class_2561.method_30163("Block Outline"), class_2561.method_30163("\u041a\u0440\u0430\u0441\u0438\u0432\u0430\u044f \u043e\u0431\u0432\u043e\u0434\u043a\u0430 \u0431\u043b\u043e\u043a\u043e\u0432"), Category.Render);
        String[] tmp0 = new String[4];
        tmp0[0] = "\u0421\u0442\u0430\u043d\u0434\u0430\u0440\u0442\u043d\u0430\u044f \u043e\u0431\u0432\u043e\u0434\u043a\u0430";
        tmp0[1] = "\u0417\u0430\u043b\u0438\u0432\u043a\u0430";
        tmp0[2] = "\u0417\u0430\u043b\u0438\u0432\u043a\u0430+\u041e\u0431\u0432\u043e\u0434\u043a\u0430";
        tmp0[3] = "\u041a\u043e\u0441\u043c\u0438\u0447\u0435\u0441\u043a\u0438\u0439 \u0448\u0435\u0439\u0434\u0435\u0440";
        this.mode = new ModeSetting(class_2561.method_30163("\u0420\u0435\u0436\u0438\u043c"), null, BlockOutlineModule::lambda$new$0).set(tmp0).set("\u0421\u0442\u0430\u043d\u0434\u0430\u0440\u0442\u043d\u0430\u044f \u043e\u0431\u0432\u043e\u0434\u043a\u0430").register(this);
        this.outlineColor = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442 \u043e\u0431\u0432\u043e\u0434\u043a\u0438"), class_2561.method_30163("\u0426\u0432\u0435\u0442 \u043b\u0438\u043d\u0438\u0439 \u043e\u0431\u0432\u043e\u0434\u043a\u0438"), this::lambda$new$1).set(-1).register(this);
        this.fillColor = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442 \u0437\u0430\u043b\u0438\u0432\u043a\u0438"), class_2561.method_30163("\u0426\u0432\u0435\u0442 \u0437\u0430\u043b\u0438\u0432\u043a\u0438 \u0431\u043b\u043e\u043a\u0430"), this::lambda$new$2).set(-16711681).register(this);
        this.fillAlpha = new SliderSetting(class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u0437\u0430\u043b\u0438\u0432\u043a\u0438"), null, this::lambda$new$3).set(10f, 255f, 5f).set(80f).register(this);
        this.outlineWidth = new SliderSetting(class_2561.method_30163("\u0422\u043e\u043b\u0449\u0438\u043d\u0430 \u043e\u0431\u0432\u043e\u0434\u043a\u0438"), null, this::lambda$new$4).set(1f, 5f, 0.5f).set(2f).register(this);
        this.expandBox = new BooleanSetting(class_2561.method_30163("\u0420\u0430\u0441\u0448\u0438\u0440\u0438\u0442\u044c \u0431\u043e\u043a\u0441"), class_2561.method_30163("\u041d\u0435\u043c\u043d\u043e\u0433\u043e \u0440\u0430\u0441\u0448\u0438\u0440\u044f\u0435\u0442 \u043e\u0431\u0432\u043e\u0434\u043a\u0443 \u0434\u043b\u044f \u043b\u0443\u0447\u0448\u0435\u0433\u043e \u0432\u0438\u0434\u0430"), BlockOutlineModule::lambda$new$5).set(true).register(this);
        this.expandAmount = new SliderSetting(class_2561.method_30163("\u0420\u0430\u0437\u043c\u0435\u0440 \u0440\u0430\u0441\u0448\u0438\u0440\u0435\u043d\u0438\u044f"), null, this::lambda$new$6).set(0.001f, 0.1f, 0.001f).set(0.005f).register(this);
        this.throughWalls = new BooleanSetting(class_2561.method_30163("\u0427\u0435\u0440\u0435\u0437 \u0441\u0442\u0435\u043d\u044b"), class_2561.method_30163("\u0412\u0438\u0434\u043d\u043e \u0447\u0435\u0440\u0435\u0437 \u0431\u043b\u043e\u043a\u0438"), BlockOutlineModule::lambda$new$7).set(false).register(this);
        this.animateShader = new BooleanSetting(class_2561.method_30163("\u0410\u043d\u0438\u043c\u0430\u0446\u0438\u044f \u0448\u0435\u0439\u0434\u0435\u0440\u0430"), class_2561.method_30163("\u0410\u043d\u0438\u043c\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043a\u043e\u0441\u043c\u0438\u0447\u0435\u0441\u043a\u0438\u0439 \u0448\u0435\u0439\u0434\u0435\u0440"), this::lambda$new$8).set(true).register(this);
        this.shaderAlpha = new SliderSetting(class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u0448\u0435\u0439\u0434\u0435\u0440\u0430"), class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u043a\u043e\u0441\u043c\u0438\u0447\u0435\u0441\u043a\u043e\u0433\u043e \u044d\u0444\u0444\u0435\u043a\u0442\u0430"), this::lambda$new$9).set(10f, 255f, 5f).set(75f).register(this);
        this.animationSpeed = new SliderSetting(class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u0430\u043d\u0438\u043c\u0430\u0446\u0438\u0438"), class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u0430\u043d\u0438\u043c\u0430\u0446\u0438\u0438 \u043a\u043e\u0441\u043c\u0438\u0447\u0435\u0441\u043a\u043e\u0433\u043e \u0448\u0435\u0439\u0434\u0435\u0440\u0430"), this::lambda$new$10).set(0.1f, 5f, 0.1f).set(0.67f).register(this);
        this.shaderStartTime = System.currentTimeMillis();
    }

    public void activate() {
        super.activate();
        this.shaderStartTime = System.currentTimeMillis();
        SpaceShaderRenderer.resetTimer();
    }

    public void deactivate() {
        super.deactivate();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Subscribe
    public void onRender(RenderEvent.AfterHand event) {
        if (!this.getEnabled().booleanValue() || mc.field_1724 == null || mc.field_1687 == null) {
            return;
        }
        class_239 hitResult = mc.field_1765;
        if (hitResult == null || hitResult.method_17783() != class_239.class_240.field_1332) {
            return;
        }
        class_3965 blockHit = hitResult;
        class_2338 blockPos = blockHit.method_17777();
        class_265 shape = mc.field_1687.method_8320(blockPos).method_26218(mc.field_1687, blockPos);
        if (shape.method_1110()) {
            return;
        }
        class_238 box = shape.method_1107().method_996(blockPos);
        if (this.expandBox.getEnabled().booleanValue()) {
            double expand = this.expandAmount.getValue().doubleValue();
            box = box.method_1014(expand);
        }
        class_4587 stack = event.getStack();
        class_243 cameraPos = mc.field_1773.method_19418().method_19326();
        stack.method_22903();
        try {
            this.setupRender();
            String var9 = this.mode.getValue();
            int var10 = -1;
            switch (var9.hashCode()) {
                case 2063670715:
                    if (var9.equals("\u0421\u0442\u0430\u043d\u0434\u0430\u0440\u0442\u043d\u0430\u044f \u043e\u0431\u0432\u043e\u0434\u043a\u0430")) {
                        var10 = 0;
                    }
                case -1132456174:
                    if (var9.equals("\u0417\u0430\u043b\u0438\u0432\u043a\u0430")) {
                        var10 = 1;
                    }
                case 1914694450:
                    if (var9.equals("\u0417\u0430\u043b\u0438\u0432\u043a\u0430+\u041e\u0431\u0432\u043e\u0434\u043a\u0430")) {
                        var10 = 2;
                    }
                case 404670706:
                    if (var9.equals("\u041a\u043e\u0441\u043c\u0438\u0447\u0435\u0441\u043a\u0438\u0439 \u0448\u0435\u0439\u0434\u0435\u0440")) {
                        var10 = 3;
                    }
                default:
                    switch (var10) {
                        case 0:
                            this.renderStandardOutline(stack, box, cameraPos);
                            break;
                        case 1:
                            this.renderFilled(stack, box, cameraPos);
                            break;
                        case 2:
                            this.renderFilledWithOutline(stack, box, cameraPos);
                            break;
                        case 3:
                            this.renderSpaceShader(stack, box, cameraPos);
                        default:
                    }
            }
        }
        finally {
            stack.method_22909();
            this.restoreRender();
            throw var11;
        }
    }

    private void renderStandardOutline(class_4587 stack, class_238 box, class_243 cameraPos) {
        class_289 tessellator = class_289.method_1348();
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_29344, class_290.field_1576);
        Matrix4f matrix = stack.method_23760().method_23761();
        int color = this.outlineColor.getColorRGB();
        int layers = this.outlineWidth.getValue().floatValue();
        for (int layer = 0; layer < layers; layer++) {
            double layerExpand = layer * 0.002;
            class_238 layerBox = box.method_1014(layerExpand);
            this.drawBoxOutline(buffer, matrix, layerBox, cameraPos, color);
        }
        class_286.method_43433(buffer.method_60800());
    }

    private void renderFilled(class_4587 stack, class_238 box, class_243 cameraPos) {
        class_289 tessellator = class_289.method_1348();
        Matrix4f matrix = stack.method_23760().method_23761();
        int color = this.fillColor.getColorRGB();
        int alpha = this.fillAlpha.getValue().intValue();
        int finalColor = color & 16777215 | alpha << 24;
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        this.drawBoxFilled(buffer, matrix, box, cameraPos, finalColor);
        class_286.method_43433(buffer.method_60800());
    }

    private void renderFilledWithOutline(class_4587 stack, class_238 box, class_243 cameraPos) {
        this.renderFilled(stack, box, cameraPos);
        this.renderStandardOutline(stack, box, cameraPos);
    }

    private void renderSpaceShader(class_4587 stack, class_238 box, class_243 cameraPos) {
        try {
            int alpha = this.shaderAlpha.getValue().intValue();
            float speed = this.animationSpeed.getValue().floatValue();
            SpaceShaderRenderer.renderSpaceBox(stack, box, cameraPos, this.animateShader.getEnabled().booleanValue(), alpha, speed);
        }
        catch (Exception e) {
            this.renderFallbackSpaceEffect(stack, box, cameraPos);
            return;
        }
    }

    private void renderFallbackSpaceEffect(class_4587 stack, class_238 box, class_243 cameraPos) {
        class_289 tessellator = class_289.method_1348();
        Matrix4f matrix = stack.method_23760().method_23761();
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        this.drawBoxFilledGradient(buffer, matrix, box, cameraPos);
        class_286.method_43433(buffer.method_60800());
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

    private void drawBoxFilledGradient(class_287 buffer, Matrix4f matrix, class_238 box, class_243 cameraPos) {
        float x1 = (box.field_1323 - cameraPos.field_1352);
        float y1 = (box.field_1322 - cameraPos.field_1351);
        float z1 = (box.field_1321 - cameraPos.field_1350);
        float x2 = (box.field_1320 - cameraPos.field_1352);
        float y2 = (box.field_1325 - cameraPos.field_1351);
        float z2 = (box.field_1324 - cameraPos.field_1350);
        long time = System.currentTimeMillis();
        float hue1 = (time % 5000L) / 5000f;
        float hue2 = ((float)(time % 5000L) / 5000f + 0.3f) % 1f;
        float hue3 = ((float)(time % 5000L) / 5000f + 0.6f) % 1f;
        int color1 = Color.HSBtoRGB(hue1, 0.8f, 0.9f) | -1442840576;
        int color2 = Color.HSBtoRGB(hue2, 0.8f, 0.9f) | -1442840576;
        int color3 = Color.HSBtoRGB(hue3, 0.8f, 0.9f) | -1442840576;
        this.addQuadGradient(buffer, matrix, x1, y1, z1, x2, y1, z1, x2, y1, z2, x1, y1, z2, color1, color2);
        this.addQuadGradient(buffer, matrix, x1, y2, z2, x2, y2, z2, x2, y2, z1, x1, y2, z1, color2, color3);
        this.addQuadGradient(buffer, matrix, x1, y1, z1, x1, y2, z1, x2, y2, z1, x2, y1, z1, color1, color3);
        this.addQuadGradient(buffer, matrix, x2, y1, z2, x2, y2, z2, x1, y2, z2, x1, y1, z2, color2, color1);
        this.addQuadGradient(buffer, matrix, x1, y1, z2, x1, y2, z2, x1, y2, z1, x1, y1, z1, color3, color2);
        this.addQuadGradient(buffer, matrix, x2, y1, z1, x2, y2, z1, x2, y2, z2, x2, y1, z2, color1, color3);
    }

    private void addQuad(class_287 buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, int color) {
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color);
        buffer.method_22918(matrix, x2, y2, z2).method_39415(color);
        buffer.method_22918(matrix, x3, y3, z3).method_39415(color);
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color);
        buffer.method_22918(matrix, x3, y3, z3).method_39415(color);
        buffer.method_22918(matrix, x4, y4, z4).method_39415(color);
    }

    private void addQuadGradient(class_287 buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, int color1, int color2) {
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color1);
        buffer.method_22918(matrix, x2, y2, z2).method_39415(color2);
        buffer.method_22918(matrix, x3, y3, z3).method_39415(color1);
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color2);
        buffer.method_22918(matrix, x3, y3, z3).method_39415(color1);
        buffer.method_22918(matrix, x4, y4, z4).method_39415(color2);
    }

    private void drawLine(class_287 buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, int color) {
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color);
        buffer.method_22918(matrix, x2, y2, z2).method_39415(color);
    }

    private void setupRender() {
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        if (this.throughWalls.getEnabled().booleanValue()) {
            RenderSystem.disableDepthTest();
        }
        RenderSystem.setShader(class_10142.field_53876);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.setShaderTexture(0, 0);
        RenderSystem.lineWidth(this.outlineWidth.getValue().floatValue());
    }

    private void restoreRender() {
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(1f);
        RenderSystem.setShader(class_10142.field_53876);
    }
}
