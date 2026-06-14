/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.draggable.interfaces;

import net.minecraft.class_332;
import net.minecraft.class_9779;

public interface DraggableLayer {
    public void render(class_332 var1, double var2, double var4, class_9779 var6);

    public boolean mouseClicked(double var1, double var3, int var5);

    public boolean mouseReleased(double var1, double var3, int var5);

    public boolean keyPressed(int var1, int var2, int var3);

    public boolean keyReleased(int var1, int var2, int var3);

    public boolean charTyped(char var1, int var2);

    public void tick();
}
