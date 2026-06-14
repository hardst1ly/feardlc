/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.utility;

import com.mojang.blaze3d.systems.RenderSystem;

private static class Scissors.Builder {
    int x;
    int y;
    int width;
    int height;

    private Scissors.Builder() {
    }

    public Scissors.Builder set(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public void apply() {
        RenderSystem.enableScissor(this.x, this.y, this.width, this.height);
    }
}
