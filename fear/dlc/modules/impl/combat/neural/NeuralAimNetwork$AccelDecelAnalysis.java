/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat.neural;

private static class NeuralAimNetwork.AccelDecelAnalysis {
    final float acceleration;
    final float deceleration;
    final float onTargetSpeedMultiplier;

    NeuralAimNetwork.AccelDecelAnalysis(float acceleration, float deceleration, float onTargetSpeedMultiplier) {
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.onTargetSpeedMultiplier = onTargetSpeedMultiplier;
    }
}
