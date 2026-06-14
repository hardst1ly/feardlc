/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ColorSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.utility.render.utility.VertexUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1921;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_5819;
import net.minecraft.class_9848;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class WorldParticles
extends ModuleLayer {
    public BooleanSetting enabledParticles;
    public SliderSetting particlesCount;
    public SliderSetting particlesSize;
    public SliderSetting particlesDistance;
    public SliderSetting particlesSpeed;
    public SliderSetting particlesLifetime;
    public SliderSetting verticalOffset;
    public SliderSetting verticalSpread;
    public ModeSetting particlesType;
    public ModeSetting particlesColorMode;
    public ColorSetting particlesCustomColor;
    public BooleanSetting particlesGlow;
    public SliderSetting glowIntensity;
    public SliderSetting glowAlpha;
    public BooleanSetting particlesRotation;
    private final List<Particle> particlesList;
    private final class_5819 random;

    public WorldParticles() {
        super(class_2561.method_30163("WorldParticles"), class_2561.method_30163("\u0420\u0435\u043d\u0434\u0435\u0440\u0438\u0442 \u043a\u0440\u0430\u0441\u0438\u0432\u044b\u0435 \u043f\u0430\u0440\u0442\u0438\u043a\u043b\u044b \u0432 \u043c\u0438\u0440\u0435"), Category.Render);
        this.enabledParticles = new BooleanSetting(class_2561.method_30163("\u0412\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u043f\u0430\u0440\u0442\u0438\u043a\u043b\u044b"), class_2561.method_30163("\u0412\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u0440\u0435\u043d\u0434\u0435\u0440 \u043f\u0430\u0440\u0442\u0438\u043a\u043b\u043e\u0432"), WorldParticles::lambda$new$0).set(true).register(this);
        this.particlesCount = new SliderSetting(class_2561.method_30163("\u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e"), null, this::lambda$new$1).set(10f, 500f, 1f).set(50f).register(this);
        this.particlesSize = new SliderSetting(class_2561.method_30163("\u0420\u0430\u0437\u043c\u0435\u0440"), null, this::lambda$new$2).set(0.1f, 2f, 0.1f).set(0.3f).register(this);
        this.particlesDistance = new SliderSetting(class_2561.method_30163("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f"), null, this::lambda$new$3).set(5f, 100f, 1f).set(25f).register(this);
        this.particlesSpeed = new SliderSetting(class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c"), null, this::lambda$new$4).set(0.1f, 2f, 0.1f).set(0.5f).register(this);
        this.particlesLifetime = new SliderSetting(class_2561.method_30163("\u0412\u0440\u0435\u043c\u044f \u0436\u0438\u0437\u043d\u0438"), null, this::lambda$new$5).set(1000f, 10000f, 100f).set(5000f).register(this);
        this.verticalOffset = new SliderSetting(class_2561.method_30163("\u0412\u044b\u0441\u043e\u0442\u0430"), class_2561.method_30163("\u0423\u0440\u043e\u0432\u0435\u043d\u044c \u0441\u043f\u0430\u0432\u043d\u0430 \u0447\u0430\u0441\u0442\u0438\u0446 \u043e\u0442\u043d\u043e\u0441\u0438\u0442\u0435\u043b\u044c\u043d\u043e \u0438\u0433\u0440\u043e\u043a\u0430. \u041f\u043e\u043b\u043e\u0436\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0435 \u2014 \u0432\u044b\u0448\u0435, \u043e\u0442\u0440\u0438\u0446\u0430\u0442\u0435\u043b\u044c\u043d\u043e\u0435 \u2014 \u043d\u0438\u0436\u0435"), this::lambda$new$6).set(-20f, 20f, 1f).set(4f).register(this);
        this.verticalSpread = new SliderSetting(class_2561.method_30163("\u0412\u0435\u0440\u0442. \u0440\u0430\u0437\u0431\u0440\u043e\u0441"), class_2561.method_30163("\u041d\u0430\u0441\u043a\u043e\u043b\u044c\u043a\u043e \u0432\u0432\u0435\u0440\u0445/\u0432\u043d\u0438\u0437 \u043e\u0442 \u0446\u0435\u043d\u0442\u0440\u0430 \u0440\u0430\u0437\u043b\u0435\u0442\u0430\u044e\u0442\u0441\u044f \u0447\u0430\u0441\u0442\u0438\u0446\u044b"), this::lambda$new$7).set(0.5f, 15f, 0.5f).set(3f).register(this);
        String[] tmp0 = new String[9];
        tmp0[0] = "\u0413\u043b\u043e\u0443";
        tmp0[1] = "\u0417\u0432\u0435\u0437\u0434\u0430";
        tmp0[2] = "\u0414\u043e\u043b\u043b\u0430\u0440";
        tmp0[3] = "\u0421\u043d\u0435\u0436\u0438\u043d\u043a\u0430";
        tmp0[4] = "\u0421\u0435\u0440\u0434\u0446\u0435";
        tmp0[5] = "\u041a\u043e\u0440\u043e\u043d\u0430";
        tmp0[6] = "\u041c\u043e\u043b\u043d\u0438\u044f";
        tmp0[7] = "\u041a\u0440\u0443\u0433";
        tmp0[8] = "\u0422\u0440\u0435\u0443\u0433\u043e\u043b\u044c\u043d\u0438\u043a";
        this.particlesType = new ModeSetting(class_2561.method_30163("\u0422\u0438\u043f \u043f\u0430\u0440\u0442\u0438\u043a\u043b\u043e\u0432"), null, this::lambda$new$8).set(tmp0).set("\u0413\u043b\u043e\u0443").register(this);
        String[] tmp1 = new String[4];
        tmp1[0] = "\u041a\u043b\u0438\u0435\u043d\u0442";
        tmp1[1] = "\u0421\u0432\u043e\u0439";
        tmp1[2] = "\u0420\u0430\u0434\u0443\u0433\u0430";
        tmp1[3] = "\u0421\u043b\u0443\u0447\u0430\u0439\u043d\u044b\u0439";
        this.particlesColorMode = new ModeSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442"), null, this::lambda$new$9).set(tmp1).set("\u041a\u043b\u0438\u0435\u043d\u0442").register(this);
        this.particlesCustomColor = new ColorSetting(class_2561.method_30163("\u0421\u0432\u043e\u0439 \u0446\u0432\u0435\u0442"), null, this::lambda$new$10).set(-8090).register(this);
        this.particlesGlow = new BooleanSetting(class_2561.method_30163("\u0421\u0432\u0435\u0447\u0435\u043d\u0438\u0435"), class_2561.method_30163("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u044d\u0444\u0444\u0435\u043a\u0442 \u0441\u0432\u0435\u0447\u0435\u043d\u0438\u044f"), this::lambda$new$11).set(false).register(this);
        this.glowIntensity = new SliderSetting(class_2561.method_30163("\u0421\u0438\u043b\u0430 \u0441\u0432\u0435\u0447\u0435\u043d\u0438\u044f"), null, this::lambda$new$12).set(0.5f, 5f, 0.1f).set(1.5f).register(this);
        this.glowAlpha = new SliderSetting(class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u0441\u0432\u0435\u0447\u0435\u043d\u0438\u044f"), null, this::lambda$new$13).set(0.1f, 1f, 0.05f).set(0.3f).register(this);
        this.particlesRotation = new BooleanSetting(class_2561.method_30163("\u0412\u0440\u0430\u0449\u0435\u043d\u0438\u0435"), class_2561.method_30163("\u0412\u0440\u0430\u0449\u0430\u0442\u044c \u043f\u0430\u0440\u0442\u0438\u043a\u043b\u044b"), this::lambda$new$14).set(true).register(this);
        this.particlesList = new ArrayList();
        this.random = class_5819.method_43047();
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
        this.particlesList.clear();
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        this.particlesList.clear();
        super.deactivate();
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (!this.getEnabled().booleanValue() || mc.field_1687 == null || mc.field_1724 == null || !this.enabledParticles.getEnabled().booleanValue()) {
            return;
        }
        long now = System.currentTimeMillis();
        this.particlesList.removeIf(WorldParticles::lambda$onTick$15 /* captured: now */);
        int targetCount = this.particlesCount.getValue().intValue();
        float distance = this.particlesDistance.getValue().floatValue();
        float vOffset = this.verticalOffset.getValue().floatValue();
        float vSpread = this.verticalSpread.getValue().floatValue();
        class_243 playerPos = mc.field_1724.method_19538();
        double centerY = playerPos.field_1351 + (double)vOffset;
        this.particlesList.removeIf(this::lambda$onTick$16 /* captured: centerY, vSpread, distance */);
        int attempts = 0;
        while (this.particlesList.size() < targetCount) {
            if (attempts >= 200) break;
            attempts++;
            double theta = 6.283185307179586 * this.random.method_43058();
            double u = 2 * this.random.method_43058() - 1;
            double horiz = distance * Math.sqrt(1 - u * u);
            double dx = horiz * Math.cos(theta);
            double dz = horiz * Math.sin(theta);
            double dy = u * (double)vSpread;
            double x = playerPos.field_1352 + dx;
            double y = centerY + dy;
            double z = playerPos.field_1350 + dz;
            if (y >= (double)(mc.field_1687.method_31607() + 1)) {
                float speed = this.particlesSpeed.getValue().floatValue();
                float motionX = (this.random.method_43057() - 0.5f) * 0.04f * speed;
                float motionY = (this.random.method_43057() - 0.5f) * 0.02f * speed;
                float motionZ = (this.random.method_43057() - 0.5f) * 0.04f * speed;
                this.particlesList.add(new Particle(this, (float)x, (float)y, (float)z, motionX, motionY, motionZ, System.currentTimeMillis(), this.particlesLifetime.getValue().intValue()));
            }
        }
        for (Particle particle : this.particlesList) {
            particle.update(centerY, vSpread);
        }
    }

    private double cameraDistanceSq(Particle p) {
        class_243 cam = mc.field_1773.method_19418().method_19326();
        double dx = p.x - cam.field_1352;
        double dy = p.y - cam.field_1351;
        double dz = p.z - cam.field_1350;
        return dx * dx + dy * dy + dz * dz;
    }

    @Subscribe
    public void onRender3D(RenderEvent.AfterHand event) {
        if (!this.getEnabled().booleanValue() || mc.field_1687 == null || !this.enabledParticles.getEnabled().booleanValue() || this.particlesList.isEmpty()) {
            return;
        }
        class_4587 matrixStack = event.getStack();
        class_243 cameraPos = mc.field_1773.method_19418().method_19326();
        float tickDelta = event.getTickCounter().method_60637(true);
        float distLimit = this.particlesDistance.getValue().floatValue();
        float distLimitSq = distLimit * distLimit;
        float size = this.particlesSize.getValue().floatValue();
        boolean glow = this.particlesGlow.getEnabled().booleanValue();
        float glowMult = this.glowIntensity.getValue().floatValue();
        float glowAlphaVal = this.glowAlpha.getValue().floatValue();
        class_2960 particleTexture = this.getTexture();
        class_2960 glowTexture = class_2960.method_60655("pasxalka", "images/Particles/bloom.png");
        class_1921 particleLayer = VertexUtils.getParticleTexturedDepth(particleTexture);
        class_1921 glowLayer = VertexUtils.getGlowTexturedDepth(glowTexture);
        class_4598 vcp = mc.method_22940().method_23000();
        class_4588 particleVc = vcp.getBuffer(particleLayer);
        class_4588 glowVc = glow ? vcp.getBuffer(glowLayer) : null;
        List<Particle> snapshot = new ArrayList(this.particlesList);
        for (Particle particle : snapshot) {
            float x = particle.prevX + (particle.x - particle.prevX) * tickDelta;
            float y = particle.prevY + (particle.y - particle.prevY) * tickDelta;
            float z = particle.prevZ + (particle.z - particle.prevZ) * tickDelta;
            double dx = x - cameraPos.field_1352;
            double dy = y - cameraPos.field_1351;
            double dz = z - cameraPos.field_1350;
            while (dx * dx + dy * dy + dz * dz > (double)distLimitSq) {
            }
            float alpha = particle.getAlpha();
            while (alpha <= 0f) {
            }
            int color = this.getParticleColor(particle);
            this.renderQuad(particleVc, matrixStack, cameraPos, x, y, z, size, particle, color, alpha, false);
            if (glow) {
                if (glowVc == null) continue;
                this.renderQuad(glowVc, matrixStack, cameraPos, x, y, z, size * glowMult, particle, color, alpha * glowAlphaVal, true);
            }
        }
        vcp.method_22994(particleLayer);
        if (glow) {
            vcp.method_22994(glowLayer);
        }
    }

    private void renderQuad(class_4588 vc, class_4587 matrixStack, class_243 cameraPos, float x, float y, float z, float size, Particle p, int color, float alpha, boolean isGlow) {
        if (alpha <= 0f) {
            return;
        }
        int a = (alpha * 255f);
        if (a <= 0) {
            return;
        }
        if (a > 255) {
            a = 255;
        }
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        int finalColor = class_9848.method_61324(a, r, g, b);
        Quaternionf rot = mc.field_1773.method_19418().method_23767();
        Vector3f right = new Vector3f(1f, 0f, 0f).rotate(rot);
        Vector3f up = new Vector3f(0f, 1f, 0f).rotate(rot);
        float rad = Math.toRadians((double)p.rotation);
        if (this.particlesRotation.getEnabled().booleanValue() && !isGlow) {
            float cos = Math.cos((double)rad);
            float sin = Math.sin((double)rad);
            Vector3f nr = new Vector3f(right.x * cos + up.x * sin, right.y * cos + up.y * sin, right.z * cos + up.z * sin);
            Vector3f nu = new Vector3f(-right.x * sin + up.x * cos, -right.y * sin + up.y * cos, -right.z * sin + up.z * cos);
            right = nr;
            up = nu;
        }
        float cx = ((double)x - cameraPos.field_1352);
        float cy = ((double)y - cameraPos.field_1351);
        float cz = ((double)z - cameraPos.field_1350);
        float rx = right.x * size;
        float ry = right.y * size;
        float rz = right.z * size;
        float ux = up.x * size;
        float uy = up.y * size;
        float uz = up.z * size;
        Matrix4f matrix = matrixStack.method_23760().method_23761();
        vc.method_22918(matrix, cx - rx - ux, cy - ry - uy, cz - rz - uz).method_22913(0f, 1f).method_39415(finalColor);
        vc.method_22918(matrix, cx - rx + ux, cy - ry + uy, cz - rz + uz).method_22913(0f, 0f).method_39415(finalColor);
        vc.method_22918(matrix, cx + rx + ux, cy + ry + uy, cz + rz + uz).method_22913(1f, 0f).method_39415(finalColor);
        vc.method_22918(matrix, cx + rx - ux, cy + ry - uy, cz + rz - uz).method_22913(1f, 1f).method_39415(finalColor);
    }

    private class_2960 getTexture() {
        String type = this.particlesType.getValue();
        if (type == null) {
            return class_2960.method_60655("pasxalka", "images/Particles/bloom.png");
        }
        var var2 = type;
        int var3 = -1;
        switch (var2.hashCode()) {
            case 32147533:
                if (var2.equals("\u0413\u043b\u043e\u0443")) {
                    var3 = 0;
                }
            case 934967833:
                if (var2.equals("\u0417\u0432\u0435\u0437\u0434\u0430")) {
                    var3 = 1;
                }
            case 860345114:
                if (var2.equals("\u0414\u043e\u043b\u043b\u0430\u0440")) {
                    var3 = 2;
                }
            case -2018961832:
                if (var2.equals("\u0421\u043d\u0435\u0436\u0438\u043d\u043a\u0430")) {
                    var3 = 3;
                }
            case 1224355287:
                if (var2.equals("\u0421\u0435\u0440\u0434\u0446\u0435")) {
                    var3 = 4;
                }
            case 1032272245:
                if (var2.equals("\u041a\u043e\u0440\u043e\u043d\u0430")) {
                    var3 = 5;
                }
            case 1089380507:
                if (var2.equals("\u041c\u043e\u043b\u043d\u0438\u044f")) {
                    var3 = 6;
                }
            case 32361014:
                if (var2.equals("\u041a\u0440\u0443\u0433")) {
                    var3 = 7;
                }
            case -1485804969:
                if (var2.equals("\u0422\u0440\u0435\u0443\u0433\u043e\u043b\u044c\u043d\u0438\u043a")) {
                    var3 = 8;
                }
            default:
                switch (var3) {
                    case 0:
                        return class_2960.method_60655("pasxalka", "images/Particles/glow.png");
                    case 1:
                        return class_2960.method_60655("pasxalka", "images/Particles/star.png");
                    case 2:
                        return class_2960.method_60655("pasxalka", "images/Particles/dollar.png");
                    case 3:
                        return class_2960.method_60655("pasxalka", "images/Particles/snow.png");
                    case 4:
                        return class_2960.method_60655("pasxalka", "images/Particles/heart.png");
                    case 5:
                        return class_2960.method_60655("pasxalka", "images/Particles/crown.png");
                    case 6:
                        return class_2960.method_60655("pasxalka", "images/Particles/lightning.png");
                    case 7:
                        return class_2960.method_60655("pasxalka", "images/circle.png");
                    case 8:
                        return class_2960.method_60655("pasxalka", "images/triangle.png");
                    default:
                        return class_2960.method_60655("pasxalka", "images/Particles/bloom.png");
                }
        }
    }

    private int getParticleColor(Particle particle) {
        String colorMode = this.particlesColorMode.getValue();
        if (colorMode == null) {
            return -1;
        }
        var var3 = colorMode;
        int var4 = -1;
        switch (var3.hashCode()) {
            case 1029254723:
                if (var3.equals("\u041a\u043b\u0438\u0435\u043d\u0442")) {
                    var4 = 0;
                }
            case 32555948:
                if (var3.equals("\u0421\u0432\u043e\u0439")) {
                    var4 = 1;
                }
            case 1190764860:
                if (var3.equals("\u0420\u0430\u0434\u0443\u0433\u0430")) {
                    var4 = 2;
                }
            case -1629585340:
                if (var3.equals("\u0421\u043b\u0443\u0447\u0430\u0439\u043d\u044b\u0439")) {
                    var4 = 3;
                }
            default:
                switch (var4) {
                    case 0:
                        return this.getClientColor(particle);
                    case 1:
                        return this.particlesCustomColor.getColor();
                    case 2:
                        return Color.HSBtoRGB((float)(System.currentTimeMillis() % 10000L) / 10000f, 0.8f, 1f);
                    case 3:
                        return Color.HSBtoRGB(particle.randomHue, 0.8f, 1f);
                    default:
                        return -1;
                }
        }
    }

    private int getClientColor(Particle particle) {
        float t = ((float)(System.currentTimeMillis() % 3000L) / 3000f + particle.randomHue) % 1f;
        float wave = (Math.sin((double)t * 3.141592653589793 * 2) * 0.5 + 0.5);
        int rA = 230;
        int gA = 242;
        int bA = 255;
        int rB = 91;
        int gB = 168;
        int bB = 255;
        int r = ((float)rA + (float)(rB - rA) * wave);
        int g = ((float)gA + (float)(gB - gA) * wave);
        int b = ((float)bA + (float)(bB - bA) * wave);
        return -16777216 | r << 16 | g << 8 | b;
    }
}
