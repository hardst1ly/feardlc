/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

public static class AimAnalyzer.AimSettings {
    public final float maxYawSpeed;
    public final float maxPitchSpeed;
    public final float smoothFactor;
    public final float acceleration;
    public final float deceleration;
    public final float noiseStrength;
    public final float noiseFrequency;
    public final float microStrength;
    public final float finalError;
    public final float multiPointSpeed;
    public final float multiPointSmoothSpeed;
    public final float onTargetSpeedMultiplier;
    public final float microMovementChance;
    public final float microMovementIntensity;

    public AimAnalyzer.AimSettings(float maxYawSpeed, float maxPitchSpeed, float smoothFactor, float acceleration, float deceleration, float noiseStrength, float noiseFrequency, float microStrength, float finalError, float multiPointSpeed, float multiPointSmoothSpeed, float onTargetSpeedMultiplier, float microMovementChance, float microMovementIntensity) {
        this.maxYawSpeed = maxYawSpeed;
        this.maxPitchSpeed = maxPitchSpeed;
        this.smoothFactor = smoothFactor;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.noiseStrength = noiseStrength;
        this.noiseFrequency = noiseFrequency;
        this.microStrength = microStrength;
        this.finalError = finalError;
        this.multiPointSpeed = multiPointSpeed;
        this.multiPointSmoothSpeed = multiPointSmoothSpeed;
        this.onTargetSpeedMultiplier = onTargetSpeedMultiplier;
        this.microMovementChance = microMovementChance;
        this.microMovementIntensity = microMovementIntensity;
    }
}
