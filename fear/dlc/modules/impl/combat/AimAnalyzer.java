/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

import fear.dlc.api.commands.list.record.AimRecorder;
import fear.dlc.modules.impl.combat.NeuroSpeedCurve;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_3532;

public class AimAnalyzer {
    public static AnalyzedSettings analyze(List<AimRecorder.AimDataPoint> data) {
        if (data == null || data.size() < 400) {
            return new AnalyzedSettings();
        }
        FeatureVector features = AimAnalyzer.extractFeatures(data);
        NeuralOutput neuralOutput = NeuralNetwork.forward(features);
        HumanProfile profile = AimAnalyzer.classifyProfile(features, neuralOutput);
        AimSettings settings = AimAnalyzer.convertToAimSettings(features, neuralOutput, profile);
        SpeedCurve speedCurve = NeuroSpeedCurve.analyzeSpeedCurve(data);
        return new AnalyzedSettings(features, neuralOutput, profile, settings, speedCurve);
    }

    private static FeatureVector extractFeatures(List<AimRecorder.AimDataPoint> data) {
        double sumYaw = 0;
        double sumPitch = 0;
        double maxYaw = 0;
        double maxPitch = 0;
        double sumAccel = 0;
        double microCount = 0;
        double microIntensitySum = 0;
        int onTargetCount = 0;
        int size = data.size();
        List<Double> yawSpeeds = new ArrayList();
        for (AimDataPoint p : data) {
            sumYaw = sumYaw + (double)p.yawSpeed;
            sumPitch = sumPitch + (double)p.pitchSpeed;
            maxYaw = Math.max(maxYaw, (double)p.yawSpeed);
            maxPitch = Math.max(maxPitch, (double)p.pitchSpeed);
            yawSpeeds.add((double)p.yawSpeed);
            if (!p.onTarget) continue;
            onTargetCount++;
            if (p.isMicroMovement) {
                microCount = microCount + 1;
                microIntensitySum = microIntensitySum + (double)p.microIntensity;
            }
        }
        for (i = 1; i < size; i++) {
            AimDataPoint curr = data.get(i);
            AimDataPoint prev = data.get(i - 1);
            sumAccel = sumAccel + (double)(Math.abs(curr.yawSpeed - prev.yawSpeed) + Math.abs(curr.pitchSpeed - prev.pitchSpeed));
        }
        double avgYaw = sumYaw / (double)size;
        double avgPitch = sumPitch / (double)size;
        double onTargetRatio = onTargetCount / (double)size;
        double microFrequency = microCount / (double)size;
        double avgMicroIntensity = microCount > 0 ? microIntensitySum / microCount : 0;
        double avgAccel = size > 1 ? sumAccel / (double)(size - 1) : 0;
        double consistency = AimAnalyzer.calculateConsistency(yawSpeeds);
        double flickRatio = AimAnalyzer.calculateFlickRatio(data);
        return new FeatureVector(avgYaw, avgPitch, maxYaw, maxPitch, onTargetRatio, microFrequency, avgMicroIntensity, avgAccel, 1, consistency, flickRatio, 0.65);
    }

    private static double calculateConsistency(List<Double> speeds) {
        double mean = speeds.stream().mapToDouble(AimAnalyzer::lambda$calculateConsistency$0).average().orElse(0);
        double variance = speeds.stream().mapToDouble(AimAnalyzer::lambda$calculateConsistency$1 /* captured: mean */).average().orElse(0);
        return class_3532.method_15350(1 - Math.sqrt(variance) / 45, 0.1, 1);
    }

    private static double calculateFlickRatio(List<AimRecorder.AimDataPoint> data) {
        int flicks = 0;
        for (AimDataPoint p : data) {
            if (p.isMicroMovement) {
                if (p.microIntensity <= 1.8f) continue;
                flicks++;
            }
        }
        return data.isEmpty() ? 0 : (double)flicks / (double)data.size();
    }

    private static HumanProfile classifyProfile(FeatureVector f, NeuralOutput out) {
        if (out.aggression > 0.65 && f.flickRatio > 0.12) {
            return HumanProfile.FLICKER;
        }
        if (out.smoothness > 0.7 && f.consistency > 0.75) {
            return HumanProfile.SMOOTH;
        }
        if (f.avgYaw > 65) {
            return HumanProfile.AGGRESSIVE;
        }
        return HumanProfile.ADAPTIVE;
    }

    private static AimSettings convertToAimSettings(FeatureVector f, NeuralOutput out, HumanProfile profile) {
        float maxYawSpeed = AimAnalyzer.clamp(f.avgYaw / 19.2, 1.2, 13.5);
        float maxPitchSpeed = AimAnalyzer.clamp(f.avgPitch / 20.8, 0.6, 7.5);
        float smoothFactor = AimAnalyzer.clamp(0.28 + out.smoothness * 0.26 - f.avgAccel / 420, 0.16, 0.58);
        float acceleration = AimAnalyzer.clamp(0.32 + out.aggression * 0.38, 0.25, 0.72);
        float deceleration = acceleration * 0.79f;
        switch (profile.ordinal()) {
            case 0:
                acceleration = acceleration * 1.2f;
                deceleration = deceleration * 0.8f;
            case 1:
                acceleration = acceleration * 0.8f;
                deceleration = deceleration * 1.2f;
            case 2:
                acceleration = acceleration * 1.4f;
                deceleration = deceleration * 1.1f;
            case 3:
            default:
                float noiseStrength = AimAnalyzer.clamp(0.9 + (1 - out.smoothness) * 2.4, 0.6, 3.2);
                float microStrength = AimAnalyzer.clamp(f.avgMicroIntensity * 1.25, 0.4, 2.1);
                float onTargetMultiplier = AimAnalyzer.clamp(56 + f.onTargetRatio * 42, 52, 85);
                return new AimSettings(maxYawSpeed, maxPitchSpeed, smoothFactor, acceleration, deceleration, noiseStrength, 0.028f, microStrength, 0.95f, 1.45f, 7.5f, onTargetMultiplier, (float)f.microFrequency, (float)f.avgMicroIntensity);
        }
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
