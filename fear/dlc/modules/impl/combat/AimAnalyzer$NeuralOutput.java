/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

public static class AimAnalyzer.NeuralOutput {
    public final double humanLikeness;
    public final double aggression;
    public final double smoothness;
    public final double[] hiddenActivations;

    public AimAnalyzer.NeuralOutput(double humanLikeness, double aggression, double smoothness, double[] hidden) {
        this.humanLikeness = humanLikeness;
        this.aggression = aggression;
        this.smoothness = smoothness;
        this.hiddenActivations = hidden;
    }
}
