/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.utility;

import java.util.OptionalDouble;
import net.minecraft.class_1921;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_4588;
import net.minecraft.class_4668;
import net.minecraft.class_9851;
import org.joml.Matrix4f;

public class VertexUtils {
    public static final class_1921.class_4687 IMAGE = class_1921.method_24048("image", class_290.field_1575, class_293.class_5596.field_27382, 1536, class_1921.class_4688.method_23598().method_34578(class_4668.field_53128).method_23607(class_4668.field_22241).method_23615(class_4668.field_21370).method_23610(class_4668.field_25643).method_23616(class_4668.field_21349).method_23604(class_4668.field_21346).method_23603(class_4668.field_21345).method_23617(false));
    public static final class_1921.class_4687 LINES = class_1921.method_24048("lines", class_290.field_29337, class_293.class_5596.field_27377, 1536, class_1921.class_4688.method_23598().method_34578(class_4668.field_29433).method_23609(new class_4668.class_4677(OptionalDouble.of(2))).method_23607(class_4668.field_22241).method_23615(class_4668.field_21370).method_23610(class_4668.field_25643).method_23616(class_4668.field_21349).method_23604(class_4668.field_21346).method_23603(class_4668.field_21345).method_23617(false));
    public static final class_1921.class_4687 GLOW_ADDITIVE = class_1921.method_24048("glow_additive", class_290.field_1575, class_293.class_5596.field_27382, 1536, class_1921.class_4688.method_23598().method_34578(class_4668.field_53128).method_23607(class_4668.field_22241).method_23615(class_4668.field_21366).method_23610(class_4668.field_25643).method_23616(class_4668.field_21350).method_23604(class_4668.field_21346).method_23603(class_4668.field_21345).method_23617(false));

    public static class_1921.class_4687 getGlowTextured(class_2960 texture) {
        return class_1921.method_24048("glow_textured_" + texture.toString(), class_290.field_1575, class_293.class_5596.field_27382, 1536, class_1921.class_4688.method_23598().method_34578(class_4668.field_53128).method_34577(new class_4668.class_4683(texture, class_9851.field_52395, false)).method_23607(class_4668.field_22241).method_23615(class_4668.field_21366).method_23610(class_4668.field_25643).method_23616(class_4668.field_21350).method_23604(class_4668.field_21348).method_23603(class_4668.field_21345).method_23617(false));
    }

    public static class_1921.class_4687 getParticleTextured(class_2960 texture) {
        return class_1921.method_24048("particle_textured_" + texture.toString(), class_290.field_1575, class_293.class_5596.field_27382, 1536, class_1921.class_4688.method_23598().method_34578(class_4668.field_53128).method_34577(new class_4668.class_4683(texture, class_9851.field_52395, false)).method_23607(class_4668.field_22241).method_23615(class_4668.field_21370).method_23610(class_4668.field_25643).method_23616(class_4668.field_21349).method_23604(class_4668.field_21348).method_23603(class_4668.field_21345).method_23617(false));
    }

    public static class_1921.class_4687 getParticleTexturedDepth(class_2960 texture) {
        return class_1921.method_24048("particle_textured_depth_" + texture.toString(), class_290.field_1575, class_293.class_5596.field_27382, 1536, class_1921.class_4688.method_23598().method_34578(class_4668.field_53128).method_34577(new class_4668.class_4683(texture, class_9851.field_52395, false)).method_23615(class_4668.field_21370).method_23610(class_4668.field_21358).method_23616(class_4668.field_21350).method_23604(class_4668.field_21348).method_23603(class_4668.field_21345).method_23617(false));
    }

    public static class_1921.class_4687 getGlowTexturedDepth(class_2960 texture) {
        return class_1921.method_24048("glow_textured_depth_" + texture.toString(), class_290.field_1575, class_293.class_5596.field_27382, 1536, class_1921.class_4688.method_23598().method_34578(class_4668.field_53128).method_34577(new class_4668.class_4683(texture, class_9851.field_52395, false)).method_23615(class_4668.field_21366).method_23610(class_4668.field_21358).method_23616(class_4668.field_21350).method_23604(class_4668.field_21348).method_23603(class_4668.field_21345).method_23617(false));
    }

    public static class_1921.class_4687 getGlowTexturedXray(class_2960 texture) {
        return class_1921.method_24048("glow_textured_xray_" + texture.toString(), class_290.field_1575, class_293.class_5596.field_27382, 1536, class_1921.class_4688.method_23598().method_34578(class_4668.field_53128).method_34577(new class_4668.class_4683(texture, class_9851.field_52395, false)).method_23615(class_4668.field_21366).method_23610(class_4668.field_21358).method_23616(class_4668.field_21350).method_23604(class_4668.field_21346).method_23603(class_4668.field_21345).method_23617(false));
    }

    public static void drawImageQuad(class_4588 vertices, Matrix4f matrix, float posX, float posY, float posZ, float halfSize, int color) {
        vertices.method_22918(matrix, posX - halfSize, posY - halfSize, posZ).method_22913(0f, 1f).method_39415(color);
        vertices.method_22918(matrix, posX - halfSize, posY + halfSize, posZ).method_22913(0f, 0f).method_39415(color);
        vertices.method_22918(matrix, posX + halfSize, posY + halfSize, posZ).method_22913(1f, 0f).method_39415(color);
        vertices.method_22918(matrix, posX + halfSize, posY - halfSize, posZ).method_22913(1f, 1f).method_39415(color);
    }
}
