/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import com.google.common.eventbus.Subscribe;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.FriendManager;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ColorSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_2561;

public class PlayerOutlineModule
extends ModuleLayer {
    private static PlayerOutlineModule instance;
    public final ColorSetting outlineColor;
    public final SliderSetting alpha;
    public final BooleanSetting pulse;
    public final SliderSetting pulseSpeed;
    public final BooleanSetting includeFriends;
    public final BooleanSetting onlyEnemies;
    public final SliderSetting maxDistance;
    private Method setFlagMethod;
    private Method getFlagMethod;
    private final Map<Integer, Boolean> originalGlowStates;
    private final Set<Integer> outlined;
    private long startTime;

    public PlayerOutlineModule() {
        super(class_2561.method_30163("Player Outline"), class_2561.method_30163("\u041a\u0430\u0441\u0442\u043e\u043c\u043d\u0430\u044f \u043e\u0431\u0432\u043e\u0434\u043a\u0430 \u0438\u0433\u0440\u043e\u043a\u043e\u0432"), Category.Render);
        this.outlineColor = new ColorSetting(class_2561.method_30163("\u0426\u0432\u0435\u0442"), class_2561.method_30163("\u0426\u0432\u0435\u0442 \u043a\u043e\u043d\u0442\u0443\u0440\u0430"), PlayerOutlineModule::lambda$new$0).set(-1).register(this);
        this.alpha = new SliderSetting(class_2561.method_30163("\u041f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u044c"), class_2561.method_30163("\u0410\u043b\u044c\u0444\u0430 \u043a\u0430\u043d\u0430\u043b \u043a\u043e\u043d\u0442\u0443\u0440\u0430"), PlayerOutlineModule::lambda$new$1).set(50f, 255f, 1f).set(255f).register(this);
        this.pulse = new BooleanSetting(class_2561.method_30163("\u041f\u0443\u043b\u044c\u0441\u0430\u0446\u0438\u044f"), class_2561.method_30163("\u041f\u043b\u0430\u0432\u043d\u043e\u0435 \u043c\u0435\u0440\u0446\u0430\u043d\u0438\u0435 \u043a\u043e\u043d\u0442\u0443\u0440\u0430"), PlayerOutlineModule::lambda$new$2).set(false).register(this);
        Objects.requireNonNull(this.pulse);
        this.pulseSpeed = new SliderSetting(class_2561.method_30163("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u043f\u0443\u043b\u044c\u0441\u0430\u0446\u0438\u0438"), null, this.pulse::getEnabled).set(0.1f, 5f, 0.1f).set(1f).register(this);
        this.includeFriends = new BooleanSetting(class_2561.method_30163("\u0414\u0440\u0443\u0437\u044c\u044f"), class_2561.method_30163("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u043e\u0431\u0432\u043e\u0434\u043a\u0443 \u0443 \u0434\u0440\u0443\u0437\u0435\u0439"), PlayerOutlineModule::lambda$new$3).set(true).register(this);
        this.onlyEnemies = new BooleanSetting(class_2561.method_30163("\u0422\u043e\u043b\u044c\u043a\u043e \u0432\u0440\u0430\u0433\u0438"), class_2561.method_30163("\u041d\u0435 \u043f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0442\u044c \u0441\u0432\u043e\u0438\u0445 \u0438 \u0434\u0440\u0443\u0437\u0435\u0439"), PlayerOutlineModule::lambda$new$4).set(false).register(this);
        this.maxDistance = new SliderSetting(class_2561.method_30163("\u041c\u0430\u043a\u0441. \u0434\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f"), null, PlayerOutlineModule::lambda$new$5).set(8f, 256f, 1f).set(96f).register(this);
        this.originalGlowStates = new HashMap();
        this.outlined = new HashSet();
        this.startTime = System.currentTimeMillis();
        instance = this;
        this.initReflection();
    }

    public static PlayerOutlineModule getInstance() {
        return instance;
    }

    private void initReflection() {
        String[] tmp0 = new String[2];
        tmp0[0] = "setFlag";
        tmp0[1] = "method_5729";
        var var1 = tmp0;
        var var2 = var1.length;
        int var3 = 0;
        if (var3 < var2) {
            String name = var1[var3];
        }
        try {
            Class[] tmp1 = new Class[2];
            tmp1[0] = Integer.TYPE;
            tmp1[1] = Boolean.TYPE;
            this.setFlagMethod = class_1297.class.getDeclaredMethod(name, tmp1);
            this.setFlagMethod.setAccessible(true);
        }
        catch (NoSuchMethodException e5) {
            var3++;
            /* goto @20; */
            String[] tmp2 = new String[2];
            tmp2[0] = "getFlag";
            tmp2[1] = "method_5795";
            var1 = tmp2;
            var2 = var1.length;
            for (var3 = 0; var3 < var2; var3++) {
                String name = var1[var3];
                Class[] tmp3 = new Class[1];
                tmp3[0] = Integer.TYPE;
                this.getFlagMethod = class_1297.class.getDeclaredMethod(name, tmp3);
                this.getFlagMethod.setAccessible(true);
                break;
                var5 = null;
            }
            return;
        }
        String[] tmp4 = new String[2];
        tmp4[0] = "getFlag";
        tmp4[1] = "method_5795";
        var1 = tmp4;
        var2 = var1.length;
        var3 = 0;
        if (var3 < var2) {
            name = var1[var3];
        }
        try {
            Class[] tmp5 = new Class[1];
            tmp5[0] = Integer.TYPE;
            this.getFlagMethod = class_1297.class.getDeclaredMethod(name, tmp5);
            this.getFlagMethod.setAccessible(true);
        }
        catch (NoSuchMethodException e5) {
            var3++;
            /* goto @98; */
            return;
        }
    }

    public void activate() {
        super.activate();
        this.startTime = System.currentTimeMillis();
        this.originalGlowStates.clear();
        this.outlined.clear();
    }

    public void deactivate() {
        if (mc.field_1687 != null) {
            for (Map.Entry<Integer, Boolean> e : this.originalGlowStates.entrySet()) {
                class_1297 entity = mc.field_1687.method_8469(((Integer)e.getKey()).intValue());
                if (entity == null) continue;
                this.setGlowing(entity, ((Boolean)e.getValue()).booleanValue());
            }
        }
        this.originalGlowStates.clear();
        this.outlined.clear();
        super.deactivate();
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        if (mc.field_1724 == null || mc.field_1687 == null) {
            return;
        }
        Set<Integer> wantOutlined = new HashSet();
        float maxDist = this.maxDistance.getValue().floatValue();
        float maxDistSq = maxDist * maxDist;
        for (class_1657 p : mc.field_1687.method_18456()) {
            while (p == mc.field_1724) {
            }
            if (!p.method_5805()) continue;
            while (p.method_31481()) {
            }
            while (p.method_5858(mc.field_1724) > (double)maxDistSq) {
            }
            boolean isFriend = FriendManager.isFriend(p.method_5477().getString());
            while (this.onlyEnemies.getEnabled().booleanValue()) {
                if (!isFriend) break;
            }
            while (isFriend) {
                if (this.includeFriends.getEnabled().booleanValue()) break;
            }
            wantOutlined.add(p.method_5628());
            if (!this.originalGlowStates.containsKey(p.method_5628())) {
                this.originalGlowStates.put(p.method_5628(), this.isGlowing(p));
            }
            if (this.isGlowing(p)) continue;
            this.setGlowing(p, true);
        }
        it = this.originalGlowStates.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Boolean> entry = it.next();
            if (!wantOutlined.contains(entry.getKey())) {
                class_1297 ent = mc.field_1687.method_8469(((Integer)entry.getKey()).intValue());
                if (ent == null) continue;
                this.setGlowing(ent, ((Boolean)entry.getValue()).booleanValue());
                it.remove();
            }
        }
        this.outlined.clear();
        this.outlined.addAll(wantOutlined);
    }

    private void setGlowing(class_1297 entity, boolean glowing) {
        if (this.setFlagMethod == null) {
            return;
        }
        try {
            Object[] tmp0 = new Object[2];
            tmp0[0] = 6;
            tmp0[1] = glowing;
            this.setFlagMethod.invoke(entity, tmp0);
        }
        catch (Exception e3) {
            return;
        }
    }

    private boolean isGlowing(class_1297 entity) {
        if (this.getFlagMethod == null) {
            return false;
        }
        try {
            Object[] tmp0 = new Object[1];
            tmp0[0] = 6;
            return ((Boolean)this.getFlagMethod.invoke(entity, tmp0)).booleanValue();
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean isEntityOutlined(class_1297 entity) {
        return this.outlined.contains(entity.method_5628());
    }

    public int[] getOutlineRGBA() {
        int rgb = this.outlineColor.getColorRGB();
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;
        float a = this.alpha.getValue().floatValue() / 255f;
        if (this.pulse.getEnabled().booleanValue()) {
            float t = (System.currentTimeMillis() - this.startTime) / 1000f;
            float wave = (Math.sin((double)(t * this.pulseSpeed.getValue().floatValue()) * 3.141592653589793 * 2) * 0.5 + 0.5);
            a = a * (0.4f + 0.6f * wave);
        }
        int finalAlpha = Math.max(0, Math.min(255, (int)(a * 255f)));
        int[] tmp0 = new int[4];
        tmp0[0] = r;
        tmp0[1] = g;
        tmp0[2] = b;
        tmp0[3] = finalAlpha;
        return tmp0;
    }
}
