/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.modules.impl.combat.AimAssistModule;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ColorSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.MathVector;
import fear.dlc.utility.render.utility.VertexUtils;
import net.minecraft.class_1297;
import net.minecraft.class_1921;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_7833;
import net.minecraft.class_9848;
import org.joml.Matrix4f;

public class TargetEspModule
extends ModuleLayer {
    private static final class_2960 GLOW_TEX = class_2960.method_60655("pasxalka", "images/glow.png");
    private static final class_1921 GHOST_VISIBLE_LAYER = VertexUtils.getGlowTexturedDepth(GLOW_TEX);
    private static final class_1921 GHOST_XRAY_LAYER = VertexUtils.getGlowTexturedXray(GLOW_TEX);
    public final ModeSetting mode;
    public final SliderSetting size;
    public final SliderSetting opacity;
    public final BooleanSetting rotate;
    public final SliderSetting rotationSpeed;
    public final SliderSetting sizeDush;
    public final SliderSetting dlinaDush;
    public final SliderSetting factorDush;
    public final SliderSetting particleDensityDush;
    public final SliderSetting atomSize;
    public final SliderSetting atomRings;
    public final SliderSetting atomParticles;
    public final SliderSetting atomSpeed;
    public final SliderSetting atomRadius;
    public final ColorSetting color;
    public final ColorSetting twinColor;
    public final BooleanSetting xrayThroughWalls;
    public final SliderSetting xrayAlpha;

    public TargetEspModule() {
        super(class_2561.method_30163("Target ESP"), class_2561.method_30163("\u0412\u0438\u0437\u0443\u0430\u043b\u0438\u0437\u0430\u0446\u0438\u044f \u0446\u0435\u043b\u0438 \u0441 \u0440\u0430\u0437\u043d\u044b\u043c\u0438 \u044d\u0444\u0444\u0435\u043a\u0442\u0430\u043c\u0438"), Category.Render);
        String[] tmp0 = new String[3];
        tmp0[0] = "2D \u0421\u0442\u0430\u043d\u0434\u0430\u0440\u0442";
        tmp0[1] = "\u041f\u0440\u0438\u0437\u0440\u0430\u043a\u0438";
        tmp0[2] = "\u0410\u0442\u043e\u043c\u044b";
        this.mode = new ModeSetting(class_2561.method_30163("\u0420\u0435\u0436\u0438\u043c"), class_2561.method_30163("\u0412\u044b\u0431\u043e\u0440 \u0432\u0438\u0437\u0443\u0430\u043b\u044c\u043d\u043e\u0433\u043e \u0441\u0442\u0438\u043b\u044f"), TargetEspModule::lambda$new$0).set(tmp0).set("\u041f\u0440\u0438\u0437\u0440\u0430\u043a\u0438").register(this);
        this.size = new SliderSetting(class_2561.method_30163("\u0420\u0430\u0437\u043c\u0435\u0440"), class_2561.method_30163("\u0420\u0430\u0437\u043c\u0435\u0440 \u0432\u0438\u0437\u0443\u0430\u043b\u0438\u0437\u0430\u0446\u0438\u0438"), this::lambda$new$1).set(0.3f, 2f, 0.1f).set(0.67f).register(this);
        this.opacity = new SliderSetting(class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c"), class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u044d\u0444\u0444\u0435\u043a\u0442\u0430"), TargetEspModule::lambda$new$2).set(10f, 255f, 5f).set(225f).register(this);
        this.rotate = new BooleanSetting(class_2561.method_30163("\u0412\u0440\u0430\u0449\u0435\u043d\u0438\u0435"), class_2561.method_30163("\u0412\u0440\u0430\u0449\u0430\u0442\u044c \u0432\u0438\u0437\u0443\u0430\u043b\u0438\u0437\u0430\u0446\u0438\u044e"), this::lambda$new$3).set(true).register(this);
        this.rotationSpeed = new SliderSetting(class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u0432\u0440\u0430\u0449\u0435\u043d\u0438\u044f"), null, this::lambda$new$4).set(1f, 20f, 1f).set(5f).register(this);
        this.sizeDush = new SliderSetting(class_2561.method_30163("\u0420\u0430\u0437\u043c\u0435\u0440 \u0447\u0430\u0441\u0442\u0438\u0446"), null, this::lambda$new$5).set(0.1f, 0.4f, 0.01f).set(0.22f).register(this);
        this.dlinaDush = new SliderSetting(class_2561.method_30163("\u0414\u043b\u0438\u043d\u0430 \u044d\u0444\u0444\u0435\u043a\u0442\u0430"), null, this::lambda$new$6).set(1f, 12f, 0.1f).set(6f).register(this);
        this.factorDush = new SliderSetting(class_2561.method_30163("\u0424\u0430\u043a\u0442\u043e\u0440 \u044d\u0444\u0444\u0435\u043a\u0442\u0430"), null, this::lambda$new$7).set(0f, 22f, 0.1f).set(12f).register(this);
        this.particleDensityDush = new SliderSetting(class_2561.method_30163("\u041a\u043e\u043b-\u0432\u043e \u0447\u0430\u0441\u0442\u0438\u0446"), null, this::lambda$new$8).set(1f, 3f, 0.1f).set(1.7f).register(this);
        this.atomSize = new SliderSetting(class_2561.method_30163("\u0420\u0430\u0437\u043c\u0435\u0440 \u0447\u0430\u0441\u0442\u0438\u0446"), null, this::lambda$new$9).set(0.05f, 0.3f, 0.01f).set(0.13f).register(this);
        this.atomRings = new SliderSetting(class_2561.method_30163("\u041a\u043e\u043b-\u0432\u043e \u043e\u0440\u0431\u0438\u0442"), null, this::lambda$new$10).set(2f, 6f, 1f).set(3f).register(this);
        this.atomParticles = new SliderSetting(class_2561.method_30163("\u0427\u0430\u0441\u0442\u0438\u0446 \u043d\u0430 \u043e\u0440\u0431\u0438\u0442\u0443"), null, this::lambda$new$11).set(8f, 80f, 1f).set(36f).register(this);
        this.atomSpeed = new SliderSetting(class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u043e\u0440\u0431\u0438\u0442\u044b"), null, this::lambda$new$12).set(0.1f, 5f, 0.1f).set(1.5f).register(this);
        this.atomRadius = new SliderSetting(class_2561.method_30163("\u0420\u0430\u0434\u0438\u0443\u0441"), class_2561.method_30163("\u041c\u043d\u043e\u0436\u0438\u0442\u0435\u043b\u044c \u0440\u0430\u0434\u0438\u0443\u0441\u0430 \u043e\u0440\u0431\u0438\u0442"), this::lambda$new$13).set(0.5f, 3f, 0.05f).set(1.2f).register(this);
        this.color = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442"), class_2561.method_30163("\u041e\u0441\u043d\u043e\u0432\u043d\u043e\u0439 \u0446\u0432\u0435\u0442"), TargetEspModule::lambda$new$14).set(-9305933).register(this);
        this.twinColor = new ColorSetting(class_2561.method_30163("\u0412\u0442\u043e\u0440\u043e\u0439 \u0446\u0432\u0435\u0442"), class_2561.method_30163("\u0412\u0442\u043e\u0440\u043e\u0439 \u0446\u0432\u0435\u0442 \u0434\u043b\u044f \u0433\u0440\u0430\u0434\u0438\u0435\u043d\u0442\u0430"), this::lambda$new$15).set(-16722689).register(this);
        this.xrayThroughWalls = new BooleanSetting(class_2561.method_30163("\u0421\u043a\u0432\u043e\u0437\u044c \u0441\u0442\u0435\u043d\u044b"), class_2561.method_30163("\u0422\u0443\u0441\u043a\u043b\u044b\u0439 \u043a\u043e\u043d\u0442\u0443\u0440 \u0432\u0438\u0434\u0435\u043d \u0437\u0430 \u0438\u0433\u0440\u043e\u043a\u043e\u043c \u0438 \u0441\u0442\u0435\u043d\u0430\u043c\u0438"), this::lambda$new$16).set(true).register(this);
        this.xrayAlpha = new SliderSetting(class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u0441\u043a\u0432\u043e\u0437\u044c"), class_2561.method_30163("\u041c\u043d\u043e\u0436\u0438\u0442\u0435\u043b\u044c \u0430\u043b\u044c\u0444\u044b \u0434\u043b\u044f \u0441\u043a\u0440\u044b\u0442\u043e\u0439 \u0447\u0430\u0441\u0442\u0438"), this::lambda$new$17).set(0.05f, 1f, 0.05f).set(0.25f).register(this);
    }

    @Subscribe
    public void render(RenderEvent.AfterHand renderEvent) {
        class_1297 target = AimAssistModule.lockedTarget;
        if (!this.getEnabled().booleanValue() || target == null) {
            return;
        }
        class_4587 ms = renderEvent.getStack();
        class_243 cameraPos = mc.field_1773.method_19418().method_19326();
        String var5 = this.mode.getValue();
        int var6 = -1;
        switch (var5.hashCode()) {
            case 146504858:
                if (var5.equals("2D \u0421\u0442\u0430\u043d\u0434\u0430\u0440\u0442")) {
                    var6 = 0;
                }
            case 1539932654:
                if (var5.equals("\u041f\u0440\u0438\u0437\u0440\u0430\u043a\u0438")) {
                    var6 = 1;
                }
            case 994012379:
                if (var5.equals("\u0410\u0442\u043e\u043c\u044b")) {
                    var6 = 2;
                }
            default:
                switch (var6) {
                    case 0:
                        class_243 targetCenter = MathVector.lerpPosition(target).method_1031(0, 1, 0).method_1020(cameraPos);
                        ms.method_22903();
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();
                        RenderSystem.disableDepthTest();
                        this.render2DStandard(ms, targetCenter);
                        RenderSystem.disableBlend();
                        RenderSystem.enableDepthTest();
                        ms.method_22909();
                    case 1:
                        this.renderGhosts(ms, target, cameraPos);
                    case 2:
                        this.renderAtoms(ms, target, cameraPos);
                    default:
                        return;
                }
        }
    }

    private void render2DStandard(class_4587 ms, class_243 targetPos) {
        ms.method_22903();
        ms.method_22904(targetPos.field_1352, targetPos.field_1351, targetPos.field_1350);
        ms.method_22907(class_7833.field_40715.rotationDegrees(mc.field_1724.method_5705(1f)));
        ms.method_22907(class_7833.field_40714.rotationDegrees(mc.field_1724.method_5695(1f)));
        if (this.rotate.getEnabled().booleanValue()) {
            ms.method_22907(class_7833.field_40718.rotationDegrees((float)(System.currentTimeMillis() % 3600L) / this.rotationSpeed.getValue().floatValue()));
        }
        class_4598 vcp = mc.method_22940().method_23000();
        class_4588 vc = vcp.getBuffer(VertexUtils.IMAGE);
        RenderSystem.setShaderTexture(0, class_2960.method_60655("pasxalka", "images/target.png"));
        VertexUtils.drawImageQuad(vc, ms.method_23760().method_23761(), 0f, 0f, 0f, this.size.getValue().floatValue() / 2f, ColorUtility.applyOpacity(this.color.getColorRGB(), (int)(this.opacity.getValue().floatValue() * this.getAnimation().getOutput().floatValue())));
        vcp.method_37104();
        ms.method_22909();
    }

    private void renderGhosts(class_4587 ms, class_1297 target, class_243 cameraPos) {
        float tickDelta = mc.method_61966().method_60637(true);
        class_243 lerp = MathVector.lerpPosition(target);
        double tPosX = lerp.field_1352 - cameraPos.field_1352;
        double tPosY = lerp.field_1351 - cameraPos.field_1351;
        double tPosZ = lerp.field_1350 - cameraPos.field_1350;
        float iAge = (target.field_6012 - 1) + tickDelta;
        float espLength = this.dlinaDush.getValue().floatValue();
        float factor = this.factorDush.getValue().floatValue();
        float shaking = 1.32f;
        float amplitude = 3f;
        float particleDensity = this.particleDensityDush.getValue().floatValue();
        float scale = this.sizeDush.getValue().floatValue();
        double radius = target.method_17681();
        int colorMain = this.color.getColorRGB();
        int colorSecond = this.twinColor.getColorRGB();
        int baseAlpha = this.opacity.getValue().floatValue();
        float anim = this.getAnimation().getOutput().floatValue();
        float time = (System.currentTimeMillis() % 2000L) / 2000f;
        float phaseShift = 1.5707964f;
        float[] tmp0 = new float[4];
        tmp0[0] = 45f;
        tmp0[1] = 135f;
        tmp0[2] = 225f;
        tmp0[3] = 315f;
        float[] angles = tmp0;
        float yaw = mc.field_1773.method_19418().method_19330();
        float pitch = mc.field_1773.method_19418().method_19329();
        class_4598 vcp = mc.method_22940().method_23000();
        class_4588 visible = vcp.getBuffer(GHOST_VISIBLE_LAYER);
        this.renderGhostsPass(ms, visible, tPosX, tPosY, tPosZ, iAge, espLength, factor, shaking, amplitude, particleDensity, scale, colorMain, colorSecond, baseAlpha, anim, time, phaseShift, angles, yaw, pitch, 1f, radius);
        vcp.method_22994(GHOST_VISIBLE_LAYER);
        if (this.xrayThroughWalls.getEnabled().booleanValue()) {
            class_4588 xray = vcp.getBuffer(GHOST_XRAY_LAYER);
            this.renderGhostsPass(ms, xray, tPosX, tPosY, tPosZ, iAge, espLength, factor, shaking, amplitude, particleDensity, scale, colorMain, colorSecond, baseAlpha, anim, time, phaseShift, angles, yaw, pitch, this.xrayAlpha.getValue().floatValue(), radius);
            vcp.method_22994(GHOST_XRAY_LAYER);
        }
    }

    private void renderGhostsPass(class_4587 ms, class_4588 vc, double tPosX, double tPosY, double tPosZ, float iAge, float espLength, float factor, float shaking, float amplitude, float particleDensity, float scale, int colorMain, int colorSecond, int baseAlpha, float anim, float time, float phaseShift, float[] angles, float yaw, float pitch, float alphaMul, double radius) {
        int rFirst = colorMain >> 16 & 255;
        int gFirst = colorMain >> 8 & 255;
        int bFirst = colorMain & 255;
        int rTwin = colorSecond >> 16 & 255;
        int gTwin = colorSecond >> 8 & 255;
        int bTwin = colorSecond & 255;
        for (int j = 0; j < 4; j++) {
            float colorLerp = (Math.sin(6.283185307179586 * (double)time + (double)((float)j * phaseShift)) * 0.5 + 0.5);
            int r = ((float)rFirst + (float)(rTwin - rFirst) * colorLerp);
            int g = ((float)gFirst + (float)(gTwin - gFirst) * colorLerp);
            int b = ((float)bFirst + (float)(bTwin - bFirst) * colorLerp);
            int particleCount = (espLength * particleDensity);
            for (int i = 0; i < particleCount; i++) {
                float offset = i / (float)particleCount;
                double radians = Math.toRadians((double)((angles[j] + (offset * espLength + iAge) * factor) % 360f));
                double sinQuad = Math.sin(Math.toRadians((double)(iAge * 2.5f + offset * espLength * 2f + (float)(j * 90))) * (double)amplitude) / (double)shaking;
                float alphaFactor = 1f - offset * 0.3f;
                int gradAlpha = ((float)baseAlpha * alphaFactor * anim * alphaMul);
                if (!(gradAlpha <= 0)) {
                    if (gradAlpha <= 255) continue;
                    gradAlpha = 255;
                    double px = tPosX + Math.cos(radians) * radius;
                    double py = tPosY + 1 + sinQuad;
                    double pz = tPosZ + Math.sin(radians) * radius;
                    ms.method_22903();
                    ms.method_22904(px, py, pz);
                    ms.method_22907(class_7833.field_40716.rotationDegrees(-yaw));
                    ms.method_22907(class_7833.field_40714.rotationDegrees(pitch));
                    Matrix4f matrix = ms.method_23760().method_23761();
                    int finalColor = class_9848.method_61324(gradAlpha, r, g, b);
                    vc.method_22918(matrix, -scale, -scale, 0f).method_22913(0f, 1f).method_39415(finalColor);
                    vc.method_22918(matrix, -scale, scale, 0f).method_22913(0f, 0f).method_39415(finalColor);
                    vc.method_22918(matrix, scale, scale, 0f).method_22913(1f, 0f).method_39415(finalColor);
                    vc.method_22918(matrix, scale, -scale, 0f).method_22913(1f, 1f).method_39415(finalColor);
                    ms.method_22909();
                }
            }
        }
    }

    private void renderAtoms(class_4587 ms, class_1297 target, class_243 cameraPos) {
        class_243 lerp = MathVector.lerpPosition(target);
        double cx = lerp.field_1352 - cameraPos.field_1352;
        double cy = lerp.field_1351 - cameraPos.field_1351 + (double)target.method_17682() * 0.5;
        double cz = lerp.field_1350 - cameraPos.field_1350;
        float scale = this.atomSize.getValue().floatValue();
        int rings = this.atomRings.getValue().floatValue();
        int particlesPerRing = this.atomParticles.getValue().floatValue();
        float speed = this.atomSpeed.getValue().floatValue();
        float radius = target.method_17681() * this.atomRadius.getValue().floatValue();
        int colorMain = this.color.getColorRGB();
        int colorSecond = this.twinColor.getColorRGB();
        int baseAlpha = this.opacity.getValue().floatValue();
        float anim = this.getAnimation().getOutput().floatValue();
        float t = (System.currentTimeMillis() % 100000L) / 1000f;
        float yaw = mc.field_1773.method_19418().method_19330();
        float pitch = mc.field_1773.method_19418().method_19329();
        class_4598 vcp = mc.method_22940().method_23000();
        class_4588 visible = vcp.getBuffer(GHOST_VISIBLE_LAYER);
        this.renderAtomsPass(ms, visible, cx, cy, cz, scale, rings, particlesPerRing, speed, radius, colorMain, colorSecond, baseAlpha, anim, t, yaw, pitch, 1f);
        vcp.method_22994(GHOST_VISIBLE_LAYER);
        if (this.xrayThroughWalls.getEnabled().booleanValue()) {
            class_4588 xray = vcp.getBuffer(GHOST_XRAY_LAYER);
            this.renderAtomsPass(ms, xray, cx, cy, cz, scale, rings, particlesPerRing, speed, radius, colorMain, colorSecond, baseAlpha, anim, t, yaw, pitch, this.xrayAlpha.getValue().floatValue());
            vcp.method_22994(GHOST_XRAY_LAYER);
        }
    }

    private void renderAtomsPass(class_4587 ms, class_4588 vc, double cx, double cy, double cz, float scale, int rings, int particlesPerRing, float speed, float radius, int colorMain, int colorSecond, int baseAlpha, float anim, float t, float yaw, float pitch, float alphaMul) {
        int rFirst = colorMain >> 16 & 255;
        int gFirst = colorMain >> 8 & 255;
        int bFirst = colorMain & 255;
        int rTwin = colorSecond >> 16 & 255;
        int gTwin = colorSecond >> 8 & 255;
        int bTwin = colorSecond & 255;
        for (int ring = 0; ring < rings; ring++) {
            double tilt = Math.toRadians(180 / (double)rings * (double)ring + 30);
            double cosTilt = Math.cos(tilt);
            double sinTilt = Math.sin(tilt);
            double precession = Math.toRadians((double)(t * (float)(15 + ring * 7) % 360f));
            double cosPre = Math.cos(precession);
            double sinPre = Math.sin(precession);
            float ringPhase = ring * 0.33f;
            int direction = ring % 2 == 0 ? 1 : -1;
            float baseAngleDeg = t * speed * 90f * (float)direction + (float)ring * 60f;
            for (int p = 0; p < particlesPerRing; p++) {
                float frac = p / (float)particlesPerRing;
                double angle = Math.toRadians((double)baseAngleDeg + (double)frac * 360);
                double localX = Math.cos(angle) * (double)radius;
                double localZ = Math.sin(angle) * (double)radius;
                double rotY = -localZ * sinTilt;
                double rotZ = localZ * cosTilt;
                double rotX = localX;
                double finalX = rotX * cosPre + rotZ * sinPre;
                double finalZ = -rotX * sinPre + rotZ * cosPre;
                double finalY = rotY;
                float colorWave = (Math.sin(angle + (double)t * 1.5 + (double)ringPhase * 3.141592653589793 * 2) * 0.5 + 0.5);
                int r = ((float)rFirst + (float)(rTwin - rFirst) * colorWave);
                int g = ((float)gFirst + (float)(gTwin - gFirst) * colorWave);
                int b = ((float)bFirst + (float)(bTwin - bFirst) * colorWave);
                float depthFade = 0.6f + 0.4f * (float)((finalZ + (double)radius) / ((double)radius * 2));
                int a = ((float)baseAlpha * anim * depthFade * alphaMul);
                if (!(a <= 0)) {
                    if (a <= 255) continue;
                    a = 255;
                    ms.method_22903();
                    ms.method_22904(cx + finalX, cy + finalY, cz + finalZ);
                    ms.method_22907(class_7833.field_40716.rotationDegrees(-yaw));
                    ms.method_22907(class_7833.field_40714.rotationDegrees(pitch));
                    Matrix4f matrix = ms.method_23760().method_23761();
                    int finalColor = class_9848.method_61324(a, r, g, b);
                    vc.method_22918(matrix, -scale, -scale, 0f).method_22913(0f, 1f).method_39415(finalColor);
                    vc.method_22918(matrix, -scale, scale, 0f).method_22913(0f, 0f).method_39415(finalColor);
                    vc.method_22918(matrix, scale, scale, 0f).method_22913(1f, 0f).method_39415(finalColor);
                    vc.method_22918(matrix, scale, -scale, 0f).method_22913(1f, 1f).method_39415(finalColor);
                    ms.method_22909();
                }
            }
        }
    }
}
