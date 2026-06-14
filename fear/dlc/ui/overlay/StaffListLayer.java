/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import fear.dlc.Api;
import fear.dlc.FearDCP;
import fear.dlc.api.draggable.DraggableLayer;
import fear.dlc.modules.impl.combat.AimAssistModule;
import fear.dlc.modules.impl.render.HudModule;
import fear.dlc.ui.overlay.OverlayRenderer;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import fear.dlc.utility.render.utility.Scissors;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.class_332;
import net.minecraft.class_742;
import net.minecraft.class_9779;

public class StaffListLayer
extends DraggableLayer {
    static List<String> staffsPrefixes;
    static Supplier<List<class_742>> list;
    static Supplier<HudModule> module;

    public StaffListLayer() {
        super(10f, 95f, 60f, 15f, StaffListLayer::lambda$new$4);
    }

    public void render(class_332 context, double mouseX, double mouseY, class_9779 tickCounter) {
        OverlayRenderer.rect(context, this.getX().floatValue(), this.getY().floatValue(), this.getWidth().floatValue(), this.getHeight().floatValue());
        ((BuiltText)Api.text().font(Api.inter()).text("Staffs").color(-3554305).size(7f).build()).render(context.method_51448().method_23760().method_23761(), this.getX().floatValue() + 2.5f, this.getY().floatValue() - 1f + (15f - Api.inter().getHeight("Keybinds", 7f)) / 2f);
        AtomicReference<Float> height = new AtomicReference(0f);
        ((List)list.get()).forEach(StaffListLayer::lambda$render$5 /* captured: height */);
        this.setHeight(15f + ((Float)height.get()).floatValue());
        AtomicReference<Float> offset = new AtomicReference(0f);
        if (FearDCP.getInstance().getModuleRepository().find(AimAssistModule.class).getEnabled().booleanValue()) {
            mc.field_1687.method_18456().forEach(StaffListLayer::lambda$render$6);
        }
        Scissors.push(this.getX().floatValue(), this.getY().floatValue(), this.getWidth().floatValue(), this.getHeight().floatValue());
        ((List)list.get()).forEach(this::lambda$render$7 /* captured: context, offset */);
        Scissors.pop();
    }

    static {
        String[] tmp0 = new String[8];
        tmp0[0] = "eternity";
        tmp0[1] = "dhelper";
        tmp0[2] = "d helper";
        tmp0[3] = "moder";
        tmp0[4] = "moderator";
        tmp0[5] = "admin";
        tmp0[6] = "owner";
        tmp0[7] = "administrator";
        staffsPrefixes = Lists.newArrayList(tmp0);
        list = StaffListLayer::lambda$static$2;
        module = Suppliers.memoize(StaffListLayer::lambda$static$3);
    }
}
