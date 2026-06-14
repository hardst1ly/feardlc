/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.utility.render.providers.ResourceProvider;
import net.minecraft.class_10149;
import net.minecraft.class_10156;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_4587;
import net.minecraft.class_5944;
import org.joml.Matrix4f;

public class MenuShaderBackground {
    private static long startTime = System.currentTimeMillis();
    private static float timeCounter = 0f;
    private static final class_10156 MENU_SHADER_KEY = new class_10156(ResourceProvider.getShaderIdentifier("menu_background"), class_290.field_1576, class_10149.field_53930);

    public static void render(class_4587 matrices, int width, int height, float tickDelta) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime > 10L) {
            timeCounter = timeCounter + 0.1f;
            startTime = currentTime;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        class_5944 shader = RenderSystem.setShader(MENU_SHADER_KEY);
        if (shader != null) {
            if (shader.method_34582("time") != null) {
                shader.method_34582("time").method_1251(timeCounter / 4f);
            }
            if (shader.method_34582("width") != null) {
                shader.method_34582("width").method_1251((float)width);
            }
            if (shader.method_34582("height") != null) {
                shader.method_34582("height").method_1251((float)height);
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
}
