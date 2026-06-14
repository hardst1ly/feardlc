/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.movement;

import com.google.common.eventbus.Subscribe;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.minecraft.class_2199;
import net.minecraft.class_2215;
import net.minecraft.class_2244;
import net.minecraft.class_2248;
import net.minecraft.class_2260;
import net.minecraft.class_2269;
import net.minecraft.class_2272;
import net.minecraft.class_2281;
import net.minecraft.class_2286;
import net.minecraft.class_2289;
import net.minecraft.class_2297;
import net.minecraft.class_2299;
import net.minecraft.class_2301;
import net.minecraft.class_2323;
import net.minecraft.class_2331;
import net.minecraft.class_2336;
import net.minecraft.class_2338;
import net.minecraft.class_2344;
import net.minecraft.class_2349;
import net.minecraft.class_2354;
import net.minecraft.class_2362;
import net.minecraft.class_2369;
import net.minecraft.class_2377;
import net.minecraft.class_238;
import net.minecraft.class_2389;
import net.minecraft.class_2401;
import net.minecraft.class_2440;
import net.minecraft.class_2458;
import net.minecraft.class_2459;
import net.minecraft.class_2462;
import net.minecraft.class_2472;
import net.minecraft.class_2482;
import net.minecraft.class_2484;
import net.minecraft.class_2488;
import net.minecraft.class_2492;
import net.minecraft.class_2508;
import net.minecraft.class_2510;
import net.minecraft.class_2527;
import net.minecraft.class_2533;
import net.minecraft.class_2542;
import net.minecraft.class_2544;
import net.minecraft.class_2546;
import net.minecraft.class_2551;
import net.minecraft.class_2555;
import net.minecraft.class_2557;
import net.minecraft.class_2561;
import net.minecraft.class_2577;
import net.minecraft.class_265;
import net.minecraft.class_3708;
import net.minecraft.class_3709;
import net.minecraft.class_3713;
import net.minecraft.class_3736;
import net.minecraft.class_3749;
import net.minecraft.class_3922;
import net.minecraft.class_3962;
import net.minecraft.class_5172;
import net.minecraft.class_5542;
import net.minecraft.class_5544;
import net.minecraft.class_5545;
import net.minecraft.class_5546;
import net.minecraft.class_5689;
import net.minecraft.class_5800;
import net.minecraft.class_5801;

