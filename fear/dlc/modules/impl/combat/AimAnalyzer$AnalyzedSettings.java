/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

import fear.dlc.modules.impl.combat.AimAnalyzer;
import fear.dlc.modules.impl.combat.NeuroSpeedCurve;

public static class AimAnalyzer.AnalyzedSettings {
    public final AimAnalyzer.FeatureVector features;
    public final AimAnalyzer.NeuralOutput neuralOutput;
    public final AimAnalyzer.HumanProfile profile;
    public final AimAnalyzer.AimSettings aimSettings;
    public final NeuroSpeedCurve.SpeedCurve speedCurve;
    public final int dataPoints;

    public AimAnalyzer.AnalyzedSettings() {
        this(null, null, AimAnalyzer.HumanProfile.ADAPTIVE, new AimAnalyzer.AimSettings(5.5f, 2.8f, 0.35f, 0.38f, 0.3f, 1.4f, 0.028f, 0.85f, 0.9f, 1.4f, 6.8f, 68f, 0.09f, 0.65f), NeuroSpeedCurve.createDefaultCurve());
    }

    public AimAnalyzer.AnalyzedSettings(AimAnalyzer.FeatureVector f, AimAnalyzer.NeuralOutput n, AimAnalyzer.HumanProfile p, AimAnalyzer.AimSettings s, NeuroSpeedCurve.SpeedCurve c) {
        this.features = f;
        this.neuralOutput = n;
        this.profile = p;
        this.aimSettings = s;
        this.speedCurve = c;
        this.dataPoints = f != null ? (int)(f.avgYaw * 10) : 0;
    }
}
