/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.utility;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Stack;
import net.minecraft.class_310;

public class Scissors {
    static Stack<Builder> stack = new Stack();

    public static void push(float x, float y, float width, float height) {
        int scale = class_310.method_1551().method_22683().method_4495();
        int winHeight = class_310.method_1551().method_22683().method_4507();
        int sx = Math.round(x * (float)scale);
        int sy = winHeight - Math.round((y + height) * (float)scale);
        int sw = Math.round(width * (float)scale);
        int sh = Math.round(height * (float)scale);
        if (!stack.isEmpty()) {
            Builder p = stack.peek();
            int ex = Math.max(sx, p.x);
            int ey = Math.max(sy, p.y);
            int ex2 = Math.min(sx + sw, p.x + p.width);
            int ey2 = Math.min(sy + sh, p.y + p.height);
            sx = ex;
            sy = ey;
            sw = Math.max(0, ex2 - ex);
            sh = Math.max(0, ey2 - ey);
        }
        Builder builder = new Builder().set(sx, sy, sw, sh);
        stack.push(builder);
        builder.apply();
    }

    public static void pop() {
        if (!(stack.isEmpty())) {
            stack.pop();
        }
        if (stack.isEmpty()) {
            RenderSystem.disableScissor();
        } else {
            ((Builder)stack.peek()).apply();
        }
    }
}
