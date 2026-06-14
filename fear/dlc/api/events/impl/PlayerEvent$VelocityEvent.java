/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.impl.PlayerEvent;
import net.minecraft.class_243;

public static class PlayerEvent.VelocityEvent
extends PlayerEvent {
    private class_243 input;
    private float speed;
    private float yaw;
    private class_243 velocity;

    public void setInput(class_243 input) {
        this.input = input;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setVelocity(class_243 velocity) {
        this.velocity = velocity;
    }

    public class_243 getInput() {
        return this.input;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getYaw() {
        return this.yaw;
    }

    public class_243 getVelocity() {
        return this.velocity;
    }

    public PlayerEvent.VelocityEvent(class_243 input, float speed, float yaw, class_243 velocity) {
        this.input = input;
        this.speed = speed;
        this.yaw = yaw;
        this.velocity = velocity;
    }
}
