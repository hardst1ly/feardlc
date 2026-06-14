/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.record;

import fear.dlc.Api;
import fear.dlc.FearDCP;
import fear.dlc.modules.impl.combat.AimAnalyzer;
import fear.dlc.modules.impl.combat.AimAssistModule;
import fear.dlc.modules.impl.combat.neural.NeuralAimNetwork;
import fear.dlc.modules.more.FriendManager;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBlur;
import fear.dlc.utility.render.renderers.impl.BuiltShadow;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.class_1657;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_9779;

public class AimRecorder
implements Api {
    private static AimRecorder instance;
    private boolean isRecording = false;
    private final List<AimDataPoint> recordedData = new ArrayList(16000);
    private long recordStartTime = 0L;
    private int currentSamples = 0;
    private float prevYaw = 0f;
    private float prevPitch = 0f;
    private long prevTime = 0L;
    private long lastSampleTime = 0L;
    private float prevYawSpeed = 0f;
    private float prevPitchSpeed = 0f;
    private class_1657 lockedTarget = null;
    private static final float LOCK_DISTANCE = 3.3f;
    private long lastTargetSearchTime = 0L;
    private static final long TARGET_SEARCH_INTERVAL = 180L;
    private static final int MAX_SAMPLES = 15000;
    private static final long MIN_SAMPLE_INTERVAL_MS = 5L;
    private static final float MICRO_MOVEMENT_THRESHOLD = 50f;

    private AimRecorder() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
        HudRenderCallback.EVENT.register(this::onHudRender);
    }

    public static AimRecorder getInstance() {
        if (instance == null) {
            instance = new AimRecorder();
        }
        return instance;
    }

    private void onHudRender(class_332 context, class_9779 tickCounter) {
        if (!this.isRecording || mc.field_1724 == null) {
            return;
        }
        String statusText = this.lockedTarget != null ? "NEURAL TRAINING" : "WAITING FOR TARGET...";
        Object[] tmp0 = new Object[2];
        tmp0[0] = this.currentSamples;
        tmp0[1] = 15000;
        String samplesText = String.format("SAMPLES: %d / %d", tmp0);
        Object[] tmp1 = new Object[1];
        tmp1[0] = (float)this.currentSamples * 100f / 15000f;
        String progressText = String.format("%.1f%%", tmp1);
        int statusColor = this.lockedTarget != null ? -16711800 : -48060;
        float x = 8f;
        float y = context.method_51443() - 58f;
        float width = 260f;
        float height = 52f;
        this.renderNeuralBackground(context, x - 5f, y - 5f, width, height);
        ((BuiltText)Api.text().text(statusText).font(Api.inter()).color(statusColor).size(8.4f).build()).render(context.method_51448().method_23760().method_23761(), x, y);
        ((BuiltText)Api.text().text(samplesText).font(Api.inter()).color(-1514497).size(7.6f).build()).render(context.method_51448().method_23760().method_23761(), x, y + 13.5f);
        ((BuiltText)Api.text().text(progressText).font(Api.inter()).color(this.getProgressColor((float)this.currentSamples / 15000f)).size(7.6f).build()).render(context.method_51448().method_23760().method_23761(), x + 170f, y + 13.5f);
        if (this.currentSamples > 800) {
            int sim = 68 + this.currentSamples % 28;
            ((BuiltText)Api.text().text("HUMAN SIM: " + sim + "%").font(Api.inter()).color(-5570646).size(6.8f).build()).render(context.method_51448().method_23760().method_23761(), x, y + 27f);
        }
    }

    private int getProgressColor(float progress) {
        if (progress < 0.33f) {
            return -256;
        }
        if (progress < 0.66f) {
            return -22016;
        }
        return -16711936;
    }

    private void renderNeuralBackground(class_332 context, float x, float y, float width, float height) {
        ((BuiltBlur)Api.blur().blurRadius(25f).radius(new QuadRadiusState(10f)).size(new SizeState(width, height)).build()).render(context.method_51448().method_23760().method_23761(), x, y);
        ((BuiltShadow)Api.shadow().size(new SizeState(width + 4f, height + 4f)).radius(new QuadRadiusState(11f)).shadow(10f).color(new QuadColorState(ColorUtility.applyOpacity(-15791585, 55))).build()).render(context.method_51448().method_23760().method_23761(), x - 2f, y - 2f);
        ((BuiltBlur)Api.blur().blurRadius(32f).radius(new QuadRadiusState(10f)).size(new SizeState(width, height)).color(new QuadColorState(new Color(-1441131469, true), new Color(-1439816363, true), new Color(-1440802752, true), new Color(-1439487386, true))).build()).render(context.method_51448().method_23760().method_23761(), x, y);
    }

    private void onTick(class_310 client) {
        if (!this.isRecording || mc.field_1724 == null) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastSampleTime < 5L) {
            return;
        }
        float currentYaw = mc.field_1724.method_36454();
        float currentPitch = mc.field_1724.method_36455();
        if (currentTime - this.lastTargetSearchTime > 180L || this.lockedTarget == null) {
            this.updateLockedTarget();
            this.lastTargetSearchTime = currentTime;
        }
        boolean shouldRecord = this.lockedTarget != null;
        boolean onTarget = this.isLookingAtPlayer();
        if (this.prevTime > 0L) {
            if (shouldRecord) {
                float deltaTime = (currentTime - this.prevTime) / 1000f;
                if (deltaTime > 0f) {
                    if (deltaTime < 0.5f) {
                        float yawSpeed = Math.abs(currentYaw - this.prevYaw) / deltaTime;
                        float pitchSpeed = Math.abs(currentPitch - this.prevPitch) / deltaTime;
                        if (yawSpeed > 0.5f || pitchSpeed > 0.5f) {
                            boolean isMicro = false;
                            float microIntensity = 0f;
                            if (this.prevYawSpeed > 0f || this.prevPitchSpeed > 0f) {
                                float yawAccel = Math.abs(yawSpeed - this.prevYawSpeed);
                                float pitchAccel = Math.abs(pitchSpeed - this.prevPitchSpeed);
                                float totalAccel = Math.sqrt((double)(yawAccel * yawAccel + pitchAccel * pitchAccel));
                                if (totalAccel > 50f) {
                                    isMicro = true;
                                    microIntensity = Math.min(totalAccel / 50f, 3f);
                                }
                            }
                            this.recordedData.add(new AimDataPoint(currentTime - this.recordStartTime, currentYaw, currentPitch, yawSpeed, pitchSpeed, onTarget, isMicro, microIntensity));
                            this.currentSamples = this.currentSamples + 1;
                            this.lastSampleTime = currentTime;
                            this.prevYawSpeed = yawSpeed;
                            this.prevPitchSpeed = pitchSpeed;
                            if (this.currentSamples >= 15000) {
                                this.stopRecording();
                            }
                        }
                    }
                }
            }
        }
        this.prevYaw = currentYaw;
        this.prevPitch = currentPitch;
        this.prevTime = currentTime;
    }

    private void updateLockedTarget() {
        if (mc.field_1724 == null || mc.field_1687 == null) {
            this.lockedTarget = null;
            return;
        }
        if (this.lockedTarget != null && this.lockedTarget.method_5805() && mc.field_1724.method_5858(this.lockedTarget) <= 10.889999389648438) {
            return;
        }
        this.lockedTarget = this.findBestTarget();
    }

    private class_1657 findBestTarget() {
        if (mc.field_1724 == null || mc.field_1687 == null) {
            return null;
        }
        class_1657 best = null;
        double bestScore = 179769313486231570000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000;
        for (class_1657 player : mc.field_1687.method_18456()) {
            if (player == mc.field_1724) continue;
            if (!player.method_5805()) continue;
            while (player.method_7325()) {
            }
            while (FriendManager.isFriend(player.method_5477().getString().toLowerCase())) {
            }
            double distSq = mc.field_1724.method_5858(player);
            while (distSq > 10.889999389648438) {
            }
            while (!this.canSeeTarget(player)) {
            }
            double angle = this.angleToEntity(player);
            double score = distSq * 0.3 + angle * 0.7;
            if (score < bestScore) {
                bestScore = score;
                best = player;
            }
        }
        return best;
    }

    private double angleToEntity(class_1657 target) {
        if (mc.field_1724 == null) {
            return 180;
        }
        class_243 look = mc.field_1724.method_5828(1f);
        class_243 toTarget = target.method_33571().method_1020(mc.field_1724.method_33571()).method_1029();
        double dot = Math.max(-1, Math.min(1, look.method_1026(toTarget)));
        return Math.acos(dot) * 57.29577951308232;
    }

    private boolean canSeeTarget(class_1657 target) {
        if (mc.field_1724 == null || mc.field_1687 == null || target == null) {
            return false;
        }
        class_243 eyePos = mc.field_1724.method_33571();
        class_243 targetPos = target.method_33571();
        class_3965 hit = mc.field_1687.method_17742(new class_3959(eyePos, targetPos, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, mc.field_1724));
        return hit == null || hit.method_17783() == class_239.class_240.field_1333;
    }

    private boolean isLookingAtPlayer() {
        var var2 = mc.field_1765;
        if (var2 instanceof class_3966) {
            class_3966 ehr = var2;
            return ehr.method_17782() instanceof class_1657 && ehr.method_17782() != mc.field_1724;
        }
        return false;
    }

    public void startRecording() {
        if (this.isRecording) {
            return;
        }
        this.isRecording = true;
        this.recordedData.clear();
        this.currentSamples = 0;
        this.recordStartTime = System.currentTimeMillis();
        this.prevTime = 0L;
        this.lastSampleTime = 0L;
        this.lockedTarget = null;
        this.prevYawSpeed = 0f;
        this.prevPitchSpeed = 0f;
        if (mc.field_1724 != null) {
            this.prevYaw = mc.field_1724.method_36454();
            this.prevPitch = mc.field_1724.method_36455();
            mc.field_1724.method_7353(class_2561.method_43470("\u00a7a[Neural] \u00a7f\u0417\u0430\u043f\u0438\u0441\u044c \u043d\u0430\u0447\u0430\u0442\u0430..."), false);
        }
    }

    public void stopRecording() {
        if (!this.isRecording) {
            return;
        }
        this.isRecording = false;
        this.lockedTarget = null;
        System.out.println("[Neural] Recording stopped. Samples: " + this.currentSamples);
        if (this.recordedData.size() >= 450) {
            if (mc.field_1724 != null) {
                mc.field_1724.method_7353(class_2561.method_43470("\u00a7a[Neural] \u00a7f\u041e\u0431\u0443\u0447\u0435\u043d\u0438\u0435 \u043d\u0435\u0439\u0440\u043e\u0441\u0435\u0442\u0438 \u043d\u0430 \u0437\u0430\u043f\u0438\u0441\u0438..."), false);
            }
            NeuralAimNetwork network = new NeuralAimNetwork();
            network.trainOnRecording(this.recordedData, 220);
            AimSettings settings = network.generateSettings(this.recordedData);
            AimAssistModule aimAssist = FearDCP.getInstance().getModuleRepository().find(AimAssistModule.class);
            if (aimAssist != null) {
                AnalyzedSettings analyzed = new AimAnalyzer.AnalyzedSettings(null, null, AimAnalyzer.HumanProfile.ADAPTIVE, settings);
                aimAssist.loadNeuroSettings(analyzed);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_7353(class_2561.method_43470("\u00a7a[Neural] \u00a7f\u041d\u0435\u0439\u0440\u043e\u0441\u0435\u0442\u044c \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u043e\u0431\u0443\u0447\u0435\u043d\u0430 \u0438 \u043f\u0440\u0438\u043c\u0435\u043d\u0435\u043d\u0430!"), false);
                }
            }
        } else {
            if (mc.field_1724 != null) {
                mc.field_1724.method_7353(class_2561.method_43470("\u00a7c[Neural] \u00a7f\u041d\u0435\u0434\u043e\u0441\u0442\u0430\u0442\u043e\u0447\u043d\u043e \u0434\u0430\u043d\u043d\u044b\u0445 \u0434\u043b\u044f \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f (\u043c\u0438\u043d\u0438\u043c\u0443\u043c 450 samples)"), false);
            }
        }
    }

    public boolean isRecording() {
        return this.isRecording;
    }

    public List<AimDataPoint> getRecordedData() {
        return new ArrayList(this.recordedData);
    }

    public int getCurrentSamples() {
        return this.currentSamples;
    }

    public int getMaxSamples() {
        return 15000;
    }

    public void clearData() {
        this.recordedData.clear();
        this.currentSamples = 0;
    }
}
