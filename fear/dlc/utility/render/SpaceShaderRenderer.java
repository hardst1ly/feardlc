/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render;

import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.utility.render.providers.ResourceProvider;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_4587;
import net.minecraft.class_5944;
import org.joml.Matrix4f;

public class SpaceShaderRenderer {
    private static long startTime = System.currentTimeMillis();
    private static float timeCounter = 0f;
    private static final class_10156 SPACE_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("space_shader"), class_290.field_1576, class_10149.field_53930);

    public static void renderSpaceBox(class_4587 matrices, class_238 box, class_243 cameraPos, boolean animate, int alpha, float animationSpeed) {
        if (animate) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > 16L) {
                timeCounter = timeCounter + 0.016f * animationSpeed;
                startTime = currentTime;
            }
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        class_5944 shader = RenderSystem.setShader(SPACE_SHADER_KEY);
        if (shader != null) {
            if (shader.method_34582("time") != null) {
                shader.method_34582("time").method_1251(timeCounter);
            }
            if (shader.method_34582("resolution") != null) {
                shader.method_34582("resolution").method_1255(512f, 512f);
            }
            if (shader.method_34582("alpha") != null) {
                float alphaFloat = alpha / 255f;
                shader.method_34582("alpha").method_1251(alphaFloat);
            }
        }
        Matrix4f matrix = matrices.method_23760().method_23761();
        class_289 tessellator = class_289.method_1348();
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_27379, class_290.field_1576);
        SpaceShaderRenderer.drawSpaceBoxFilled(buffer, matrix, box, cameraPos, alpha);
        class_286.method_43433(buffer.method_60800());
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    public static void renderFullscreen(class_4587 matrices, int width, int height, float tickDelta) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime > 16L) {
            timeCounter = timeCounter + 0.016f;
            startTime = currentTime;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_5944 shader = RenderSystem.setShader(SPACE_SHADER_KEY);
        if (shader != null) {
            if (shader.method_34582("time") != null) {
                shader.method_34582("time").method_1251(timeCounter);
            }
            if (shader.method_34582("resolution") != null) {
                shader.method_34582("resolution").method_1255((float)width, (float)height);
            }
        }
        Matrix4f matrix = matrices.method_23760().method_23761();
        class_289 tessellator = class_289.method_1348();
        class_287 bufferBuilder = tessellator.method_60827(class_293.class_5596.field_27382, class_290.field_1576);
        int color = -1;
        bufferBuilder.method_22918(matrix, 0f, (float)height, 0f).method_39415(color);
        bufferBuilder.method_22918(matrix, (float)width, (float)height, 0f).method_39415(color);
        bufferBuilder.method_22918(matrix, (float)width, 0f, 0f).method_39415(color);
        bufferBuilder.method_22918(matrix, 0f, 0f, 0f).method_39415(color);
        class_286.method_43433(bufferBuilder.method_60800());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void drawSpaceBoxFilled(class_287 buffer, Matrix4f matrix, class_238 box, class_243 cameraPos, int alpha) {
        float x1 = (box.field_1323 - cameraPos.field_1352);
        float y1 = (box.field_1322 - cameraPos.field_1351);
        float z1 = (box.field_1321 - cameraPos.field_1350);
        float x2 = (box.field_1320 - cameraPos.field_1352);
        float y2 = (box.field_1325 - cameraPos.field_1351);
        float z2 = (box.field_1324 - cameraPos.field_1350);
        int color = -1;
        SpaceShaderRenderer.addSpaceQuad(buffer, matrix, x1, y1, z1, x2, y1, z1, x2, y1, z2, x1, y1, z2, color);
        SpaceShaderRenderer.addSpaceQuad(buffer, matrix, x1, y2, z2, x2, y2, z2, x2, y2, z1, x1, y2, z1, color);
        SpaceShaderRenderer.addSpaceQuad(buffer, matrix, x1, y1, z1, x1, y2, z1, x2, y2, z1, x2, y1, z1, color);
        SpaceShaderRenderer.addSpaceQuad(buffer, matrix, x2, y1, z2, x2, y2, z2, x1, y2, z2, x1, y1, z2, color);
        SpaceShaderRenderer.addSpaceQuad(buffer, matrix, x1, y1, z2, x1, y2, z2, x1, y2, z1, x1, y1, z1, color);
        SpaceShaderRenderer.addSpaceQuad(buffer, matrix, x2, y1, z1, x2, y2, z1, x2, y2, z2, x2, y1, z2, color);
    }

    private static void addSpaceQuad(class_287 buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, int color) {
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color);
        buffer.method_22918(matrix, x2, y2, z2).method_39415(color);
        buffer.method_22918(matrix, x3, y3, z3).method_39415(color);
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color);
        buffer.method_22918(matrix, x3, y3, z3).method_39415(color);
        buffer.method_22918(matrix, x4, y4, z4).method_39415(color);
    }

    public static void resetTimer() {
        startTime = System.currentTimeMillis();
        timeCounter = 0f;
    }
}
