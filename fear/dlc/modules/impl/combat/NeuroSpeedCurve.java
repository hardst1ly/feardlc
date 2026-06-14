/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

import fear.dlc.api.commands.list.record.AimRecorder;
import fear.dlc.modules.impl.combat.AimAnalyzer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.class_3532;

public class NeuroSpeedCurve {
    private static final Random random = new Random();
    private static final float MAX_YAW_SPEED_RANDOMNESS = 0.15f;
    private static final float MAX_PITCH_SPEED_RANDOMNESS = 0.12f;
    private static final float SMOOTH_FACTOR_RANDOMNESS = 0.1f;
    private static final float ACCELERATION_RANDOMNESS = 0.15f;
    private static final float DECELERATION_RANDOMNESS = 0.12f;
    private static final float NOISE_STRENGTH_RANDOMNESS = 0.15f;
    private static final float MICRO_STRENGTH_RANDOMNESS = 0.15f;
    private static final float FINAL_ERROR_RANDOMNESS = 0.1f;
    private static final float MULTIPOINT_SPEED_RANDOMNESS = 0.12f;
    private static final float ONTARGET_SPEED_RANDOMNESS = 0.1f;
    private static final float MAX_TOTAL_DEVIATION = 0.2f;

    public static SpeedCurve analyzeSpeedCurve(List<AimRecorder.AimDataPoint> data) {
        if (data == null || data.isEmpty()) {
            return NeuroSpeedCurve.createDefaultCurve();
        }
        List<AimRecorder.AimDataPoint> sortedData = new ArrayList(data);
        sortedData.sort(NeuroSpeedCurve::lambda$analyzeSpeedCurve$0);
        int size = sortedData.size();
        long totalTime = ((AimRecorder.AimDataPoint)sortedData.get(size - 1)).timestamp - ((AimRecorder.AimDataPoint)sortedData.get(0)).timestamp;
        if (totalTime <= 0L) {
            return NeuroSpeedCurve.createDefaultCurve();
        }
        float maxSpeed = 0f;
        float totalSpeed = 0f;
        for (AimDataPoint point : sortedData) {
            float combinedSpeed = point.yawSpeed + point.pitchSpeed;
            if (combinedSpeed <= maxSpeed) continue;
            maxSpeed = combinedSpeed;
            totalSpeed = totalSpeed + combinedSpeed;
        }
        avgSpeed = totalSpeed / (float)size;
        long[] timings = new long[size - 1];
        for (int i = 1; i < size; i++) {
            timings[i - 1] = ((AimRecorder.AimDataPoint)sortedData.get(i)).timestamp - ((AimRecorder.AimDataPoint)sortedData.get(i - 1)).timestamp;
        }
        int segments = 20;
        float[] speedPoints = new float[segments];
        float[] timePoints = new float[segments];
        for (int i = 0; i < segments; i++) {
            timePoints[i] = (float)i / (float)(segments - 1);
            long segmentStart = ((float)totalTime * (float)i / (float)segments);
            long segmentEnd = ((float)totalTime * (float)(i + 1) / (float)segments);
            float segmentSpeed = 0f;
            int segmentCount = 0;
            for (AimDataPoint point : sortedData) {
                long relativeTime = point.timestamp - ((AimRecorder.AimDataPoint)sortedData.get(0)).timestamp;
                if (relativeTime >= segmentStart) {
                    if (relativeTime < segmentEnd) {
                        segmentSpeed = segmentSpeed + (point.yawSpeed + point.pitchSpeed);
                        segmentCount++;
                    }
                }
            }
            if (segmentCount > 0) {
                if (maxSpeed <= 0f) continue;
                speedPoints[i] = segmentSpeed / (float)segmentCount / maxSpeed;
            }
        }
        int peakIndex = 0;
        float peakValue = 0f;
        for (int i = 0; i < segments; i++) {
            if (speedPoints[i] > peakValue) {
                peakValue = speedPoints[i];
                peakIndex = i;
            }
        }
        float accelDuration = peakIndex / (float)segments;
        float accelPower = NeuroSpeedCurve.estimateCurvePower(speedPoints, 0, peakIndex);
        float decelDuration = (segments - 1 - peakIndex) / (float)segments;
        float decelPower = NeuroSpeedCurve.estimateCurvePower(speedPoints, peakIndex, segments - 1);
        float peakDuration = 2f / (float)segments;
        variance = 0f;
        for (int i = 1; i < segments; i++) {
            variance = variance + Math.abs(speedPoints[i] - speedPoints[i - 1]);
        }
        float smoothness = Math.max(0.1f, 1f - variance / (float)segments);
        float movementBoost = NeuroSpeedCurve.analyzeMovementBoost(sortedData);
        float adaptiveSpeed = NeuroSpeedCurve.analyzeAdaptiveSpeed(sortedData);
        return new SpeedCurve(speedPoints, timePoints, avgSpeed, peakValue, smoothness, timings, movementBoost, adaptiveSpeed, accelDuration, peakDuration, decelDuration, accelPower, decelPower);
    }

