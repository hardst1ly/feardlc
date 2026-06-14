/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ColorSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.minecraft.class_2561;

public class Ambience
extends ModuleLayer {
    public BooleanSetting changeTime;
    public SliderSetting timeValue;
    public BooleanSetting changeWeather;
    public ModeSetting weatherValue;
    public BooleanSetting smoothTransition;
    public BooleanSetting fogChanger;
    public ColorSetting fogColor;
    public SliderSetting fogDistanceValue;
    public static float fogDistance = 100f;
    public static float currentFogDistance = 100f;
    private long originalTime;
    private long originalTimeOfDay;
    private boolean wasTimeChangerEnabled;
    private float originalRainLevel;
    private float originalThunderLevel;
    private boolean wasWeatherChangerEnabled;
    private float targetRainLevel;
    private float targetThunderLevel;
    private float currentRainLevel;
    private float currentThunderLevel;

    public Ambience() {
        super(class_2561.method_30163("Ambience"), class_2561.method_30163("\u041c\u0435\u043d\u044f\u0435\u0442 \u043c\u0438\u0440."), Category.Render);
        this.changeTime = new BooleanSetting(class_2561.method_30163("Time changer"), class_2561.method_30163("\u041c\u0435\u043d\u044f\u0442\u044c \u043b\u0438 \u0432\u0440\u0435\u043c\u044f"), Ambience::lambda$new$0).set(true).register(this);
        this.timeValue = new SliderSetting(class_2561.method_30163("Time"), null, Ambience::lambda$new$1).set(-24f, 24f, 1f).set(24f).register(this);
        this.changeWeather = new BooleanSetting(class_2561.method_30163("Weather changer"), class_2561.method_30163("\u041c\u0435\u043d\u044f\u0442\u044c \u043b\u0438 \u043f\u043e\u0433\u043e\u0434\u0443"), Ambience::lambda$new$2).set(false).register(this);
        String[] tmp0 = new String[3];
        tmp0[0] = "Clear";
        tmp0[1] = "Rain";
        tmp0[2] = "Thunder";
        this.weatherValue = new ModeSetting(class_2561.method_30163("\u041f\u043e\u0433\u043e\u0434\u0430"), null, Ambience::lambda$new$3).set(tmp0).register(this);
        this.smoothTransition = new BooleanSetting(class_2561.method_30163("\u041f\u043b\u0430\u0432\u043d\u0430\u044f \u0441\u043c\u0435\u043d\u0430 \u043f\u043e\u0433\u043e\u0434\u044b"), null, Ambience::lambda$new$4).set(true).register(this);
        this.fogChanger = new BooleanSetting(class_2561.method_30163("Fog Changer"), class_2561.method_30163("\u041c\u0435\u043d\u044f\u0442\u044c \u043b\u0438 \u0442\u0443\u043c\u0430\u043d"), Ambience::lambda$new$5).set(true).register(this);
        this.fogColor = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442 \u0442\u0443\u043c\u0430\u043d\u0430"), null, this::lambda$new$6).set(-5192482).register(this);
        this.fogDistanceValue = new SliderSetting(class_2561.method_30163("\u0414\u0430\u043b\u044c\u043d\u043e\u0441\u0442\u044c \u0442\u0443\u043c\u0430\u043d\u0430"), null, this::lambda$new$7).set(10f, 300f, 1f).set(100f).register(this);
        this.wasTimeChangerEnabled = false;
        this.wasWeatherChangerEnabled = false;
        this.targetRainLevel = 0f;
        this.targetThunderLevel = 0f;
        this.currentRainLevel = 0f;
        this.currentThunderLevel = 0f;
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
        if (mc.field_1687 != null) {
            this.wasTimeChangerEnabled = this.changeTime.getEnabled().booleanValue();
            if (this.wasTimeChangerEnabled) {
                this.originalTime = mc.field_1687.method_8510();
                this.originalTimeOfDay = mc.field_1687.method_8532();
            }
            this.wasWeatherChangerEnabled = this.changeWeather.getEnabled().booleanValue();
            if (this.wasWeatherChangerEnabled) {
                this.saveOriginalWeather();
                this.setTargetWeather();
            }
        }
        currentFogDistance = this.fogDistanceValue.getValue().floatValue();
        fogDistance = currentFogDistance;
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        if (mc.field_1687 != null) {
            if (this.wasTimeChangerEnabled) {
                mc.field_1687.method_29089(this.originalTime, this.originalTimeOfDay, true);
            }
            this.wasTimeChangerEnabled = false;
            if (this.wasWeatherChangerEnabled) {
                this.restoreOriginalWeather();
            }
            this.wasWeatherChangerEnabled = false;
        }
        fogDistance = 100f;
        currentFogDistance = 100f;
        super.deactivate();
    }

    @Subscribe
    public void onTick(TickEvent e) {
        if (!this.getEnabled().booleanValue() || mc.field_1687 == null) {
            return;
        }
        boolean timeCurrentlyEnabled = this.changeTime.getEnabled().booleanValue();
        if (timeCurrentlyEnabled) {
            if (!this.wasTimeChangerEnabled) {
                this.originalTime = mc.field_1687.method_8510();
                this.originalTimeOfDay = mc.field_1687.method_8532();
                this.wasTimeChangerEnabled = true;
            }
            this.setCustomTime();
        } else {
            if (this.wasTimeChangerEnabled) {
                mc.field_1687.method_29089(this.originalTime, this.originalTimeOfDay, true);
                this.wasTimeChangerEnabled = false;
            }
        }
        if (this.fogChanger.getEnabled().booleanValue()) {
            fogDistance = this.fogDistanceValue.getValue().floatValue();
        }
        if (this.smoothTransition.getEnabled().booleanValue()) {
            float speed = 2f;
            float diff = fogDistance - currentFogDistance;
            if (Math.abs(diff) > 0.1f) {
                currentFogDistance = currentFogDistance + (diff > 0f ? speed : -speed);
                if (diff > 0f) {
                    currentFogDistance = Math.min(currentFogDistance, fogDistance);
                } else {
                    currentFogDistance = Math.max(currentFogDistance, fogDistance);
                }
            } else {
                currentFogDistance = fogDistance;
            }
        } else {
            currentFogDistance = fogDistance;
        }
        boolean weatherCurrentlyEnabled = this.changeWeather.getEnabled().booleanValue();
        if (weatherCurrentlyEnabled) {
            if (!this.wasWeatherChangerEnabled) {
                this.saveOriginalWeather();
                this.wasWeatherChangerEnabled = true;
            }
            this.setCustomWeather();
        } else {
            if (this.wasWeatherChangerEnabled) {
                this.restoreOriginalWeather();
                this.wasWeatherChangerEnabled = false;
            }
        }
    }

    private void saveOriginalWeather() {
        if (mc.field_1687 != null) {
            this.originalRainLevel = mc.field_1687.method_8430(1f);
            this.originalThunderLevel = mc.field_1687.method_8478(1f);
        }
    }

    private void restoreOriginalWeather() {
        if (mc.field_1687 != null) {
            mc.field_1687.method_8519(this.originalRainLevel);
            mc.field_1687.method_8496(this.originalThunderLevel);
            this.targetRainLevel = this.originalRainLevel;
            this.targetThunderLevel = this.originalThunderLevel;
            this.currentRainLevel = this.originalRainLevel;
            this.currentThunderLevel = this.originalThunderLevel;
        }
    }

    private void setTargetWeather() {
        String weather = this.weatherValue.getValue();
        var var2 = weather;
        int var3 = -1;
        switch (var2.hashCode()) {
            case 65193517:
                if (var2.equals("Clear")) {
                    var3 = 0;
                }
            case 2539444:
                if (var2.equals("Rain")) {
                    var3 = 1;
                }
            case 329757892:
                if (var2.equals("Thunder")) {
                    var3 = 2;
                }
            default:
                switch (var3) {
                    case 0:
                        this.targetRainLevel = 0f;
                        this.targetThunderLevel = 0f;
                    case 1:
                        this.targetRainLevel = 1f;
                        this.targetThunderLevel = 0f;
                    case 2:
                        this.targetRainLevel = 1f;
                        this.targetThunderLevel = 1f;
                    default:
                        if (!this.smoothTransition.getEnabled().booleanValue()) {
                            this.currentRainLevel = this.targetRainLevel;
                            this.currentThunderLevel = this.targetThunderLevel;
                        }
                        return;
                }
        }
    }

    private void setCustomWeather() {
        String weather = this.weatherValue.getValue();
        if (!weather.equals(this.getCurrentWeatherTarget())) {
            this.setTargetWeather();
        }
        if (this.smoothTransition.getEnabled().booleanValue()) {
            float speed = 0.05f;
            if (Math.abs(this.currentRainLevel - this.targetRainLevel) > 0.01f) {
                this.currentRainLevel = this.currentRainLevel + (this.targetRainLevel > this.currentRainLevel ? speed : -speed);
                this.currentRainLevel = Math.max(0f, Math.min(1f, this.currentRainLevel));
            } else {
                this.currentRainLevel = this.targetRainLevel;
            }
            if (Math.abs(this.currentThunderLevel - this.targetThunderLevel) > 0.01f) {
                this.currentThunderLevel = this.currentThunderLevel + (this.targetThunderLevel > this.currentThunderLevel ? speed : -speed);
                this.currentThunderLevel = Math.max(0f, Math.min(1f, this.currentThunderLevel));
            } else {
                this.currentThunderLevel = this.targetThunderLevel;
            }
            mc.field_1687.method_8519(this.currentRainLevel);
            mc.field_1687.method_8496(this.currentThunderLevel);
        } else {
            mc.field_1687.method_8519(this.targetRainLevel);
            mc.field_1687.method_8496(this.targetThunderLevel);
        }
    }

    private String getCurrentWeatherTarget() {
        if (this.targetRainLevel == 0f && this.targetThunderLevel == 0f) {
            return "Clear";
        }
        if (this.targetRainLevel == 1f && this.targetThunderLevel == 0f) {
            return "Rain";
        }
        if (this.targetRainLevel == 1f && this.targetThunderLevel == 1f) {
            return "Thunder";
        }
        return "Clear";
    }

    private void setCustomTime() {
        long hours = this.timeValue.getValue().intValue();
        long timeInTicks = hours * 1000L % 24000L;
        if (timeInTicks < 0L) {
            timeInTicks = timeInTicks + 24000L;
        }
        mc.field_1687.method_29089(timeInTicks, timeInTicks, false);
    }
}
