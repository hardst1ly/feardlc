/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.EventLayer;

public class MovementInputEvent
extends EventLayer {
    private float forward;
    private float sideways;
    private boolean jumping;
    private boolean sneaking;

    public MovementInputEvent(float forward, float sideways, boolean jumping, boolean sneaking) {
        this.forward = forward;
        this.sideways = sideways;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }

    public float getForward() {
        return this.forward;
    }

    public float getSideways() {
        return this.sideways;
    }

    public boolean isJumping() {
        return this.jumping;
    }

    public boolean isSneaking() {
        return this.sneaking;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public void setSideways(float sideways) {
        this.sideways = sideways;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }
}
