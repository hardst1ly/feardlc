/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.animations.implement;

import fear.dlc.api.animations.Animation;

public class DecelerateAnimation
extends Animation {
    public double calculation(double value) {
        double x = value / (double)this.ms;
        return 1 - (x - 1) * (x - 1);
    }
}
