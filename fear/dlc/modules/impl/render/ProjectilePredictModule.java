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
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.render.utility.VertexUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.class_10142;
import net.minecraft.class_1297;
import net.minecraft.class_1676;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1771;
import net.minecraft.class_1776;
import net.minecraft.class_1779;
import net.minecraft.class_1787;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1812;
import net.minecraft.class_1823;
import net.minecraft.class_1828;
import net.minecraft.class_1835;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_9848;
import org.joml.Matrix4f;

public class ProjectilePredictModule
extends ModuleLayer {
    private final BooleanSetting showForHeldItems;
    private final BooleanSetting showSphere;
    private final SliderSetting sphereRadius;
    private final BooleanSetting sphereFilled;
    private final BooleanSetting sphereOutline;
    private final ColorSetting lineColor;
    private final ColorSetting sphereFillColor;
    private final ColorSetting sphereOutlineColor;
    private final SliderSetting sphereAlpha;
    private final BooleanSetting showExistingProjectiles;

    public ProjectilePredictModule() {
        super(class_2561.method_30163("Projectile Predict"), null, Category.Render);
        this.showForHeldItems = new BooleanSetting(class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u0434\u043b\u044f \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432 \u0432 \u0440\u0443\u043a\u0435"), null, ProjectilePredictModule::lambda$new$0).set(true).register(this);
        this.showSphere = new BooleanSetting(class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u0441\u0444\u0435\u0440\u0443"), null, ProjectilePredictModule::lambda$new$1).set(true).register(this);
        this.sphereRadius = new SliderSetting(class_2561.method_30163("\u0420\u0430\u0434\u0438\u0443\u0441 \u0441\u0444\u0435\u0440\u044b"), null, this::lambda$new$2).set(0.1f, 2f, 0.1f).set(0.5f).register(this);
        this.sphereFilled = new BooleanSetting(class_2561.method_30163("\u0417\u0430\u043b\u0438\u0432\u043a\u0430 \u0441\u0444\u0435\u0440\u044b"), class_2561.method_30163("\u041f\u043e\u043b\u0443\u043f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u0430\u044f \u0437\u0430\u043b\u0438\u0432\u043a\u0430 \u0432\u043d\u0443\u0442\u0440\u0438 \u0441\u0444\u0435\u0440\u044b"), this::lambda$new$3).set(true).register(this);
        this.sphereOutline = new BooleanSetting(class_2561.method_30163("\u041e\u0431\u0432\u043e\u0434\u043a\u0430 \u0441\u0444\u0435\u0440\u044b"), class_2561.method_30163("\u041a\u043e\u043d\u0442\u0443\u0440 \u0441\u0444\u0435\u0440\u044b"), this::lambda$new$4).set(true).register(this);
        this.lineColor = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442 \u043b\u0438\u043d\u0438\u0438"), class_2561.method_30163("\u0426\u0432\u0435\u0442 \u0442\u0440\u0430\u0435\u043a\u0442\u043e\u0440\u0438\u0438 \u043f\u043e\u043b\u0435\u0442\u0430"), ProjectilePredictModule::lambda$new$5).set(-65536).register(this);
        this.sphereFillColor = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442 \u0437\u0430\u043b\u0438\u0432\u043a\u0438 \u0441\u0444\u0435\u0440\u044b"), class_2561.method_30163("\u0426\u0432\u0435\u0442 \u0437\u0430\u043b\u0438\u0432\u043a\u0438 \u0432\u043d\u0443\u0442\u0440\u0438 \u0441\u0444\u0435\u0440\u044b"), this::lambda$new$6).set(-16711936).register(this);
        this.sphereOutlineColor = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442 \u043e\u0431\u0432\u043e\u0434\u043a\u0438 \u0441\u0444\u0435\u0440\u044b"), class_2561.method_30163("\u0426\u0432\u0435\u0442 \u043a\u043e\u043d\u0442\u0443\u0440\u0430 \u0441\u0444\u0435\u0440\u044b"), this::lambda$new$7).set(-256).register(this);
        this.sphereAlpha = new SliderSetting(class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c \u0437\u0430\u043b\u0438\u0432\u043a\u0438"), null, this::lambda$new$8).set(10f, 255f, 5f).set(80f).register(this);
        this.showExistingProjectiles = new BooleanSetting(class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u044e\u0449\u0438\u0435 \u0441\u043d\u0430\u0440\u044f\u0434\u044b"), null, ProjectilePredictModule::lambda$new$9).set(true).register(this);
    }

    @Subscribe
    public void renderEvent(RenderEvent.AfterHand event) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        class_4587 matrices = event.getStack();
        class_243 cameraPos = mc.field_1773.method_19418().method_19326();
        matrices.method_22903();
        matrices.method_22904(-cameraPos.field_1352, -cameraPos.field_1351, -cameraPos.field_1350);
        class_4598 immediate = mc.method_22940().method_23000();
        if (this.showForHeldItems.getEnabled().booleanValue()) {
            if (mc.field_1724 != null) {
                class_1799 heldItem = mc.field_1724.method_6047();
                if (this.isThrowableItem(heldItem)) {
                    this.renderHeldItemTrajectory(matrices, immediate, heldItem);
                }
            }
        }
        if (this.showExistingProjectiles.getEnabled().booleanValue()) {
            this.renderExistingProjectiles(matrices, immediate);
        }
        immediate.method_37104();
        matrices.method_22909();
    }

    private void renderHeldItemTrajectory(class_4587 matrices, class_4597.class_4598 immediate, class_1799 item) {
        if (mc.field_1724 == null) {
            return;
        }
        class_243 startPos = mc.field_1724.method_33571();
        class_243 lookVec = mc.field_1724.method_5828(1f);
        float velocity = this.getItemVelocity(item);
        class_243 initialVelocity = lookVec.method_1021((double)velocity);
        TrajectoryData trajectory = this.calculateTrajectory(startPos, initialVelocity, item);
        this.renderTrajectoryLine(matrices, immediate, trajectory.points);
        class_243 landingPoint = trajectory.points.get(trajectory.points.size() - 1);
        if (this.showSphere.getEnabled().booleanValue() && !trajectory.points.isEmpty()) {
            this.renderSphere(matrices, immediate, landingPoint, this.sphereRadius.getValue().floatValue());
        }
    }

    private void renderExistingProjectiles(class_4587 matrices, class_4597.class_4598 immediate) {
        class_4588 vertexConsumer = immediate.getBuffer(VertexUtils.LINES);
        for (class_1297 entity : mc.field_1687.method_18112()) {
            while (!(entity instanceof class_1676)) {
            }
            AtomicReference<class_243> prev = new AtomicReference(entity.method_19538());
            List<class_243> list = this.calcTrajectory((class_1676)entity);
            list.forEach(this::lambda$renderExistingProjectiles$10 /* captured: list, vertexConsumer, matrices, prev */);
        }
    }

    private void renderTrajectoryLine(class_4587 matrices, class_4597.class_4598 immediate, List<class_243> points) {
        if (points.size() < 2) {
            return;
        }
        class_4588 vertexConsumer = immediate.getBuffer(VertexUtils.LINES);
        int color = this.lineColor.getColorRGB();
        for (int i = 0; i < points.size() - 1; i++) {
            class_243 current = points.get(i);
            class_243 next = points.get(i + 1);
            float progress = i / (float)points.size();
            int currentColor = class_9848.method_61319(progress, color, ColorUtility.applyOpacity(color, 50));
            vertexConsumer.method_22918(matrices.method_23760().method_23761(), (float)current.field_1352, (float)current.field_1351, (float)current.field_1350).method_39415(currentColor).method_22914(0f, 1f, 0f);
            vertexConsumer.method_22918(matrices.method_23760().method_23761(), (float)next.field_1352, (float)next.field_1351, (float)next.field_1350).method_39415(currentColor).method_22914(0f, 1f, 0f);
        }
    }

    private void renderSphere(class_4587 matrices, class_4597.class_4598 immediate, class_243 center, float radius) {
        Matrix4f matrix = matrices.method_23760().method_23761();
        int segments = 24;
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.setShader(class_10142.field_53876);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        if (!this.sphereFilled.getEnabled().booleanValue()) { /* goto L611; */ }
        class_289 tessellator = class_289.method_1348();
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        int fillColor = this.sphereFillColor.getColorRGB();
        int alpha = this.sphereAlpha.getValue().intValue();
        fillColor = fillColor & 16777215 | alpha << 24;
        for (int lat = 0; lat < segments / 2; lat++) {
            float theta1 = ((double)lat * 3.141592653589793 / (double)(segments / 2));
            float theta2 = ((double)(lat + 1) * 3.141592653589793 / (double)(segments / 2));
            for (int lon = 0; lon < segments; lon++) {
                float phi1 = ((double)(lon * 2) * 3.141592653589793 / (double)segments);
                float phi2 = ((double)((lon + 1) * 2) * 3.141592653589793 / (double)segments);
                float x1 = (center.field_1352 + (double)radius * Math.sin((double)theta1) * Math.cos((double)phi1));
                float y1 = (center.field_1351 + (double)radius * Math.cos((double)theta1));
                float z1 = (center.field_1350 + (double)radius * Math.sin((double)theta1) * Math.sin((double)phi1));
                float x2 = (center.field_1352 + (double)radius * Math.sin((double)theta1) * Math.cos((double)phi2));
                float y2 = (center.field_1351 + (double)radius * Math.cos((double)theta1));
                float z2 = (center.field_1350 + (double)radius * Math.sin((double)theta1) * Math.sin((double)phi2));
                float x3 = (center.field_1352 + (double)radius * Math.sin((double)theta2) * Math.cos((double)phi2));
                float y3 = (center.field_1351 + (double)radius * Math.cos((double)theta2));
                float z3 = (center.field_1350 + (double)radius * Math.sin((double)theta2) * Math.sin((double)phi2));
                float x4 = (center.field_1352 + (double)radius * Math.sin((double)theta2) * Math.cos((double)phi1));
                float y4 = (center.field_1351 + (double)radius * Math.cos((double)theta2));
                float z4 = (center.field_1350 + (double)radius * Math.sin((double)theta2) * Math.sin((double)phi1));
                buffer.method_22918(matrix, x1, y1, z1).method_39415(fillColor);
                buffer.method_22918(matrix, x2, y2, z2).method_39415(fillColor);
                buffer.method_22918(matrix, x3, y3, z3).method_39415(fillColor);
                buffer.method_22918(matrix, x1, y1, z1).method_39415(fillColor);
                buffer.method_22918(matrix, x3, y3, z3).method_39415(fillColor);
                buffer.method_22918(matrix, x4, y4, z4).method_39415(fillColor);
            }
        }
        class_286.method_43433(buffer.method_60800());
        if (!this.sphereOutline.getEnabled().booleanValue()) { /* goto L1171; */ }
        class_4588 lineConsumer = immediate.getBuffer(VertexUtils.LINES);
        int outlineColor = this.sphereOutlineColor.getColorRGB();
        for (int i = 0; i <= segments / 2; i++) {
            float theta = ((double)i * 3.141592653589793 / (double)(segments / 2));
            float y = (center.field_1351 + (double)radius * Math.cos((double)theta));
            float currentRadius = ((double)radius * Math.sin((double)theta));
            for (int j = 0; j < segments; j++) {
                float angle1 = (6.283185307179586 * (double)j / (double)segments);
                float angle2 = (6.283185307179586 * (double)(j + 1) / (double)segments);
                float x1 = (center.field_1352 + (double)currentRadius * Math.cos((double)angle1));
                float z1 = (center.field_1350 + (double)currentRadius * Math.sin((double)angle1));
                float x2 = (center.field_1352 + (double)currentRadius * Math.cos((double)angle2));
                float z2 = (center.field_1350 + (double)currentRadius * Math.sin((double)angle2));
                lineConsumer.method_22918(matrix, x1, y, z1).method_39415(outlineColor).method_22914(0f, 1f, 0f);
                lineConsumer.method_22918(matrix, x2, y, z2).method_39415(outlineColor).method_22914(0f, 1f, 0f);
            }
        }
        for (int i = 0; i < segments / 4; i++) {
            float phi = ((double)(i * 2) * 3.141592653589793 / (double)(segments / 2));
            for (int j = 0; j < segments; j++) {
                float theta1 = ((double)(j * 2) * 3.141592653589793 / (double)segments);
                float theta2 = ((double)((j + 1) * 2) * 3.141592653589793 / (double)segments);
                float x1 = (center.field_1352 + (double)radius * Math.sin((double)theta1) * Math.cos((double)phi));
                float y1 = (center.field_1351 + (double)radius * Math.cos((double)theta1));
                float z1 = (center.field_1350 + (double)radius * Math.sin((double)theta1) * Math.sin((double)phi));
                float x2 = (center.field_1352 + (double)radius * Math.sin((double)theta2) * Math.cos((double)phi));
                float y2 = (center.field_1351 + (double)radius * Math.cos((double)theta2));
                float z2 = (center.field_1350 + (double)radius * Math.sin((double)theta2) * Math.sin((double)phi));
                lineConsumer.method_22918(matrix, x1, y1, z1).method_39415(outlineColor).method_22914(0f, 1f, 0f);
                lineConsumer.method_22918(matrix, x2, y2, z2).method_39415(outlineColor).method_22914(0f, 1f, 0f);
            }
        }
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private boolean isThrowableItem(class_1799 item) {
        if (item.method_7960()) {
            return false;
        }
        class_1792 itemType = item.method_7909();
        return itemType instanceof class_1823 || itemType instanceof class_1771 || itemType instanceof class_1776 || itemType instanceof class_1779 || itemType instanceof class_1828 || itemType instanceof class_1753 || itemType instanceof class_1764 || itemType instanceof class_1835 || itemType instanceof class_1787;
    }

    private float getItemVelocity(class_1799 item) {
        class_1792 itemType = item.method_7909();
        int useTicks = mc.field_1724.method_6048();
        if (itemType instanceof class_1753 && mc.field_1724 != null) {
            float pullProgress = class_1753.method_7722(useTicks);
            return pullProgress * 3f;
        }
        if (itemType instanceof class_1764) {
            return 3.15f;
        }
        int useTicks = mc.field_1724.method_6048();
        if (itemType instanceof class_1835 && mc.field_1724 != null) {
            float riptideLevel = 0f;
            return 2.5f + riptideLevel * 0.5f;
        }
        if (itemType instanceof class_1823 || itemType instanceof class_1771 || itemType instanceof class_1776) {
            return 1.5f;
        }
        if (itemType instanceof class_1812 || itemType instanceof class_1779) {
            return 1f;
        }
        return 1.5f;
    }

    private TrajectoryData calculateTrajectory(class_243 startPos, class_243 initialVelocity, class_1799 item) {
        List<class_243> points = new ArrayList();
        class_243 pos = startPos;
        class_243 vel = initialVelocity;
        double gravity = this.getGravity(item);
        double drag = this.getDrag(item);
        int i = 0;
        while (i < 150) {
            points.add(pos);
            class_243 next = pos.method_1019(vel);
            class_3965 blockHit = mc.field_1687.method_17742(new class_3959(pos, next, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, mc.field_1724));
            if (blockHit.method_17783() != class_239.class_240.field_1333) {
                points.add(blockHit.method_17784());
            } else {
                pos = next;
                vel = vel.method_1021(drag);
                vel = vel.method_1023(0, gravity, 0);
                if ((vel.method_1027() < 0.0001)) continue;
                i++;
            }
        }
        return new TrajectoryData(points);
    }

    private double getGravity(class_1799 item) {
        class_1792 itemType = item.method_7909();
        if (itemType instanceof class_1753 || itemType instanceof class_1764) {
            return 0.05;
        }
        if (itemType instanceof class_1835) {
            return 0.05;
        }
        if (itemType instanceof class_1787) {
            return 0.04;
        }
        return 0.03;
    }

    private double getDrag(class_1799 item) {
        return 0.99;
    }

    public List<class_243> calcTrajectory(class_1676 entity) {
        List<class_243> out = new ArrayList();
        class_243 vel = entity.method_18798();
        class_243 pos = entity.method_19538();
        double gravity = entity.method_56989();
        double drag = entity.method_5799() ? 0.800000011920929 : 0.9900000095367432;
        if (entity.method_18798().equals(class_243.field_1353)) {
            return List.of();
        }
        int i = 0;
        while (i < 150) {
            out.add(pos);
            class_243 next = pos.method_1019(vel);
            class_3965 blockHit = mc.field_1687.method_17742(new class_3959(pos, next, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, entity));
            if (blockHit.method_17783() != class_239.class_240.field_1333) {
                out.add(blockHit.method_17784());
            } else {
                pos = next;
                vel = vel.method_1021(drag);
                vel = vel.method_1023(0, gravity, 0);
                i++;
            }
        }
        return out;
    }
}
