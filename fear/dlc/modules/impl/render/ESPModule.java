/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.MathProjection;
import fear.dlc.utility.math.MathVector;
import net.minecraft.class_10142;
import net.minecraft.class_1297;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_332;
import org.joml.Matrix4f;
import org.joml.Vector4d;

public class ESPModule
extends ModuleLayer {
    public ESPModule() {
        super(class_2561.method_30163("ESP"), null, Category.Render);
    }

    @Subscribe
    public void renderEvent(RenderEvent.AfterHud renderEvent) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        class_332 context = renderEvent.getContext();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(class_10142.field_53876);
        class_287 builder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        mc.field_1724.method_37908().method_18456().forEach(this::lambda$renderEvent$0 /* captured: builder, context */);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public void drawBox(class_287 builder, class_1297 ent, class_332 context) {
        class_243[] vectors = ESPModule.getVectors(ent);
        Vector4d position = null;
        double posX = vectors;
        var var7 = posX.length;
        for (double posY = 0; posY < var7; posY++) {
            class_243 vector = posX[posY];
            vector = MathProjection.projectCoordinates(new class_243(vector.field_1352, vector.field_1351, vector.field_1350));
            if (vector.field_1350 > 0) {
                if (vector.field_1350 < 1) {
                    if (position != null) continue;
                    position = new Vector4d(vector.field_1352, vector.field_1351, vector.field_1350, 0);
                    position.x = Math.min(vector.field_1352, position.x);
                    position.y = Math.min(vector.field_1351, position.y);
                    position.z = Math.max(vector.field_1352, position.z);
                    position.w = Math.max(vector.field_1351, position.w);
                }
            }
        }
        if (position != null) {
            posX = position.x;
            posY = position.y;
            double endPosX = position.z;
            double endPosY = position.w;
            ESPModule.setRectPoints(builder, context.method_51448().method_23760().method_23761(), (float)(posX - 0.5), (float)posY, (float)(posX + 0.5 - 0.5), (float)endPosY, ColorUtility.applyOpacity(-16777216, 75));
            ESPModule.setRectPoints(builder, context.method_51448().method_23760().method_23761(), (float)posX, (float)(endPosY - 0.5), (float)endPosX, (float)endPosY, ColorUtility.applyOpacity(-16777216, 75));
            ESPModule.setRectPoints(builder, context.method_51448().method_23760().method_23761(), (float)(posX - 0.5), (float)posY, (float)endPosX, (float)(posY + 0.5), ColorUtility.applyOpacity(-16777216, 75));
            ESPModule.setRectPoints(builder, context.method_51448().method_23760().method_23761(), (float)(endPosX - 0.5), (float)posY, (float)endPosX, (float)endPosY, ColorUtility.applyOpacity(-16777216, 75));
        }
        class_286.method_43433(builder.method_60800());
    }

    private static class_243[] getVectors(class_1297 ent) {
        class_243 lerp = MathVector.lerpPosition(ent);
        class_238 axisAlignedBB2 = ent.method_5829();
        class_238 axisAlignedBB = new class_238(axisAlignedBB2.field_1323 - ent.method_23317() + lerp.field_1352 - 0.05, axisAlignedBB2.field_1322 - ent.method_23318() + lerp.field_1351, axisAlignedBB2.field_1321 - ent.method_23321() + lerp.field_1350 - 0.05, axisAlignedBB2.field_1320 - ent.method_23317() + lerp.field_1352 + 0.05, axisAlignedBB2.field_1325 - ent.method_23318() + lerp.field_1351 + 0.15, axisAlignedBB2.field_1324 - ent.method_23321() + lerp.field_1350 + 0.05);
        class_243[] tmp0 = new class_243[8];
        tmp0[0] = new class_243(axisAlignedBB.field_1323, axisAlignedBB.field_1322, axisAlignedBB.field_1321);
        tmp0[1] = new class_243(axisAlignedBB.field_1323, axisAlignedBB.field_1325, axisAlignedBB.field_1321);
        tmp0[2] = new class_243(axisAlignedBB.field_1320, axisAlignedBB.field_1322, axisAlignedBB.field_1321);
        tmp0[3] = new class_243(axisAlignedBB.field_1320, axisAlignedBB.field_1325, axisAlignedBB.field_1321);
        tmp0[4] = new class_243(axisAlignedBB.field_1323, axisAlignedBB.field_1322, axisAlignedBB.field_1324);
        tmp0[5] = new class_243(axisAlignedBB.field_1323, axisAlignedBB.field_1325, axisAlignedBB.field_1324);
        tmp0[6] = new class_243(axisAlignedBB.field_1320, axisAlignedBB.field_1322, axisAlignedBB.field_1324);
        tmp0[7] = new class_243(axisAlignedBB.field_1320, axisAlignedBB.field_1325, axisAlignedBB.field_1324);
        return tmp0;
    }

    public static void setRectPoints(class_287 bufferBuilder, Matrix4f matrix, float x, float y, float x1, float y1, int color) {
        bufferBuilder.method_22918(matrix, x, y1, 0f).method_39415(color);
        bufferBuilder.method_22918(matrix, x1, y1, 0f).method_39415(color);
        bufferBuilder.method_22918(matrix, x1, y, 0f).method_39415(color);
        bufferBuilder.method_22918(matrix, x, y, 0f).method_39415(color);
    }
}
