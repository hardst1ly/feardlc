/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat.neural;

import fear.dlc.api.commands.list.record.AimRecorder;
import fear.dlc.modules.impl.combat.AimAnalyzer;
import java.util.List;
import java.util.Random;

public class NeuralAimNetwork {
    private static final Random rand = new Random();
    private final int inputSize = 14;
    private final int[] layerSizes;
    private double[][][] weights;
    private double[][] biases;

    public NeuralAimNetwork() {
        this.inputSize = 14;
        int[] tmp0 = new int[5];
        tmp0[0] = 14;
        tmp0[1] = 32;
        tmp0[2] = 24;
        tmp0[3] = 16;
        tmp0[4] = 8;
        this.layerSizes = tmp0;
        this.initializeNetwork();
    }

    private void initializeNetwork() {
        this.weights = new double[this.layerSizes.length - 1][][];
        this.biases = new double[this.layerSizes.length - 1][];
        for (int i = 0; i < this.layerSizes.length - 1; i++) {
            this.weights[i] = new double[this.layerSizes[i + 1]][this.layerSizes[i]];
            this.biases[i] = new double[this.layerSizes[i + 1]];
            for (int j = 0; j < this.layerSizes[i + 1]; j++) {
                this.biases[i][j] = rand.nextGaussian() * 0.08;
                for (int k = 0; k < this.layerSizes[i]; k++) {
                    this.weights[i][j][k] = rand.nextGaussian() * Math.sqrt(2 / (double)this.layerSizes[i]);
                }
            }
        }
    }

    public void trainOnRecording(List<AimRecorder.AimDataPoint> data, int epochs) {
        if (data.size() < 350) {
            return;
        }
        double[] features = this.extractFeatures(data);
        for (int epoch = 0; epoch < epochs; epoch++) {
            double lr = 0.045 * (1 - (double)epoch / (double)epochs);
            this.fineTune(features, lr);
            if (epoch % 35 != 0) continue;
            this.slightMutation(0.009);
        }
    }

    private void fineTune(double[] input, double lr) {
        for (int i = 0; i < this.weights[0].length; i++) {
            for (int j = 0; j < this.weights[0][i].length; j++) {
                this.weights[0][i][j] = this.weights[0][i][j] + lr * rand.nextGaussian() * input[j] * 0.3;
            }
        }
    }

    private void slightMutation(double amount) {
        for (double[][] layer : this.weights) {
            for (double[] neuron : layer) {
                for (int i = 0; i < neuron.length; i++) {
                    neuron[i] = neuron[i] + rand.nextGaussian() * amount;
                }
            }
        }
    }

    private double[] extractFeatures(List<AimRecorder.AimDataPoint> data) {
        double[] f = new double[14];
        int n = data.size();
        double sumYaw = 0;
        double sumPitch = 0;
        double maxYaw = 0;
        double maxPitch = 0;
        double microSum = 0;
        double accelSum = 0;
        int microCount = 0;
        int onTarget = 0;
        for (int i = 0; i < n; i++) {
            AimDataPoint p = data.get(i);
            sumYaw = sumYaw + (double)p.yawSpeed;
            sumPitch = sumPitch + (double)p.pitchSpeed;
            maxYaw = Math.max(maxYaw, (double)p.yawSpeed);
            maxPitch = Math.max(maxPitch, (double)p.pitchSpeed);
            if (!p.onTarget) continue;
            onTarget++;
            if (p.isMicroMovement) {
                microCount++;
                microSum = microSum + (double)p.microIntensity;
            }
            if (i > 0) {
                AimDataPoint prev = data.get(i - 1);
                accelSum = accelSum + (double)(Math.abs(p.yawSpeed - prev.yawSpeed) + Math.abs(p.pitchSpeed - prev.pitchSpeed));
            }
        }
        f[0] = sumYaw / (double)n;
        f[1] = sumPitch / (double)n;
        f[2] = maxYaw;
        f[3] = maxPitch;
        f[4] = (double)onTarget / (double)n;
        f[5] = (double)microCount / (double)n;
        f[6] = microCount > 0 ? microSum / (double)microCount : 0;
        f[7] = n > 1 ? accelSum / (double)(n - 1) : 0;
        f[8] = this.calculateConsistency(data);
        f[9] = this.calculateFlickRatio(data);
        f[10] = this.calculateVariance(data);
        f[11] = (double)n / 1200;
        f[12] = maxYaw / (maxPitch + 0.01);
        f[13] = this.calculateBurstiness(data);
        return f;
    }

