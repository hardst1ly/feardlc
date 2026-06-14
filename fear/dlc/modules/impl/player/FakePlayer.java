/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import com.mojang.authlib.GameProfile;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import java.util.UUID;
import net.minecraft.class_1268;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_2663;
import net.minecraft.class_310;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_745;

public class FakePlayer
extends ModuleLayer {
    private static class_745 fakePlayer;
    private static boolean wasAttacked = false;
    private static float damageAmount = 0f;
    private static boolean wasCritical = false;
    BooleanSetting withArmor;
    private int regenTicks;

    public FakePlayer() {
        super(class_2561.method_30163("FakePlayer"), class_2561.method_30163("\u0421\u043f\u0430\u0432\u043d\u0438\u0442 \u0444\u0435\u0439\u043a\u043e\u0432\u043e\u0433\u043e \u0438\u0433\u0440\u043e\u043a\u0430."), Category.Player);
        this.withArmor = new BooleanSetting(class_2561.method_30163("\u0421 \u0431\u0440\u043e\u043d\u0435\u0439"), null, FakePlayer::lambda$new$0).set(true).register(this);
        this.regenTicks = 0;
    }

    public static void onAttack(float damage, boolean isCritical) {
        wasAttacked = true;
        damageAmount = damage;
        wasCritical = isCritical;
    }

    public static void onSelfDestruct() {
        if (class_310.method_1551().field_1687 != null) {
            if (fakePlayer != null) {
                class_310.method_1551().field_1687.method_2945(fakePlayer.method_5628(), class_1297.class_5529.field_26999);
            }
        }
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
        if (mc.field_1724 == null || mc.field_1687 == null) {
            return;
        }
        GameProfile profile = new GameProfile(UUID.randomUUID(), mc.field_1724.method_5477().getString());
        fakePlayer = new class_745(mc.field_1687, profile);
        fakePlayer.method_5878(mc.field_1724);
        class_1293 regenEffect = new class_1293(class_1294.field_5924, -1, 3, false, false, true);
        fakePlayer.method_6092(regenEffect);
        class_1293 resistanceEffect = new class_1293(class_1294.field_5907, -1, 1, false, false, true);
        fakePlayer.method_6092(resistanceEffect);
        class_1293 absorptionEffect = new class_1293(class_1294.field_5898, -1, 4, false, false, true);
        fakePlayer.method_6092(absorptionEffect);
        if (this.withArmor.getEnabled().booleanValue()) {
            fakePlayer.method_5673(class_1304.field_6169, new class_1799(class_1802.field_22027));
            fakePlayer.method_5673(class_1304.field_6174, new class_1799(class_1802.field_22028));
            fakePlayer.method_5673(class_1304.field_6172, new class_1799(class_1802.field_22029));
            fakePlayer.method_5673(class_1304.field_6166, new class_1799(class_1802.field_22030));
        }
        fakePlayer.method_6122(class_1268.field_5810, new class_1799(class_1802.field_8288));
        fakePlayer.method_23327(mc.field_1724.method_23317(), mc.field_1724.method_23318(), mc.field_1724.method_23321());
        mc.field_1687.method_53875(fakePlayer);
    }

    @Subscribe
    public void onUpdate(TickEvent event) {
        if (fakePlayer == null || mc.field_1687 == null) {
            return;
        }
        if (wasAttacked) {
            wasAttacked = false;
        }
        mc.field_1687.method_43128(mc.field_1724, fakePlayer.method_23317(), fakePlayer.method_23318(), fakePlayer.method_23321(), class_3417.field_15115, class_3419.field_15248, 1f, 1f);
        if (wasCritical) {
            mc.field_1687.method_43128(mc.field_1724, fakePlayer.method_23317(), fakePlayer.method_23318(), fakePlayer.method_23321(), class_3417.field_15016, class_3419.field_15248, 1f, 1f);
        }
        fakePlayer.field_6235 = 10;
        fakePlayer.field_6254 = 10;
        float currentHealth = fakePlayer.method_6032();
        float newHealth = currentHealth - damageAmount;
        if (newHealth <= 0f) {
            if (fakePlayer.method_6079().method_7909() == class_1802.field_8288) {
                new class_2663(fakePlayer, 35).method_11471(mc.field_1724.field_3944);
                fakePlayer.method_6033(10f);
                fakePlayer.method_6122(class_1268.field_5810, new class_1799(class_1802.field_8288));
            } else {
                fakePlayer.method_6033(1f);
            }
        } else {
            fakePlayer.method_6033(Math.max(1f, newHealth));
        }
        damageAmount = 0f;
        wasCritical = false;
        this.regenTicks = this.regenTicks + 1;
        if (this.regenTicks >= 5) {
            this.regenTicks = 0;
            float currentHealth = fakePlayer.method_6032();
            if (currentHealth < 20f) {
                float newHealth = Math.min(20f, currentHealth + 2f);
                fakePlayer.method_6033(newHealth);
            }
        }
        if (fakePlayer.method_6079().method_7909() != class_1802.field_8288) {
            fakePlayer.method_6122(class_1268.field_5810, new class_1799(class_1802.field_8288));
        }
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        mc.field_1687.method_2945(fakePlayer.method_5628(), class_1297.class_5529.field_26999);
        if (mc.field_1687 != null && fakePlayer != null) {
            fakePlayer = null;
        }
        wasAttacked = false;
        damageAmount = 0f;
        this.regenTicks = 0;
        super.deactivate();
    }

    public static class_745 getFakePlayer() {
        return fakePlayer;
    }
}