    private static float estimateCurvePower(float[] points, int start, int end) {
        if (end <= start || points.length == 0) {
            return 2f;
        }
        int count = end - start + 1;
        float[] segment = new float[count];
        for (int i = 0; i < count; i++) {
            segment[i] = points[start + i];
        }
        float min = 340282350000000000000000000000000000000f;
        float max = 0.000000000000000000000000000000000000000000001f;
        int i = segment;
        float power2Error = i.length;
        for (float power3Error = 0; power3Error < power2Error; power3Error++) {
            float v = i[power3Error];
            if (v >= min) continue;
            min = v;
            if (v <= max) continue;
            max = v;
        }
        if (max - min < 0.01f) {
            return 2f;
        }
        for (i = 0; i < count; i++) {
            segment[i] = (segment[i] - min) / (max - min);
        }
        float linearError = 0f;
        power2Error = 0f;
        power3Error = 0f;
        for (int i = 0; i < count; i++) {
            float t = i / (float)(count - 1);
            float linear = t;
            float power2 = t * t;
            float power3 = t * t * t;
            linearError = linearError + Math.abs(segment[i] - linear);
            power2Error = power2Error + Math.abs(segment[i] - power2);
            power3Error = power3Error + Math.abs(segment[i] - power3);
        }
        if (power2Error < linearError && power2Error < power3Error) {
            return 2f;
        }
        if (power3Error < linearError && power3Error < power2Error) {
            return 3f;
        }
        return 1.5f;
    }

    private static float analyzeMovementBoost(List<AimRecorder.AimDataPoint> data) {
        return 0.15f;
    }

    private static float analyzeAdaptiveSpeed(List<AimRecorder.AimDataPoint> data) {
        if (data.size() < 10) {
            return 1f;
        }
        float smallAngleSpeed = 0f;
        float largeAngleSpeed = 0f;
        int smallCount = 0;
        int largeCount = 0;
        for (int i = 1; i < data.size(); i++) {
            AimDataPoint curr = data.get(i);
            AimDataPoint prev = data.get(i - 1);
            float angleChange = Math.sqrt(Math.pow((double)(curr.yawSpeed - prev.yawSpeed), 2) + Math.pow((double)(curr.pitchSpeed - prev.pitchSpeed), 2));
            float speed = curr.yawSpeed + curr.pitchSpeed;
            if (angleChange < 5f) {
                smallAngleSpeed = smallAngleSpeed + speed;
                smallCount++;
            } else {
                if (angleChange > 20f) {
                    largeAngleSpeed = largeAngleSpeed + speed;
                    largeCount++;
                }
            }
        }
        float avgSmall = smallAngleSpeed / (float)smallCount;
        if (smallCount > 0 && largeCount > 0) {
            float avgLarge = largeAngleSpeed / (float)largeCount;
            float ratio = avgLarge / Math.max(avgSmall, 0.001f);
            return class_3532.method_15363(ratio, 0.5f, 2f);
        }
        return 1f;
    }

    static SpeedCurve createDefaultCurve() {
        float[] tmp0 = new float[20];
        tmp0[0] = 0.15f;
        tmp0[1] = 0.25f;
        tmp0[2] = 0.4f;
        tmp0[3] = 0.6f;
        tmp0[4] = 0.8f;
        tmp0[5] = 0.95f;
        tmp0[6] = 1f;
        tmp0[7] = 0.95f;
        tmp0[8] = 0.85f;
        tmp0[9] = 0.7f;
        tmp0[10] = 0.55f;
        tmp0[11] = 0.4f;
        tmp0[12] = 0.3f;
        tmp0[13] = 0.35f;
        tmp0[14] = 0.45f;
        tmp0[15] = 0.5f;
        tmp0[16] = 0.45f;
        tmp0[17] = 0.35f;
        tmp0[18] = 0.25f;
        tmp0[19] = 0.15f;
        float[] speedPoints = tmp0;
        float[] timePoints = new float[20];
        for (int i = 0; i < 20; i++) {
            timePoints[i] = (float)i / 19f;
        }
        long[] tmp1 = new long[4];
        tmp1[0] = 16L;
        tmp1[1] = 17L;
        tmp1[2] = 16L;
        tmp1[3] = 15L;
        return new SpeedCurve(speedPoints, timePoints, 0.5f, 1f, 0.7f, tmp1, 0.15f, 1f, 0.3f, 0.1f, 0.3f, 2f, 2.5f);
    }

    private static float applyMinimalRandomization(float baseValue, float randomnessCoeff) {
        double gaussian = random.nextGaussian();
        gaussian = class_3532.method_15350(gaussian, -2, 2);
        float deviation = (gaussian * (double)randomnessCoeff);
        deviation = class_3532.method_15363(deviation, -0.2f, 0.2f);
        return baseValue * (1f + deviation);
    }

    public static NeuroRandomizedSettings randomizeSettings(AimAnalyzer.AimSettings baseSettings, SpeedCurve curve) {
        float accelInfluence = curve != null ? curve.accelerationPower / 4f : 0.5f;
        float decelInfluence = curve != null ? curve.decelerationPower / 4f : 0.6f;
        return new NeuroRandomizedSettings(NeuroSpeedCurve.applyMinimalRandomization(baseSettings.maxYawSpeed, 0.15f * accelInfluence), NeuroSpeedCurve.applyMinimalRandomization(baseSettings.maxPitchSpeed, 0.12f * accelInfluence), NeuroSpeedCurve.applyMinimalRandomization(baseSettings.smoothFactor, 0.1f * decelInfluence), NeuroSpeedCurve.applyMinimalRandomization(baseSettings.acceleration, 0.15f * accelInfluence), NeuroSpeedCurve.applyMinimalRandomization(baseSettings.deceleration, 0.12f * decelInfluence), NeuroSpeedCurve.applyMinimalRandomization(baseSettings.noiseStrength, 0.15f), baseSettings.noiseFrequency, NeuroSpeedCurve.applyMinimalRandomization(baseSettings.microStrength, 0.15f), NeuroSpeedCurve.applyMinimalRandomization(baseSettings.finalError, 0.1f), NeuroSpeedCurve.applyMinimalRandomization(baseSettings.multiPointSpeed, 0.12f), NeuroSpeedCurve.applyMinimalRandomization(baseSettings.multiPointSmoothSpeed, 0.12f), NeuroSpeedCurve.applyMinimalRandomization(baseSettings.onTargetSpeedMultiplier, 0.1f));
    }
}
