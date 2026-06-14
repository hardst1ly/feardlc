/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.animations.implement;

import fear.dlc.api.animations.Animation;

public class InOutCircAnimation
extends Animation {
    public double calculation(double value) {
        double x = value / (double)this.ms;
        return x < 0.5 ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2 : (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2;
    }
}