    private double calculateConsistency(List<AimRecorder.AimDataPoint> data) {
        double mean = data.stream().mapToDouble(NeuralAimNetwork::lambda$calculateConsistency$0).average().orElse(0);
        double var = data.stream().mapToDouble(NeuralAimNetwork::lambda$calculateConsistency$1 /* captured: mean */).average().orElse(0);
        return 1 - Math.min(1, Math.sqrt(var) / 48);
    }

    private double calculateFlickRatio(List<AimRecorder.AimDataPoint> data) {
        long count = data.stream().filter(NeuralAimNetwork::lambda$calculateFlickRatio$2).count();
        return (double)count / (double)data.size();
    }

    private double calculateVariance(List<AimRecorder.AimDataPoint> data) {
        double mean = data.stream().mapToDouble(NeuralAimNetwork::lambda$calculateVariance$3).average().orElse(0);
        return data.stream().mapToDouble(NeuralAimNetwork::lambda$calculateVariance$4 /* captured: mean */).average().orElse(0);
    }

    private double calculateBurstiness(List<AimRecorder.AimDataPoint> data) {
        int burst = 0;
        for (int i = 2; i < data.size(); i++) {
            if (((AimRecorder.AimDataPoint)data.get(i)).isMicroMovement) {
                if (!((AimRecorder.AimDataPoint)data.get(i - 1)).isMicroMovement) continue;
                burst++;
            }
        }
        return (double)burst / (double)data.size();
    }

    public AimAnalyzer.AimSettings generateSettings(List<AimRecorder.AimDataPoint> data) {
        double[] features = this.extractFeatures(data);
        AccelDecelAnalysis accelAnalysis = this.analyzeAccelDecel(data);
        return new AimAnalyzer.AimSettings((float)(features[0] / 18.5), (float)(features[1] / 20.5), 0.26f + (float)(features[8] * 0.28), accelAnalysis.acceleration, accelAnalysis.deceleration, 1.1f + (float)(features[9] * 8), 0.027f, (float)features[6] * 1.45f, 0.9f, 1.45f, 7.2f, accelAnalysis.onTargetSpeedMultiplier, (float)features[5], (float)features[6]);
    }

    private AccelDecelAnalysis analyzeAccelDecel(List<AimRecorder.AimDataPoint> data) {
        if (data.size() < 100) {
            return new AccelDecelAnalysis(0.42f, 0.35f, 40f);
        }
        double totalAccel = 0;
        double totalDecel = 0;
        int accelCount = 0;
        int decelCount = 0;
        double onTargetSpeedSum = 0;
        int onTargetCount = 0;
        for (int i = 1; i < data.size(); i++) {
            AimDataPoint curr = data.get(i);
            AimDataPoint prev = data.get(i - 1);
            double speedChange = (curr.yawSpeed - prev.yawSpeed);
            if (!(Math.abs(speedChange) <= 0.1)) {
                if (speedChange > 0) {
                    totalAccel = totalAccel + speedChange;
                    accelCount++;
                } else {
                    totalDecel = totalDecel + Math.abs(speedChange);
                    decelCount++;
                }
            }
            if (curr.onTarget) {
                onTargetSpeedSum = onTargetSpeedSum + (double)curr.yawSpeed;
                onTargetCount++;
            }
        }
        double avgAccel = accelCount > 0 ? totalAccel / (double)accelCount : 1;
        double avgDecel = decelCount > 0 ? totalDecel / (double)decelCount : 1;
        double avgOnTargetSpeed = onTargetCount > 0 ? onTargetSpeedSum / (double)onTargetCount : 5;
        float acceleration = Math.max(0.1, Math.min(1, 0.2 + avgAccel * 0.05));
        float deceleration = Math.max(0.1, Math.min(1, 0.2 + avgDecel * 0.04));
        float onTargetMultiplier = Math.max(20, Math.min(80, 100 - avgOnTargetSpeed * 8));
        return new AccelDecelAnalysis(acceleration, deceleration, onTargetMultiplier);
    }
}
