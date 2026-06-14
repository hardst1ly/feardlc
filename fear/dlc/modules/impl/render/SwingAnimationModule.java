/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_2561;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;

public class SwingAnimationModule
extends ModuleLayer {
    public final ModeSetting style;
    public final SliderSetting strength;
    public final SliderSetting durationMul;
    public final BooleanSetting onlyMainHand;

    public SwingAnimationModule() {
        super(class_2561.method_30163("Swing Animation"), class_2561.method_30163("\u041a\u0430\u0441\u0442\u043e\u043c\u043d\u044b\u0435 \u0430\u043d\u0438\u043c\u0430\u0446\u0438\u0438 \u0437\u0430\u043c\u0430\u0445\u0430"), Category.Render);
        String[] tmp0 = new String[9];
        tmp0[0] = "Default";
        tmp0[1] = "Swipe";
        tmp0[2] = "Chop";
        tmp0[3] = "Down";
        tmp0[4] = "Smooth";
        tmp0[5] = "Smooth 2";
        tmp0[6] = "Power";
        tmp0[7] = "Feast";
        tmp0[8] = "Twist";
        this.style = new ModeSetting(class_2561.method_30163("\u0421\u0442\u0438\u043b\u044c"), class_2561.method_30163("\u0422\u0438\u043f \u0430\u043d\u0438\u043c\u0430\u0446\u0438\u0438 \u0437\u0430\u043c\u0430\u0445\u0430"), SwingAnimationModule::lambda$new$0).set(tmp0).set("Smooth").register(this);
        this.strength = new SliderSetting(class_2561.method_30163("\u0421\u0438\u043b\u0430"), class_2561.method_30163("\u0421\u0438\u043b\u0430 \u0430\u043d\u0438\u043c\u0430\u0446\u0438\u0438"), SwingAnimationModule::lambda$new$1).set(0.5f, 3f, 0.05f).set(1f).register(this);
        this.durationMul = new SliderSetting(class_2561.method_30163("\u0414\u043b\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0441\u0442\u044c"), class_2561.method_30163("\u041c\u043d\u043e\u0436\u0438\u0442\u0435\u043b\u044c \u0434\u043b\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0441\u0442\u0438 \u0437\u0430\u043c\u0430\u0445\u0430"), SwingAnimationModule::lambda$new$2).set(0.5f, 4f, 0.05f).set(1f).register(this);
        this.onlyMainHand = new BooleanSetting(class_2561.method_30163("\u0422\u043e\u043b\u044c\u043a\u043e \u043e\u0441\u043d. \u0440\u0443\u043a\u0430"), class_2561.method_30163("\u041f\u0440\u0438\u043c\u0435\u043d\u044f\u0442\u044c \u0442\u043e\u043b\u044c\u043a\u043e \u043a \u043e\u0441\u043d\u043e\u0432\u043d\u043e\u0439 \u0440\u0443\u043a\u0435"), SwingAnimationModule::lambda$new$3).set(true).register(this);
    }

    public boolean applySwing(class_4587 matrices, class_1268 hand, float swingProgress) {
        if (!this.getEnabled().booleanValue()) {
            return false;
        }
        if (this.onlyMainHand.getEnabled().booleanValue() && hand != class_1268.field_5808) {
            return false;
        }
        if (mc.field_1724 == null) {
            return false;
        }
        int i = mc.field_1724.method_6068() == class_1306.field_6183 ? 1 : -1;
        if (hand == class_1268.field_5810) {
            i = -i;
        }
        float p = class_3532.method_15363(swingProgress, 0f, 1f);
        float sin1 = class_3532.method_15374(p * p * 3.1415927f);
        float sin2 = class_3532.method_15374(class_3532.method_15355(p) * 3.1415927f);
        float sinSmooth = (Math.sin((double)p * 3.141592653589793) * 0.5);
        float s = this.strength.getValue().floatValue();
        String var10 = this.style.getValue();
        int var11 = -1;
        switch (var10.hashCode()) {
            case -1085510111:
                if (var10.equals("Default")) {
                    var11 = 0;
                }
            case 2099494:
                if (var10.equals("Chop")) {
                    var11 = 1;
                }
            case 81225479:
                if (var10.equals("Twist")) {
                    var11 = 2;
                }
            case 80301850:
                if (var10.equals("Swipe")) {
                    var11 = 3;
                }
            case 2136258:
                if (var10.equals("Down")) {
                    var11 = 4;
                }
            case -1814666802:
                if (var10.equals("Smooth")) {
                    var11 = 5;
                }
            case -138073504:
                if (var10.equals("Smooth 2")) {
                    var11 = 6;
                }
            case 77306085:
                if (!var10.equals("Power")) return true;
                var11 = 7;
            case 67752259:
                if (var10.equals("Feast")) {
                    var11 = 8;
                }
            default:
                switch (var11) {
                    case 0:
                        matrices.method_46416((float)i * 0.56f, -0.52f - sin2 * 0.5f * s, -0.72f);
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)(45 * i)));
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)(-45 * i)));
                        break;
                    case 1:
                        matrices.method_46416(0.56f * (float)i, -0.44f, -0.72f);
                        matrices.method_46416(0f, -0.19800001f, 0f);
                        matrices.method_22907(class_7833.field_40716.rotationDegrees(45f * (float)i));
                        matrices.method_22907(class_7833.field_40718.rotationDegrees(sin2 * -20f * (float)i * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(sin2 * -80f * s));
                        matrices.method_46416(0.4f, 0.2f, 0.2f);
                        matrices.method_46416(-0.5f, 0.08f, 0f);
                        matrices.method_22907(class_7833.field_40716.rotationDegrees(20f));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(-80f));
                        matrices.method_22907(class_7833.field_40716.rotationDegrees(20f));
                        break;
                    case 2:
                        matrices.method_46416((float)i * 0.56f, -0.36f, -0.72f);
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)(80 * i)));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(sin2 * -90f * s));
                        matrices.method_22907(class_7833.field_40718.rotationDegrees((sin1 - sin2) * 60f * (float)i * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(-30f));
                        matrices.method_46416(0f, -0.1f, 0.05f);
                        break;
                    case 3:
                        matrices.method_46416(0.56f * (float)i, -0.32f, -0.72f);
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)(70 * i)));
                        matrices.method_22907(class_7833.field_40718.rotationDegrees((float)(-20 * i)));
                        matrices.method_22907(class_7833.field_40716.rotationDegrees(sin2 * sin1 * -5f * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(sin2 * sin1 * -120f * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(-70f));
                        break;
                    case 4:
                        matrices.method_46416((float)i * 0.56f, -0.32f, -0.72f);
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)(76 * i)));
                        matrices.method_22907(class_7833.field_40716.rotationDegrees(sin2 * -5f * s));
                        matrices.method_22907(class_7833.field_40713.rotationDegrees(sin2 * -100f * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(sin2 * -155f * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(-100f));
                        break;
                    case 5:
                        matrices.method_46416((float)i * 0.56f, -0.42f, -0.72f);
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)i * (45f + sin1 * -20f * s)));
                        matrices.method_22907(class_7833.field_40718.rotationDegrees((float)i * sin2 * -20f * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(sin2 * -80f * s));
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)i * -45f));
                        matrices.method_22904(0, -0.1, 0);
                        break;
                    case 6:
                        matrices.method_46416((float)i * 0.56f, -0.42f, -0.72f);
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(sin2 * -80f * s));
                        matrices.method_22904(0, -0.1, 0);
                        break;
                    case 7:
                        matrices.method_46416((float)i * 0.56f, -0.32f, -0.72f);
                        matrices.method_46416(-sinSmooth * sinSmooth * sin1 * (float)i * s, 0f, 0f);
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)(61 * i)));
                        matrices.method_22907(class_7833.field_40718.rotationDegrees(sin2 * s));
                        matrices.method_22907(class_7833.field_40716.rotationDegrees(sin2 * sin1 * -5f * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(sin2 * sin1 * -30f * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(-60f));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(sinSmooth * -60f * s));
                        break;
                    case 8:
                        matrices.method_46416((float)i * 0.56f, -0.32f, -0.72f);
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)(30 * i)));
                        matrices.method_22907(class_7833.field_40716.rotationDegrees(sin2 * 75f * (float)i * s));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(sin2 * -45f * s));
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)(30 * i)));
                        matrices.method_22907(class_7833.field_40714.rotationDegrees(-80f));
                        matrices.method_22907(class_7833.field_40716.rotationDegrees((float)(35 * i)));
                        break;
                    default:
                        return false;
                }
                return true;
        }
    }

    public float getDurationMultiplier() {
        return this.durationMul.getValue().floatValue();
    }
}
