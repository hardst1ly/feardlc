/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

import fear.dlc.modules.impl.combat.AimAnalyzer;
import net.minecraft.class_3532;

private static class AimAnalyzer.NeuralNetwork {
    private AimAnalyzer.NeuralNetwork() {
    }

    public static AimAnalyzer.NeuralOutput forward(AimAnalyzer.FeatureVector f) {
        double h1 = AimAnalyzer.NeuralNetwork.relu(f.avgYaw * 0.085 + f.microFrequency * 12.5 - f.consistency * 3.2 + 0.8);
        double h2 = AimAnalyzer.NeuralNetwork.relu(f.onTargetRatio * 5.5 + f.maxYaw * 0.065 - f.avgAccel * 0.12 + 1.1);
        double h3 = AimAnalyzer.NeuralNetwork.relu(f.avgMicroIntensity * 7.5 + f.flickRatio * 8.5 - 2.3);
        double h4 = AimAnalyzer.NeuralNetwork.relu(f.consistency * 4.2 + f.trackingStability * 3.1 - 1.4);
        double h5 = AimAnalyzer.NeuralNetwork.relu(h1 * 0.6 + h2 * 0.8 + h3 * 0.4 - 1.2);
        double h6 = AimAnalyzer.NeuralNetwork.relu(h2 * 0.7 + h4 * 0.9 - 0.8);
        double humanLikeness = AimAnalyzer.NeuralNetwork.sigmoid(h5 * 0.75 + h6 * 0.45 - 0.9);
        double aggression = AimAnalyzer.NeuralNetwork.sigmoid(h1 * 1.1 + h3 * 0.8 - 1.3);
        double smoothness = AimAnalyzer.NeuralNetwork.sigmoid(-h1 * 0.6 + h4 * 1.2);
        double[] tmp0 = new double[6];
        tmp0[0] = h1;
        tmp0[1] = h2;
        tmp0[2] = h3;
        tmp0[3] = h4;
        tmp0[4] = h5;
        tmp0[5] = h6;
        return new AimAnalyzer.NeuralOutput(class_3532.method_15350(humanLikeness * 100, 62, 96), aggression, smoothness, tmp0);
    }

    private static double relu(double x) {
        return Math.max(0, x);
    }

    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
