/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.math;

import fear.dlc.Api;
import net.minecraft.class_243;
import net.minecraft.class_4184;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

public class MathProjection
implements Api {
    public static final Matrix4f lastProjMat = new Matrix4f();
    public static final Matrix4f lastModMat = new Matrix4f();
    public static final Matrix4f lastWorldSpaceMatrix = new Matrix4f();

    public static class_243 projectCoordinates(class_243 pos) {
        class_4184 camera = mc.method_1561().field_4686;
        if (camera == null) {
            return new class_243(0, 0, 0);
        }
        int displayHeight = mc.method_22683().method_4507();
        int[] viewport = new int[4];
        GL11.glGetIntegerv(2978, viewport);
        Vector3f target = new Vector3f();
        double deltaX = pos.field_1352 - camera.method_19326().field_1352;
        double deltaY = pos.field_1351 - camera.method_19326().field_1351;
        double deltaZ = pos.field_1350 - camera.method_19326().field_1350;
        Vector4f transformedCoordinates = new Vector4f((float)deltaX, (float)deltaY, (float)deltaZ, 1f).mul(lastWorldSpaceMatrix);
        Matrix4f matrixProj = new Matrix4f(lastProjMat);
        Matrix4f matrixModel = new Matrix4f(lastModMat);
        matrixProj.mul(matrixModel).project(transformedCoordinates.x(), transformedCoordinates.y(), transformedCoordinates.z(), viewport, target);
        return new class_243((double)target.x / mc.method_22683().method_4495(), (double)((float)displayHeight - target.y) / mc.method_22683().method_4495(), (double)target.z);
    }
}
