/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.animations.implement;

import fear.dlc.api.animations.Animation;

public class OutBackAnimation
extends Animation {
    public double calculation(double value) {
        double x = value / (double)this.ms;
        double c1 = 1.70158;
        double c3 = c1 + 1;
        return 1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2);
    }
}
