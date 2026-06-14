/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

public static class NeuroSpeedCurve.SpeedCurve {
    public final float[] speedPoints;
    public final float[] timePoints;
    public final float avgSpeed;
    public final float peakSpeed;
    public final float smoothness;
    public final long[] timings;
    public final float movementBoostFactor;
    public final float adaptiveSpeedFactor;
    public final float accelerationPhaseDuration;
    public final float peakPhaseDuration;
    public final float decelerationPhaseDuration;
    public final float accelerationPower;
    public final float decelerationPower;

    public NeuroSpeedCurve.SpeedCurve(float[] speedPoints, float[] timePoints, float avgSpeed, float peakSpeed, float smoothness, long[] timings, float movementBoostFactor, float adaptiveSpeedFactor, float accelDuration, float peakDuration, float decelDuration, float accelPower, float decelPower) {
        this.speedPoints = speedPoints;
        this.timePoints = timePoints;
        this.avgSpeed = avgSpeed;
        this.peakSpeed = peakSpeed;
        this.smoothness = smoothness;
        this.timings = timings;
        this.movementBoostFactor = movementBoostFactor;
        this.adaptiveSpeedFactor = adaptiveSpeedFactor;
        this.accelerationPhaseDuration = accelDuration;
        this.peakPhaseDuration = peakDuration;
        this.decelerationPhaseDuration = decelDuration;
        this.accelerationPower = accelPower;
        this.decelerationPower = decelPower;
    }
}
