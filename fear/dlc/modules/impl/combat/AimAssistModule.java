/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

import fear.dlc.modules.impl.combat.AimAnalyzer;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.FriendManager;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import fear.dlc.utility.math.RotationUtils;
import fear.dlc.utility.render.builders.Builder;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltTexture;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1738;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_465;
import net.minecraft.class_5321;
import net.minecraft.class_9779;
import org.joml.Matrix4f;

public class AimAssistModule
extends ModuleLayer {
    public final ModeSetting rotationMode;
    SliderSetting distance;
    SliderSetting fov;
    SliderSetting speed;
    public final BooleanSetting drawFov;
    SliderSetting aimHeight;
    public final BooleanSetting onlyPlayers;
    public final BooleanSetting noAimInInventory;
    public final BooleanSetting hitInvisible;
    public final BooleanSetting onlyArmored;
    public final BooleanSetting ignoreNaked;
    public final BooleanSetting onlyX;
    public final BooleanSetting multiPoint;
    public final BooleanSetting wallCheck;
    public final BooleanSetting disableOnWorldChange;
    public final BooleanSetting microMovements;
    public static boolean onSelfDestruct = false;
    public static class_1657 lockedTarget = null;
    private class_1044 circleTexture;
    private class_1297 currentTarget;
    private long targetLockTime;
    private long lastFrameTimeNanos;
    private double lastYawStep;
    private double lastPitchStep;
    private double noisePhaseYaw;
    private double noisePhasePitch;
    private double noiseSpeedYaw;
    private double noiseSpeedPitch;
    private double easingPower;
    private double speedVariation;
    private double microAccumulator;
    private double verticalNoise;
    private long lastMicroTime;
    private int easingCurveType;
    private double multipointCurrentX;
    private double multipointCurrentY;
    private double multipointCurrentZ;
    private double multipointTargetX;
    private double multipointTargetY;
    private double multipointTargetZ;
    private double multipointSmoothSpeed;
    private int lostTargetTicks;
    private double carryYaw;
    private double carryPitch;
    private long nextMultipointUpdate;
    private class_5321<class_1937> lastWorld;
    private AimAnalyzer.AimSettings neuroSettings;
    private boolean hasNeuroSettings;

    public AimAssistModule() {
        super(class_2561.method_30163("Aim Assist"), class_2561.method_30163("\u041f\u043b\u0430\u0432\u043d\u043e \u043f\u043e\u043c\u043e\u0433\u0430\u0435\u0442 \u043d\u0430\u0432\u043e\u0434\u0438\u0442\u0441\u044f \u043d\u0430 \u0438\u0433\u0440\u043e\u043a\u0430"), Category.Combat);
        String[] tmp0 = new String[2];
        tmp0[0] = "Standard";
        tmp0[1] = "Neuro";
        this.rotationMode = new ModeSetting(class_2561.method_30163("Rotation Mode"), null, AimAssistModule::lambda$new$0).set(tmp0).set("Standard").register(this);
        this.distance = new SliderSetting(class_2561.method_30163("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f"), null, this::lambda$new$1).set(1f, 6f, 0.1f).set(4f).register(this);
        this.fov = new SliderSetting(class_2561.method_30163("\u041f\u043e\u043b\u0435 \u0437\u0440\u0435\u043d\u0438\u044f"), null, this::lambda$new$2).set(20f, 360f, 1f).set(90f).register(this);
        this.speed = new SliderSetting(class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c"), null, this::lambda$new$3).set(5f, 50f, 1f).set(18f).register(this);
        this.drawFov = new BooleanSetting(class_2561.method_30163("\u0420\u0438\u0441\u043e\u0432\u0430\u0442\u044c \u043a\u0440\u0443\u0436\u043e\u043a \u0441 Fov"), null, AimAssistModule::lambda$new$4).set(true).register(this);
        this.aimHeight = new SliderSetting(class_2561.method_30163("\u0412\u044b\u0441\u043e\u0442\u0430 \u043d\u0430\u0432\u043e\u0434\u043a\u0438 \u0430\u0438\u043c\u0430"), class_2561.method_30163("0.5 \u041f\u0430\u0445 / 0.9 \u0413\u043e\u043b\u043e\u0432\u0430"), this::lambda$new$5).set(0f, 1f, 0.05f).set(0.85f).register(this);
        this.onlyPlayers = new BooleanSetting(class_2561.method_30163("\u0422\u043e\u043b\u044c\u043a\u043e \u043d\u0430 \u0438\u0433\u0440\u043e\u043a\u043e\u0432"), null, this::lambda$new$6).set(true).register(this);
        this.noAimInInventory = new BooleanSetting(class_2561.method_30163("\u041d\u0435 \u043d\u0430\u0432\u043e\u0434\u0438\u0442\u044c \u0432 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435"), null, this::lambda$new$7).set(true).register(this);
        this.hitInvisible = new BooleanSetting(class_2561.method_30163("\u041d\u0430\u0432\u043e\u0434\u0438\u0442\u044c\u0441\u044f \u043d\u0430 \u0438\u043d\u0432\u0438\u0437\u043e\u043a"), null, this::lambda$new$8).set(false).register(this);
        this.onlyArmored = new BooleanSetting(class_2561.method_30163("\u0422\u043e\u043b\u044c\u043a\u043e \u043d\u0430 \u0438\u0433\u0440\u043e\u043a\u043e\u0432 \u0432 \u0431\u0440\u043e\u043d\u0435"), null, this::lambda$new$9).set(false).register(this);
        this.ignoreNaked = new BooleanSetting(class_2561.method_30163("\u0418\u0433\u043d\u043e\u0440\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0433\u043e\u043b\u044b\u0445"), null, this::lambda$new$10).set(false).register(this);
        this.onlyX = new BooleanSetting(class_2561.method_30163("\u0422\u043e\u043b\u044c\u043a\u043e \u043f\u043e X"), null, this::lambda$new$11).set(false).register(this);
        this.multiPoint = new BooleanSetting(class_2561.method_30163("\u041c\u0443\u043b\u044c\u0442\u0438\u043f\u043e\u0438\u043d\u0442\u044b"), null, this::lambda$new$12).set(true).register(this);
        this.wallCheck = new BooleanSetting(class_2561.method_30163("\u041d\u0430\u0432\u043e\u0434\u0438\u0442\u044c\u0441\u044f \u0447\u0435\u0440\u0435\u0437 \u0441\u0442\u0435\u043d\u044b"), null, this::lambda$new$13).set(true).register(this);
        this.disableOnWorldChange = new BooleanSetting(class_2561.method_30163("Disable On World Change"), null, this::lambda$new$14).set(false).register(this);
        this.microMovements = new BooleanSetting(class_2561.method_30163("\u041c\u0438\u043a\u0440\u043e\u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f"), null, this::lambda$new$15).set(true).register(this);
        this.circleTexture = null;
        this.currentTarget = null;
        this.targetLockTime = 0L;
        this.lastFrameTimeNanos = 0L;
        this.lastYawStep = 0;
        this.lastPitchStep = 0;
        this.noisePhaseYaw = 0;
        this.noisePhasePitch = 0;
        this.noiseSpeedYaw = 0;
        this.noiseSpeedPitch = 0;
        this.easingPower = 0;
        this.speedVariation = 1;
        this.microAccumulator = 0;
        this.verticalNoise = 0;
        this.lastMicroTime = 0L;
        this.easingCurveType = 0;
        this.multipointCurrentX = 0;
        this.multipointCurrentY = 0;
        this.multipointCurrentZ = 0;
        this.multipointTargetX = 0;
        this.multipointTargetY = 0;
        this.multipointTargetZ = 0;
        this.multipointSmoothSpeed = 0;
        this.lostTargetTicks = 0;
        this.carryYaw = 0;
        this.carryPitch = 0;
        this.nextMultipointUpdate = 0L;
        this.lastWorld = null;
        this.neuroSettings = null;
        this.hasNeuroSettings = false;
        this.setKey(82);
        WorldRenderEvents.BEFORE_ENTITIES.register(this::onWorldRender);
        HudRenderCallback.EVENT.register(this::onHudRender);
    }

    public void activate() {
        super.activate();
        this.resetAll();
    }

    public void deactivate() {
        this.resetAll();
        super.deactivate();
    }

    private void resetAll() {
        this.currentTarget = null;
        lockedTarget = null;
        this.targetLockTime = 0L;
        this.lastFrameTimeNanos = 0L;
        this.lastYawStep = 0;
        this.lastPitchStep = 0;
        this.lastWorld = null;
        this.multipointCurrentX = 0;
        this.multipointCurrentY = 0;
        this.multipointCurrentZ = 0;
        this.multipointTargetX = 0;
        this.multipointTargetY = 0;
        this.multipointTargetZ = 0;
        this.nextMultipointUpdate = 0L;
        this.randomizeParameters();
    }

    private void randomizeParameters() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.noisePhaseYaw = random.nextDouble(6.283185307179586);
        this.noisePhasePitch = random.nextDouble(6.283185307179586);
        this.noiseSpeedYaw = random.nextDouble(0.08, 0.25);
        this.noiseSpeedPitch = random.nextDouble(0.06, 0.2);
        this.easingPower = random.nextDouble(1.8, 3.5);
        this.speedVariation = random.nextDouble(0.55, 1.45);
        this.microAccumulator = random.nextDouble(-0.1, 0.2);
        this.easingCurveType = random.nextInt(3);
        this.verticalNoise = random.nextDouble(-0.15, 0.1);
        this.lastMicroTime = System.currentTimeMillis();
        this.multipointSmoothSpeed = random.nextDouble(0.025, 0.065);
        this.randomizeMultipoint(random);
    }

    private void randomizeMultipoint(ThreadLocalRandom random) {
        this.multipointTargetX = random.nextDouble(-0.35, 0.35);
        this.multipointTargetY = random.nextDouble(-0.12, 0.12);
        this.multipointTargetZ = random.nextDouble(-0.35, 0.35);
        this.nextMultipointUpdate = System.currentTimeMillis() + random.nextLong(300L, 900L);
        this.multipointSmoothSpeed = random.nextDouble(0.02, 0.07);
    }

    private void onWorldRender(WorldRenderContext context) {
        if (!super.getEnabled().booleanValue()) {
            return;
        }
        if (onSelfDestruct) {
            super.deactivate();
            return;
        }
        this.performAim();
    }

    private boolean isPlayerUsingItem() {
        if (mc.field_1724 == null) {
            return false;
        }
        return mc.field_1724.method_6115();
    }

    private void performAim() {
        if (mc.field_1724 == null || mc.field_1687 == null) {
            return;
        }
        boolean isNeuroMode = this.rotationMode.getValue().equals("Neuro");
        if (isNeuroMode) {
            if (!this.hasNeuroSettings) {
                if (mc.field_1724 != null) {
                    if (System.currentTimeMillis() % 5000L < 50L) {
                        mc.field_1724.method_7353(class_2561.method_43470("\u00a7c[AimAssist] \u00a7f\u041d\u0435\u0439\u0440\u043e \u0440\u0435\u0436\u0438\u043c \u0430\u043a\u0442\u0438\u0432\u0435\u043d, \u043d\u043e \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u043d\u0435 \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u044b! \u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 /aimrecord"), false);
                    }
                }
                return;
            }
        }
        float currentDistance = isNeuroMode ? 4f : this.distance.getValue();
        float currentFov = isNeuroMode ? 90f : this.fov.getValue();
        float currentSpeed = isNeuroMode && this.neuroSettings != null ? this.neuroSettings.maxYawSpeed : this.speed.getValue().floatValue() / 10f;
        float currentAimHeight = isNeuroMode ? 0.85f : this.aimHeight.getValue();
        boolean currentOnlyPlayers = isNeuroMode ? true : this.onlyPlayers.getEnabled().booleanValue();
        boolean currentNoAimInInventory = isNeuroMode ? true : this.noAimInInventory.getEnabled().booleanValue();
        boolean currentHitInvisible = isNeuroMode ? false : this.hitInvisible.getEnabled().booleanValue();
        boolean currentOnlyArmored = isNeuroMode ? false : this.onlyArmored.getEnabled().booleanValue();
        boolean currentIgnoreNaked = isNeuroMode ? false : this.ignoreNaked.getEnabled().booleanValue();
        boolean currentOnlyX = isNeuroMode ? false : this.onlyX.getEnabled().booleanValue();
        boolean currentMultiPoint = isNeuroMode ? true : this.multiPoint.getEnabled().booleanValue();
        boolean currentWallCheck = isNeuroMode ? true : this.wallCheck.getEnabled().booleanValue();
        boolean currentDisableOnWorldChange = isNeuroMode ? false : this.disableOnWorldChange.getEnabled().booleanValue();
        boolean currentMicroMovements = isNeuroMode ? true : this.microMovements.getEnabled().booleanValue();
        class_5321<class_1937> currentWorld = mc.field_1687.method_27983();
        if (this.lastWorld == null) {
            this.lastWorld = currentWorld;
        } else {
            if (!this.lastWorld.equals(currentWorld)) {
                if (currentDisableOnWorldChange) {
                    this.setEnabled(false);
                    return;
                }
                this.lastWorld = currentWorld;
                this.resetAll();
                return;
            }
        }
        if (currentNoAimInInventory && mc.field_1755 instanceof class_465) {
            return;
        }
        if (this.isPlayerUsingItem()) {
            return;
        }
        long currentTimeNanos = System.nanoTime();
        double deltaTime = this.lastFrameTimeNanos > 0L ? (double)(currentTimeNanos - this.lastFrameTimeNanos) / 16666666.666666668 : 1;
        deltaTime = class_3532.method_15350(deltaTime, 0.05, 3);
        this.lastFrameTimeNanos = currentTimeNanos;
        class_1297 target = null;
        if (lockedTarget == null) { /* goto L686; */ }
        if (lockedTarget.method_31481()) { /* goto L686; */ }
        if (lockedTarget.method_6032() <= 0f) { /* goto L686; */ }
        double distToLocked = mc.field_1724.method_5739(lockedTarget);
        if (distToLocked > (double)currentDistance) { /* goto L686; */ }
        class_243 eyePos = mc.field_1724.method_33571();
        class_243 lookVec = mc.field_1724.method_5828(1f);
        double angleToLocked = this.calculateAngleToEntity(eyePos, lookVec, lockedTarget, currentAimHeight);
        double halfFov = currentFov / 2;
        if (!(angleToLocked > halfFov)) {
            if (currentWallCheck) {
                class_243 targetPos = lockedTarget.method_19538().method_1031(0, (double)(lockedTarget.method_18381(lockedTarget.method_18376()) * currentAimHeight), 0);
                if (!this.isBlockingView(eyePos, targetPos)) {
                    target = lockedTarget;
                }
            } else {
                target = lockedTarget;
            }
        }
        if (target == null) {
            target = this.findTarget(currentDistance, currentFov, currentOnlyPlayers, currentHitInvisible, currentOnlyArmored, currentIgnoreNaked, currentWallCheck, currentAimHeight);
        }
        if (target == null) {
            if (this.lostTargetTicks < 2) {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                float fakeYaw = mc.field_1724.method_36454() + (float)(this.carryYaw * random.nextDouble(0.35, 0.7));
                float fakePitch = mc.field_1724.method_36455() + (float)(this.carryPitch * random.nextDouble(0.2, 0.5));
                mc.field_1724.method_36456(fakeYaw);
                mc.field_1724.method_36457(class_3532.method_15363(fakePitch, -90f, 90f));
                this.carryYaw = this.carryYaw * random.nextDouble(0.45, 0.75);
                this.carryPitch = this.carryPitch * random.nextDouble(0.35, 0.65);
                this.lostTargetTicks = this.lostTargetTicks + 1;
                return;
            }
            this.lostTargetTicks = 0;
            if (this.currentTarget != null) {
                this.resetAll();
            }
            return;
        }
        if (target != this.currentTarget) {
            this.currentTarget = target;
        }
        lockedTarget = target instanceof class_1657 ? (class_1657)target : null;
        this.targetLockTime = System.currentTimeMillis();
        this.randomizeParameters();
        this.lostTargetTicks = 0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long currentTime = System.currentTimeMillis();
        this.verticalNoise = this.verticalNoise + random.nextDouble(-0.06, 0.06);
        if (currentMicroMovements && currentTime - this.lastMicroTime > random.nextLong(180L, 450L)) {
            this.verticalNoise = class_3532.method_15350(this.verticalNoise, -0.2, 0.15);
            this.lastMicroTime = currentTime;
        }
        if (currentMultiPoint) {
            if (currentTime >= this.nextMultipointUpdate) {
                this.randomizeMultipoint(random);
            }
        }
        if (!currentMultiPoint) { /* goto L1120; */ }
        double smoothSpeed = this.multipointSmoothSpeed + (currentMicroMovements ? random.nextDouble(-0.008, 0.008) : 0);
        this.multipointCurrentX = class_3532.method_16436(smoothSpeed, this.multipointCurrentX, this.multipointTargetX);
        this.multipointCurrentY = class_3532.method_16436(smoothSpeed, this.multipointCurrentY, this.multipointTargetY);
        this.multipointCurrentZ = class_3532.method_16436(smoothSpeed, this.multipointCurrentZ, this.multipointTargetZ);
        double[] targetAngles = this.calculateTargetAngles(target, currentMicroMovements, currentAimHeight, currentMultiPoint);
        float currentYaw = mc.field_1724.method_36454();
        float currentPitch = mc.field_1724.method_36455();
        double yawDiff = RotationUtils.wrapDegrees(targetAngles[0] - (double)currentYaw);
        double pitchDiff = targetAngles[1] - (double)currentPitch;
        if (currentOnlyX) {
            double threshold = currentMicroMovements ? 8 + random.nextDouble(4) : 10;
            double absPitchDiff = Math.abs(pitchDiff);
            if (absPitchDiff > threshold) {
                double factor = currentMicroMovements ? 0.06 + random.nextDouble(0.06) : 0.08;
                double pitchToUse = (pitchDiff - Math.signum(pitchDiff) * threshold) * factor;
            } else {
                double pitchToUse = 0;
            }
        } else {
            double pitchToUse = pitchDiff;
        }
        double angleDist = currentOnlyX ? Math.abs(yawDiff) : Math.sqrt(yawDiff * yawDiff + pitchToUse * pitchToUse);
        if (angleDist < 0.05) {
            return;
        }
        double lockTimeProgress = class_3532.method_15350((double)(currentTime - this.targetLockTime) / (currentMicroMovements ? 400 + random.nextDouble(200) : 350), 0, 1);
        double easingMultiplier = this.applyEasing(lockTimeProgress, currentMicroMovements);
        double baseSpeed = currentSpeed * (currentMicroMovements ? this.speedVariation : 1);
        if (currentMicroMovements) {
            if (random.nextDouble() < 0.015) {
                this.speedVariation = random.nextDouble(0.7, 1.3);
            }
        }
        if (currentMicroMovements) {
            if (angleDist < 3) {
                double distanceMultiplier = 0.15 + random.nextDouble(0.25);
            } else {
                if (angleDist < 10) {
                    double distanceMultiplier = 0.7 + random.nextDouble(0.3);
                } else {
                    if (angleDist < 25) {
                        double distanceMultiplier = 0.85 + random.nextDouble(0.3);
                    } else {
                        if (angleDist < 50) {
                            double distanceMultiplier = 1.15 + random.nextDouble(0.35);
                        } else {
                            double distanceMultiplier = 1.4 + random.nextDouble(0.4);
                        }
                    }
                }
            }
        } else {
            if (angleDist < 3) {
                double distanceMultiplier = 0.15;
            } else {
                if (angleDist < 10) {
                    double distanceMultiplier = 0.85;
                } else {
                    if (angleDist < 25) {
                        double distanceMultiplier = 1;
                    } else {
                        if (angleDist < 50) {
                            double distanceMultiplier = 1.3;
                        } else {
                            double distanceMultiplier = 1.4;
                        }
                    }
                }
            }
        }
        if (angleDist > 50) {
            if ((double)mc.field_1724.method_5739(lockedTarget) < 1.2) {
                distanceMultiplier = 0.2 + random.nextDouble(0.2);
            }
        }
        if (angleDist < 10) {
            if ((double)mc.field_1724.method_5739(lockedTarget) < 1) {
                distanceMultiplier = 0.2 + random.nextDouble(0.2);
            }
        }
        double distanceBoost = 1;
        if (angleDist <= 15) { /* goto L1757; */ }
        double boostFactor = class_3532.method_15350((angleDist - 15) / 60, 0, 1);
        distanceBoost = 1 + boostFactor * (currentMicroMovements ? 1.2 + random.nextDouble(0.4) : 1.4);
        double finalSpeed = baseSpeed * easingMultiplier * distanceMultiplier * distanceBoost * deltaTime;
        finalSpeed = finalSpeed * random.nextDouble(0.96, 1.04);
        if (mc.field_1724.field_6235 <= 0) { /* goto L1857; */ }
        double hurtBoost = currentMicroMovements ? 0.12 + random.nextDouble(0.08) : 0.15;
        double hurtMultiplier = 1 + class_3532.method_15350((double)mc.field_1724.field_6235 * hurtBoost, 0, 0.7);
        finalSpeed = finalSpeed * hurtMultiplier;
        if (!(mc.field_1724.method_5715()) && !(mc.field_1724.method_18798().method_37267() <= 0.1)) {
            finalSpeed = finalSpeed * (currentMicroMovements ? 0.8 + random.nextDouble(0.15) : 0.85);
        }
        double randomNoise = currentMicroMovements ? random.nextDouble(-0.003, 0.003) : 0;
        double maxSmoothFactor = angleDist > 30 ? 0.96 : 0.92;
        double dynamicScale = 0.01 + Math.min(angleDist, 40) / 40 * 0.017;
        double smoothFactor = class_3532.method_15350(finalSpeed * dynamicScale + random.nextDouble(-0.018, 0.018), 0.01, angleDist > 25 ? 0.72 : 0.56);
        double yawStep = yawDiff * smoothFactor;
        ThreadLocalRandom randomYawStep = ThreadLocalRandom.current();
        yawStep = yawStep * randomYawStep.nextDouble(0.82, 1.18);
        if (randomYawStep.nextDouble() < 0.12) {
            yawStep = yawStep * randomYawStep.nextDouble(0.45, 0.8);
        }
        if (randomYawStep.nextDouble() < 0.06) {
            yawStep = yawStep * randomYawStep.nextDouble(1.15, 1.45);
        }
        if (currentMicroMovements) {
            double pitchMultiplier = 0.18 + random.nextDouble(0.14);
        } else {
            double pitchMultiplier = 0.25;
        }
        double pitchDeadzone = currentMicroMovements ? 1.8 + random.nextDouble(2.2) : 2.5;
        if (currentMultiPoint) {
            if (Math.abs(this.multipointCurrentY) > 0.05) {
                pitchDeadzone = pitchDeadzone * 0.3;
            }
        }
        if (Math.abs(pitchToUse) < pitchDeadzone) {
            pitchToUse = 0;
            pitchMultiplier = 0;
        }
        if (angleDist >= 4) { /* goto L2244; */ }
        double suppressFactor = currentMicroMovements ? 0.05 + random.nextDouble(0.08) : 0.06;
        pitchMultiplier = pitchMultiplier * suppressFactor;
        double pitchStep = pitchToUse * smoothFactor * pitchMultiplier;
        if (random.nextDouble() < 0.1) {
            pitchStep = 0;
        }
        if (random.nextDouble() < 0.35) {
            pitchStep = pitchStep * random.nextDouble(0.25, 0.45);
        }
        double boost = this.microAccumulator * random.nextDouble(0.4, 1);
        if (currentMicroMovements && angleDist < 2.5 && this.microAccumulator > 0.01) {
            yawStep = yawStep * (1 + boost);
            this.microAccumulator = this.microAccumulator * (0.9 + random.nextDouble(0.07));
        }
        if (currentMicroMovements) {
            double noiseStrength = 0.06 + random.nextDouble(0.1);
            this.noisePhaseYaw = this.noisePhaseYaw + (this.noiseSpeedYaw + random.nextDouble(-0.03, 0.03));
            this.noisePhasePitch = this.noisePhasePitch + (this.noiseSpeedPitch + random.nextDouble(-0.025, 0.025));
            yawStep = yawStep + Math.sin(this.noisePhaseYaw) * noiseStrength * (0.5 + random.nextDouble(0.8));
            pitchStep = pitchStep + Math.cos(this.noisePhasePitch) * noiseStrength * 0.55 * (0.4 + random.nextDouble(0.7));
        }
        double maxYawDelta = currentMicroMovements ? 2 + random.nextDouble(1.5) : 3;
        double maxPitchDelta = currentMicroMovements ? 1.5 + random.nextDouble(1) : 2.5;
        double yawDelta = yawStep - this.lastYawStep;
        double pitchDelta = pitchStep - this.lastPitchStep;
        if (Math.abs(yawDelta) > maxYawDelta) {
            yawStep = this.lastYawStep + Math.signum(yawDelta) * maxYawDelta;
        }
        if (Math.abs(pitchDelta) > maxPitchDelta) {
            pitchStep = this.lastPitchStep + Math.signum(pitchDelta) * maxPitchDelta;
        }
        this.lastYawStep = yawStep;
        this.lastPitchStep = pitchStep;
        this.carryYaw = yawStep;
        this.carryPitch = pitchStep;
        double gcd = RotationUtils.getMouseGCD();
        yawStep = RotationUtils.applyGCD(yawStep, gcd);
        pitchStep = RotationUtils.applyGCD(pitchStep, gcd);
        if (Math.abs(yawStep) < 0.003 && Math.abs(pitchStep) < 0.003) {
            return;
        }
        if (angleDist < 5.5) {
            if (random.nextDouble() < 0.09) {
                yawStep = yawStep * -0.12;
            }
        }
        if (angleDist < 1.4) {
            yawStep = yawStep + random.nextDouble(-0.06, 0.06);
            pitchStep = pitchStep + random.nextDouble(-0.03, 0.03);
        }
        float newYaw = currentYaw + (float)yawStep;
        float newPitch = class_3532.method_15363(currentPitch + (float)pitchStep, -90f, 90f);
        mc.field_1724.method_36456(newYaw);
        if (!Float.isNaN(newYaw) && !Float.isNaN(newPitch) && !Float.isInfinite(newYaw) && !Float.isInfinite(newPitch)) {
            mc.field_1724.method_36457(newPitch);
        }
    }

    private double applyEasing(double progress, boolean microEnabled) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (microEnabled) {
            switch (this.easingCurveType) {
                case 0:
                    double p1 = class_3532.method_15350(progress, 0, 1);
                    double result = 1 - Math.pow(1 - p1, this.easingPower);
                    break;
                case 1:
                    double p2 = class_3532.method_15350(progress, 0, 1);
                    if (p2 < 0.5) {
                        double result = Math.pow(2 * p2, this.easingPower) / 2;
                    } else {
                        double result = 1 - Math.pow(2 * (1 - p2), this.easingPower) / 2;
                    }
                    break;
                default:
                    double p3 = class_3532.method_15350(progress, 0, 1);
                    double result = p3 * p3 * (3 - 2 * p3);
            }
            double randomized = result * (0.85 + random.nextDouble(0.3));
            return class_3532.method_15350(randomized, 0.05, 1.8);
        }
        double p = class_3532.method_15350(progress, 0, 1);
        return 1 - Math.pow(1 - p, 2.5);
    }

    private class_1297 findTarget(float maxDistance, float maxFov, boolean onlyPlayersCheck, boolean hitInvisibleCheck, boolean onlyArmoredCheck, boolean ignoreNakedCheck, boolean wallCheckEnabled, float aimHeightValue) {
        if (mc.field_1687 == null || mc.field_1724 == null) {
            return null;
        }
        double halfFov = maxFov / 2;
        class_1297 bestTarget = null;
        double bestPriority = 179769313486231570000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000;
        class_243 eyePos = mc.field_1724.method_33571();
        class_243 lookVec = mc.field_1724.method_5828(1f);
        for (class_1297 entity : mc.field_1687.method_18112()) {
            while (entity == mc.field_1724) {
            }
            while (!(entity instanceof class_1309)) {
            }
            class_1309 living = entity;
            if (living.method_31481()) continue;
            while (living.method_6032() <= 0f) {
            }
            while (onlyPlayersCheck) {
                if (entity instanceof class_1657) break;
            }
            while (entity instanceof class_1657) {
                class_1657 player = entity;
                if (!FriendManager.isFriend(player.method_5477().getString().toLowerCase())) break;
            }
            while (!hitInvisibleCheck) {
                if (!living.method_5767()) break;
                if (this.hasAnyArmor(living)) break;
            }
            while (onlyArmoredCheck) {
            }
            while (ignoreNakedCheck) {
                if (this.hasAnyArmor(living)) break;
            }
            double dist = mc.field_1724.method_5739(entity);
            if (dist > (double)maxDistance) continue;
            while (dist < 0.5) {
            }
            while (wallCheckEnabled) {
                class_243 targetPos = entity.method_19538().method_1031(0, (double)(entity.method_18381(entity.method_18376()) * aimHeightValue), 0);
                if (!this.isBlockingView(eyePos, targetPos)) break;
            }
            double angle = this.calculateAngleToEntity(eyePos, lookVec, entity, aimHeightValue);
            while (angle > halfFov) {
            }
            double priority = dist;
            if (priority < bestPriority) {
                bestPriority = priority;
                bestTarget = entity;
            }
        }
        return bestTarget;
    }

    private double calculateAngleToEntity(class_243 eyePos, class_243 lookVec, class_1297 entity, float aimHeightValue) {
        class_243 targetPos = entity.method_19538().method_1031(0, (double)(entity.method_18381(entity.method_18376()) * aimHeightValue), 0);
        class_243 toTarget = targetPos.method_1020(eyePos);
        double length = toTarget.method_1033();
        if (length < 0.001) {
            return 0;
        }
        toTarget = toTarget.method_1021(1 / length);
        double dot = lookVec.field_1352 * toTarget.field_1352 + lookVec.field_1351 * toTarget.field_1351 + lookVec.field_1350 * toTarget.field_1350;
        dot = class_3532.method_15350(dot, -1, 1);
        return Math.toDegrees(Math.acos(dot));
    }

    private double[] calculateTargetAngles(class_1297 entity, boolean microEnabled, float aimHeightValue, boolean multiPointEnabled) {
        class_243 eyePos = mc.field_1724.method_33571();
        double finalAimHeight = microEnabled ? (double)aimHeightValue + this.verticalNoise : (double)aimHeightValue;
        double offsetX = 0;
        double offsetY = 0;
        double offsetZ = 0;
        if (multiPointEnabled) {
            double halfWidth = entity.method_17681() * 0.5;
            offsetX = this.multipointCurrentX * halfWidth;
            offsetZ = this.multipointCurrentZ * halfWidth;
            offsetY = this.multipointCurrentY;
        }
        double clampedHeight = class_3532.method_15350(finalAimHeight + offsetY, 0.05, 0.95);
        class_243 targetPos = entity.method_19538().method_1031(offsetX, (double)entity.method_18381(entity.method_18376()) * clampedHeight, offsetZ);
        double deltaX = targetPos.field_1352 - eyePos.field_1352;
        double deltaY = targetPos.field_1351 - eyePos.field_1351;
        double deltaZ = targetPos.field_1350 - eyePos.field_1350;
        double horizontalDist = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        if (horizontalDist < 0.001) {
            double yaw = mc.field_1724.method_36454();
        } else {
            double yaw = Math.toDegrees(Math.atan2(-deltaX, deltaZ));
        }
        double totalDist = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        if (totalDist < 0.001) {
            double pitch = mc.field_1724.method_36455();
        } else {
            double pitch = -Math.toDegrees(Math.asin(class_3532.method_15350(deltaY / totalDist, -1, 1)));
        }
        double[] tmp0 = new double[2];
        tmp0[0] = yaw;
        tmp0[1] = pitch;
        return tmp0;
    }

    private boolean hasAnyArmor(class_1309 entity) {
        for (class_1799 stack : entity.method_5661()) {
            if (!stack.method_7960()) {
                if (!stack.method_7909() instanceof class_1738) continue;
                return true;
            }
        }
        return false;
    }

    private boolean isBlockingView(class_243 from, class_243 to) {
        class_3959 context = new class_3959(from, to, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, mc.field_1724);
        return mc.field_1687.method_17742(context).method_17783() != class_239.class_240.field_1333;
    }

    private void onHudRender(class_332 context, class_9779 tickCounter) {
        if (!super.getEnabled().booleanValue() || !this.drawFov.getEnabled().booleanValue() || mc.field_1724 == null || this.fov.getValue().floatValue() >= 200f) {
            return;
        }
        float currentFov = this.fov.getValue().floatValue();
        this.loadCircleTexture();
        if (this.circleTexture == null) {
            return;
        }
        float screenWidth = context.method_51421();
        float screenHeight = context.method_51443();
        float mcFov = ((Integer)mc.field_1690.method_41808().method_41753()).floatValue();
        if (mcFov <= 0f) {
            return;
        }
        float radius = (Math.tan(Math.toRadians((double)(currentFov / 2f))) / Math.tan(Math.toRadians((double)(mcFov / 2f)))) * (screenHeight / 2f);
        radius = radius * 0.85f;
        radius = Math.max(10f, Math.min(radius, screenHeight / 2f));
        float size = radius * 2f;
        float x = screenWidth / 2f - radius;
        float y = screenHeight / 2f - radius;
        Matrix4f matrix = context.method_51448().method_23760().method_23761();
        int color = this.currentTarget != null ? -939458680 : -922746881;
        BuiltTexture circle = Builder.TEXTURE_BUILDER.size(new SizeState(size, size)).texture(0f, 0f, 1f, 1f, this.circleTexture).color(new QuadColorState(new Color(color, true))).build();
        circle.render(matrix, x, y);
    }

    private void loadCircleTexture() {
        if (this.circleTexture != null) {
            return;
        }
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/pasxalka/images/circle.png");
            if (inputStream == null) {
                return;
            }
        }
        catch (IOException inputStream) {
            return;
        }
        try {
            class_1011 image = class_1011.method_4309(inputStream);
            inputStream.close();
            this.circleTexture = new class_1043(image);
        }
        catch (IOException inputStream) {
            return;
        }
    }

    public void loadNeuroSettings(AimAnalyzer.AnalyzedSettings analyzedSettings) {
        if (analyzedSettings == null || analyzedSettings.aimSettings == null) {
            return;
        }
        this.neuroSettings = analyzedSettings.aimSettings;
        this.hasNeuroSettings = true;
        this.rotationMode.set("Neuro");
        this.resetAll();
        if (mc.field_1724 != null) {
            mc.field_1724.method_7353(class_2561.method_43470("\u00a7a[AimAssist] \u00a7f\u041d\u0435\u0439\u0440\u043e-\u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u044b! \u0420\u0435\u0436\u0438\u043c: Neuro"), false);
        }
    }

    public void clearNeuroSettings() {
        this.neuroSettings = null;
        this.hasNeuroSettings = false;
        this.rotationMode.set("Standard");
        if (mc.field_1724 != null) {
            mc.field_1724.method_7353(class_2561.method_43470("\u00a7c[AimAssist] \u00a7f\u041d\u0435\u0439\u0440\u043e-\u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u0441\u0431\u0440\u043e\u0448\u0435\u043d\u044b"), false);
        }
    }
}
