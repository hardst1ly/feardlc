/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import java.util.Objects;
import net.minecraft.class_1268;
import net.minecraft.class_2561;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;

public class ViewModelModule
extends ModuleLayer {
    public final SliderSetting posX;
    public final SliderSetting posY;
    public final SliderSetting posZ;
    public final SliderSetting rotX;
    public final SliderSetting rotY;
    public final SliderSetting rotZ;
    public final SliderSetting scale;
    public final BooleanSetting separateOffhand;
    public final SliderSetting offhandPosX;
    public final SliderSetting offhandPosY;
    public final SliderSetting offhandPosZ;
    public final BooleanSetting smooth;
    private float curPosX;
    private float curPosY;
    private float curPosZ;
    private float curRotX;
    private float curRotY;
    private float curRotZ;
    private float curScale;

    public ViewModelModule() {
        super(class_2561.method_30163("View Model"), class_2561.method_30163("\u041a\u0430\u0441\u0442\u043e\u043c\u0438\u0437\u0430\u0446\u0438\u044f \u043c\u043e\u0434\u0435\u043b\u0438 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u0430 \u0432 \u0440\u0443\u043a\u0435"), Category.Render);
        this.posX = new SliderSetting(class_2561.method_30163("\u041f\u043e\u0437\u0438\u0446\u0438\u044f X"), null, ViewModelModule::lambda$new$0).set(-2f, 2f, 0.01f).set(0f).register(this);
        this.posY = new SliderSetting(class_2561.method_30163("\u041f\u043e\u0437\u0438\u0446\u0438\u044f Y"), null, ViewModelModule::lambda$new$1).set(-2f, 2f, 0.01f).set(0f).register(this);
        this.posZ = new SliderSetting(class_2561.method_30163("\u041f\u043e\u0437\u0438\u0446\u0438\u044f Z"), null, ViewModelModule::lambda$new$2).set(-2f, 2f, 0.01f).set(0f).register(this);
        this.rotX = new SliderSetting(class_2561.method_30163("\u041f\u043e\u0432\u043e\u0440\u043e\u0442 X"), null, ViewModelModule::lambda$new$3).set(-180f, 180f, 1f).set(0f).register(this);
        this.rotY = new SliderSetting(class_2561.method_30163("\u041f\u043e\u0432\u043e\u0440\u043e\u0442 Y"), null, ViewModelModule::lambda$new$4).set(-180f, 180f, 1f).set(0f).register(this);
        this.rotZ = new SliderSetting(class_2561.method_30163("\u041f\u043e\u0432\u043e\u0440\u043e\u0442 Z"), null, ViewModelModule::lambda$new$5).set(-180f, 180f, 1f).set(0f).register(this);
        this.scale = new SliderSetting(class_2561.method_30163("\u041c\u0430\u0441\u0448\u0442\u0430\u0431"), class_2561.method_30163("\u041e\u0431\u0449\u0438\u0439 \u043c\u0430\u0441\u0448\u0442\u0430\u0431 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u0430"), ViewModelModule::lambda$new$6).set(0.1f, 3f, 0.01f).set(1f).register(this);
        this.separateOffhand = new BooleanSetting(class_2561.method_30163("\u0421\u0432\u043e\u044f \u043b\u0435\u0432\u0430\u044f \u0440\u0443\u043a\u0430"), class_2561.method_30163("\u041f\u0440\u0438\u043c\u0435\u043d\u044f\u0442\u044c \u043e\u0442\u0434\u0435\u043b\u044c\u043d\u044b\u0435 \u0441\u043c\u0435\u0449\u0435\u043d\u0438\u044f \u0434\u043b\u044f \u043b\u0435\u0432\u043e\u0439 \u0440\u0443\u043a\u0438"), ViewModelModule::lambda$new$7).set(false).register(this);
        Objects.requireNonNull(this.separateOffhand);
        this.offhandPosX = new SliderSetting(class_2561.method_30163("\u041b\u0435\u0432. \u043f\u043e\u0437\u0438\u0446\u0438\u044f X"), null, this.separateOffhand::getEnabled).set(-2f, 2f, 0.01f).set(0f).register(this);
        Objects.requireNonNull(this.separateOffhand);
        this.offhandPosY = new SliderSetting(class_2561.method_30163("\u041b\u0435\u0432. \u043f\u043e\u0437\u0438\u0446\u0438\u044f Y"), null, this.separateOffhand::getEnabled).set(-2f, 2f, 0.01f).set(0f).register(this);
        Objects.requireNonNull(this.separateOffhand);
        this.offhandPosZ = new SliderSetting(class_2561.method_30163("\u041b\u0435\u0432. \u043f\u043e\u0437\u0438\u0446\u0438\u044f Z"), null, this.separateOffhand::getEnabled).set(-2f, 2f, 0.01f).set(0f).register(this);
        this.smooth = new BooleanSetting(class_2561.method_30163("\u041f\u043b\u0430\u0432\u043d\u043e\u0441\u0442\u044c"), class_2561.method_30163("\u041f\u043b\u0430\u0432\u043d\u043e \u0438\u043d\u0442\u0435\u0440\u043f\u043e\u043b\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u044f \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0439"), ViewModelModule::lambda$new$8).set(true).register(this);
        this.curScale = 1f;
    }

    private void updateAnimation() {
        float speed = this.smooth.getEnabled().booleanValue() ? 0.18f : 1f;
        this.curPosX = class_3532.method_16439(speed, this.curPosX, this.posX.getValue().floatValue());
        this.curPosY = class_3532.method_16439(speed, this.curPosY, this.posY.getValue().floatValue());
        this.curPosZ = class_3532.method_16439(speed, this.curPosZ, this.posZ.getValue().floatValue());
        this.curRotX = class_3532.method_16439(speed, this.curRotX, this.rotX.getValue().floatValue());
        this.curRotY = class_3532.method_16439(speed, this.curRotY, this.rotY.getValue().floatValue());
        this.curRotZ = class_3532.method_16439(speed, this.curRotZ, this.rotZ.getValue().floatValue());
        this.curScale = class_3532.method_16439(speed, this.curScale, this.scale.getValue().floatValue());
    }

    public void applyTransformations(class_4587 matrices, class_1268 hand) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        this.updateAnimation();
        matrices.method_22903();
        float px = this.curPosX;
        float py = this.curPosY;
        float pz = this.curPosZ;
        px = this.offhandPosX.getValue().floatValue();
        if (this.separateOffhand.getEnabled().booleanValue() && hand == class_1268.field_5810) {
            py = this.offhandPosY.getValue().floatValue();
            pz = this.offhandPosZ.getValue().floatValue();
        }
        matrices.method_46416(px, py, pz);
        if (this.curRotX != 0f) {
            matrices.method_22907(class_7833.field_40714.rotationDegrees(this.curRotX));
        }
        if (this.curRotY != 0f) {
            matrices.method_22907(class_7833.field_40716.rotationDegrees(this.curRotY));
        }
        if (this.curRotZ != 0f) {
            matrices.method_22907(class_7833.field_40718.rotationDegrees(this.curRotZ));
        }
        if (this.curScale != 1f) {
            matrices.method_22905(this.curScale, this.curScale, this.curScale);
        }
    }

    public void undoTransformations(class_4587 matrices) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        matrices.method_22909();
    }

    public void resetAnimation() {
        this.curPosZ = 0f;
        this.curPosY = 0f;
        this.curPosX = 0f;
        this.curRotZ = 0f;
        this.curRotY = 0f;
        this.curRotX = 0f;
        this.curScale = 1f;
    }
}