public class SpiderModule
extends ModuleLayer {
    private final ModeSetting mode;
    private final SliderSetting speed;
    private int tickCounter;
    private boolean wasOnGround;

    public SpiderModule() {
        super(class_2561.method_30163("Spider"), null, Category.Movement);
        String[] tmp0 = new String[5];
        tmp0[0] = "Funtime \u041f\u043e\u043b\u0443\u0431\u043b\u043e\u043a\u0438";
        tmp0[1] = "Matrix";
        tmp0[2] = "Grim";
        tmp0[3] = "Polar";
        tmp0[4] = "Vanilla";
        this.mode = new ModeSetting(class_2561.method_30163("\u0420\u0435\u0436\u0438\u043c"), null, SpiderModule::lambda$new$0).set(tmp0).register(this);
        this.speed = new SliderSetting(class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c"), null, SpiderModule::lambda$new$1).set(0.1f, 3f, 0.1f).set(0.2f).register(this);
        this.tickCounter = 0;
        this.wasOnGround = false;
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (!this.getEnabled().booleanValue() || mc.field_1724 == null || mc.field_1687 == null) {
            return;
        }
        if (!this.isCollidingHorizontally()) {
            return;
        }
        String currentMode = this.mode.getValue();
        var var3 = currentMode;
        int var4 = -1;
        switch (var3.hashCode()) {
            case 2091961303:
                if (var3.equals("Funtime \u041f\u043e\u043b\u0443\u0431\u043b\u043e\u043a\u0438")) {
                    var4 = 0;
                }
            case -1997372447:
                if (var3.equals("Matrix")) {
                    var4 = 1;
                }
            case 2228079:
                if (var3.equals("Grim")) {
                    var4 = 2;
                }
            case 77295390:
                if (var3.equals("Polar")) {
                    var4 = 3;
                }
            case 1897755483:
                if (var3.equals("Vanilla")) {
                    var4 = 4;
                }
            default:
                switch (var4) {
                    case 0:
                        this.handlePartialBlocks();
                    case 1:
                        this.handleMatrix();
                    case 2:
                        this.handleGrim();
                    case 3:
                        this.handlePolar();
                    case 4:
                        this.handleVanilla();
                    default:
                        return;
                }
        }
    }

    private void handlePartialBlocks() {
        if (!mc.field_1724.field_5976) {
            return;
        }
        class_2338 playerPos = mc.field_1724.method_24515();
        for (int y = 0; y <= 2; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (x != 0) {
                        class_2338 checkPos = playerPos.method_10069(x, y, z);
                        class_2248 block = mc.field_1687.method_8320(checkPos).method_26204();
                        if (this.isPartialBlock(block)) {
                            double speedValue = this.speed.getValue().floatValue();
                            mc.field_1724.method_18800(mc.field_1724.method_18798().field_1352, speedValue, mc.field_1724.method_18798().field_1350);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void handleMatrix() {
        if (!mc.field_1724.field_5976) {
            return;
        }
        this.tickCounter = this.tickCounter + 1;
        if (mc.field_1724.method_24828()) {
            this.wasOnGround = true;
            this.tickCounter = 0;
        }
        mc.field_1724.method_18800(mc.field_1724.method_18798().field_1352, 0.2, mc.field_1724.method_18798().field_1350);
        if (this.wasOnGround && this.tickCounter >= 2) {
            this.wasOnGround = false;
            return;
        }
        if (!mc.field_1724.method_24828()) {
            if (mc.field_1724.method_18798().field_1351 < 0) {
                mc.field_1724.method_18800(mc.field_1724.method_18798().field_1352, -0.1, mc.field_1724.method_18798().field_1350);
            }
        }
    }

    private void handleGrim() {
        if (!mc.field_1724.field_5976) {
            return;
        }
        if (mc.field_1724.method_24828()) {
            mc.field_1724.method_18800(mc.field_1724.method_18798().field_1352, 0.42, mc.field_1724.method_18798().field_1350);
        } else {
            if (mc.field_1724.method_18798().field_1351 > 0) {
                mc.field_1724.method_18800(mc.field_1724.method_18798().field_1352, mc.field_1724.method_18798().field_1351 * 0.98, mc.field_1724.method_18798().field_1350);
            }
        }
    }

    private void handlePolar() {
        if (!mc.field_1724.field_5976) {
            return;
        }
        this.tickCounter = this.tickCounter + 1;
        if (mc.field_1724.method_24828()) {
            if (this.tickCounter >= 3) {
                mc.field_1724.method_6043();
                this.tickCounter = 0;
            }
        } else {
            if (mc.field_1724.method_18798().field_1351 < 0.1) {
                mc.field_1724.method_18800(mc.field_1724.method_18798().field_1352, 0.1, mc.field_1724.method_18798().field_1350);
            }
        }
    }

    private void handleVanilla() {
        if (!mc.field_1724.field_5976) {
            return;
        }
        double speedValue = this.speed.getValue().floatValue();
        mc.field_1724.method_18800(mc.field_1724.method_18798().field_1352, speedValue, mc.field_1724.method_18798().field_1350);
    }

    private boolean isCollidingHorizontally() {
        if (mc.field_1724.field_5976) {
            return true;
        }
        class_238 playerBox = mc.field_1724.method_5829().method_1009(0.1, 0, 0.1);
        class_2338 playerPos = mc.field_1724.method_24515();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x != 0) {
                    class_2338 checkPos = playerPos.method_10069(x, 0, z);
                    class_265 shape = mc.field_1687.method_8320(checkPos).method_26220(mc.field_1687, checkPos);
                    if (!shape.method_1110()) {
                        class_238 blockBox = shape.method_1107().method_996(checkPos);
                        if (!playerBox.method_994(blockBox)) continue;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isPartialBlock(class_2248 block) {
        if (block instanceof class_2533) {
            return true;
        }
        if (block instanceof class_2354 || block instanceof class_2349) {
            return true;
        }
        if (block instanceof class_2389) {
            return true;
        }
        if (block instanceof class_2544) {
            return true;
        }
        if (block instanceof class_2482) {
            return true;
        }
        if (block instanceof class_2510) {
            return true;
        }
        if (block instanceof class_2577) {
            return true;
        }
        if (block instanceof class_2244) {
            return true;
        }
        if (block instanceof class_2281 || block instanceof class_2336) {
            return true;
        }
        if (block instanceof class_2331) {
            return true;
        }
        if (block instanceof class_5546) {
            return true;
        }
        if (block instanceof class_2377) {
            return true;
        }
        if (block instanceof class_2286 || block instanceof class_2462) {
            return true;
        }
        if (block instanceof class_2440 || block instanceof class_2557) {
            return true;
        }
        if (block instanceof class_2488) {
            return true;
        }
        if (block instanceof class_2492) {
            return true;
        }
        if (block instanceof class_2344) {
            return true;
        }
        if (block instanceof class_2369) {
            return true;
        }
        if (block instanceof class_2323) {
            return true;
        }
        if (block instanceof class_2349) {
            return true;
        }
        if (block instanceof class_2362) {
            return true;
        }
        if (block instanceof class_2484) {
            return true;
        }
        if (block instanceof class_2527 || block instanceof class_2555) {
            return true;
        }
        if (block instanceof class_2459 || block instanceof class_2458) {
            return true;
        }
        if (block instanceof class_2269) {
            return true;
        }
        if (block instanceof class_2401) {
            return true;
        }
        if (block instanceof class_2215 || block instanceof class_2546) {
            return true;
        }
        if (block instanceof class_2508 || block instanceof class_2551) {
            return true;
        }
        if (block instanceof class_2199) {
            return true;
        }
        if (block instanceof class_2260) {
            return true;
        }
        if (block instanceof class_2272) {
            return true;
        }
        if (block instanceof class_5172) {
            return true;
        }
        if (block instanceof class_3749) {
            return true;
        }
        if (block instanceof class_3709) {
            return true;
        }
        if (block instanceof class_3713) {
            return true;
        }
        if (block instanceof class_3962) {
            return true;
        }
        if (block instanceof class_3708) {
            return true;
        }
        if (block instanceof class_3922) {
            return true;
        }
        if (block instanceof class_2289) {
            return true;
        }
        if (block instanceof class_2472) {
            return true;
        }
        if (block instanceof class_2542) {
            return true;
        }
        if (block instanceof class_2301 || block instanceof class_2297 || block instanceof class_2299) {
            return true;
        }
        if (block instanceof class_3736) {
            return true;
        }
        if (block instanceof class_5544 || block instanceof class_5545) {
            return true;
        }
        if (block instanceof class_5542) {
            return true;
        }
        if (block instanceof class_5689) {
            return true;
        }
        if (block instanceof class_5800) {
            return true;
        }
        if (block instanceof class_5801) {
            return true;
        }
        return false;
    }

    public void deactivate() {
        super.deactivate();
        this.tickCounter = 0;
        this.wasOnGround = false;
    }
}
