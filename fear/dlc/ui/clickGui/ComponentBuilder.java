/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui;

import net.minecraft.class_332;

public interface ComponentBuilder {
    public ComponentBuilder position(float var1, float var2);

    public ComponentBuilder size(float var1, float var2);

    public ComponentBuilder render(class_332 var1, int var2, int var3, float var4);

    public boolean mouseClicked(double var1, double var3, int var5);

    public boolean mouseReleased(double var1, double var3, int var5);

    public boolean keyPressed(int var1, int var2, int var3);

    public boolean keyReleased(int var1, int var2, int var3);

    public boolean charTyped(char var1, int var2);

    public boolean mouseScrolled(double var1, double var3, double var5, double var7);

    public void init();
}
