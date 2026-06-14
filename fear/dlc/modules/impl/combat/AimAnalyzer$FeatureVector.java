/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

public static class AimAnalyzer.FeatureVector {
    public final double avgYaw;
    public final double avgPitch;
    public final double maxYaw;
    public final double maxPitch;
    public final double onTargetRatio;
    public final double microFrequency;
    public final double avgMicroIntensity;
    public final double avgAccel;
    public final double accelVariance;
    public final double consistency;
    public final double flickRatio;
    public final double trackingStability;

    public AimAnalyzer.FeatureVector(double avgYaw, double avgPitch, double maxYaw, double maxPitch, double onTargetRatio, double microFrequency, double avgMicroIntensity, double avgAccel, double accelVariance, double consistency, double flickRatio, double trackingStability) {
        this.avgYaw = avgYaw;
        this.avgPitch = avgPitch;
        this.maxYaw = maxYaw;
        this.maxPitch = maxPitch;
        this.onTargetRatio = onTargetRatio;
        this.microFrequency = microFrequency;
        this.avgMicroIntensity = avgMicroIntensity;
        this.avgAccel = avgAccel;
        this.accelVariance = accelVariance;
        this.consistency = consistency;
        this.flickRatio = flickRatio;
        this.trackingStability = trackingStability;
    }
}
