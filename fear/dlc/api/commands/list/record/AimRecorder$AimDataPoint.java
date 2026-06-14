/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.record;

public static class AimRecorder.AimDataPoint {
    public final long timestamp;
    public final float yaw;
    public final float pitch;
    public final float yawSpeed;
    public final float pitchSpeed;
    public final boolean onTarget;
    public final boolean isMicroMovement;
    public final float microIntensity;

    public AimRecorder.AimDataPoint(long timestamp, float yaw, float pitch, float yawSpeed, float pitchSpeed, boolean onTarget, boolean isMicroMovement, float microIntensity) {
        this.timestamp = timestamp;
        this.yaw = yaw;
        this.pitch = pitch;
        this.yawSpeed = yawSpeed;
        this.pitchSpeed = pitchSpeed;
        this.onTarget = onTarget;
        this.isMicroMovement = isMicroMovement;
        this.microIntensity = microIntensity;
    }
}
