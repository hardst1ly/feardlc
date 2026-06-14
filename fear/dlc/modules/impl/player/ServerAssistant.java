/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.systems.RenderSystem;
import fear.dlc.api.events.impl.KeyEvent;
import fear.dlc.api.events.impl.RenderEvent;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BindSetting;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.ModeSetting;
import fear.dlc.ui.overlay.NotificationLayer;
import fear.dlc.utility.inventory.SilentInventoryUtil;
import fear.dlc.utility.movement.MovementFreeze;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_10142;
import net.minecraft.class_1268;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_408;
import net.minecraft.class_4587;
import org.joml.Matrix4f;

public class ServerAssistant
extends ModuleLayer {
    private final ModeSetting serverMode;
    private final BooleanSetting itemRadius;
    private final BindSetting disorientationKey;
    private final BindSetting trapKey;
    private final BindSetting dustKey;
    private final BindSetting plastKey;
    private final BindSetting freezeKey;
    private final BindSetting fireSwirlKey;
    private final BindSetting godAuraKey;
    private final BindSetting explosiveTrapKey;
    private final BindSetting normalTrapKey;
    private final BindSetting stanKey;
    private final BindSetting explosiveItemKey;
    private final BindSetting snowballKey;
    private final BindSetting jackLanternKey;
    private final BindSetting antiFlightKey;
    private final BindSetting expScrollKey;
    private final List<AbilityBind> abilityBinds;
    private int lastPressedKey;
    private long lastPressTime;
    private boolean pendingUse;
    private int pendingInventorySlot;
    private int pendingStage;
    private int pendingTimer;

    public ServerAssistant() {
        super(class_2561.method_30163("Server Assistant"), class_2561.method_30163("FT HW RW \u0425\u0435\u043b\u043f\u0435\u0440"), Category.Player);
        String[] tmp0 = new String[3];
        tmp0[0] = "FunTime";
        tmp0[1] = "HolyWorld";
        tmp0[2] = "ReallyWorld";
        this.serverMode = new ModeSetting(class_2561.method_30163("\u0421\u0435\u0440\u0432\u0435\u0440"), null, ServerAssistant::lambda$new$0).set(tmp0).set("FunTime").register(this);
        this.itemRadius = new BooleanSetting(class_2561.method_30163("Item Radius"), null, ServerAssistant::lambda$new$1).set(true).register(this);
        this.disorientationKey = new BindSetting(class_2561.method_30163("\u0414\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044f"), null, this::lambda$new$2).register(this);
        this.trapKey = new BindSetting(class_2561.method_30163("\u0422\u0440\u0430\u043f\u043a\u0430"), null, this::lambda$new$3).register(this);
        this.dustKey = new BindSetting(class_2561.method_30163("\u042f\u0432\u043d\u0430\u044f \u043f\u044b\u043b\u044c"), null, this::lambda$new$4).register(this);
        this.plastKey = new BindSetting(class_2561.method_30163("\u041f\u043b\u0430\u0441\u0442"), null, this::lambda$new$5).register(this);
        this.freezeKey = new BindSetting(class_2561.method_30163("\u0421\u043d\u0435\u0436\u043e\u043a \u0437\u0430\u043c\u043e\u0440\u043e\u0437\u043a\u0438"), null, this::lambda$new$6).register(this);
        this.fireSwirlKey = new BindSetting(class_2561.method_30163("\u041e\u0433\u043d\u0435\u043d\u043d\u044b\u0439 \u0441\u043c\u0435\u0440\u0447"), null, this::lambda$new$7).register(this);
        this.godAuraKey = new BindSetting(class_2561.method_30163("\u0411\u043e\u0436\u044c\u044f \u0430\u0443\u0440\u0430"), null, this::lambda$new$8).register(this);
        this.explosiveTrapKey = new BindSetting(class_2561.method_30163("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0442\u0440\u0430\u043f\u043a\u0430"), null, this::lambda$new$9).register(this);
        this.normalTrapKey = new BindSetting(class_2561.method_30163("\u041e\u0431\u044b\u0447\u043d\u0430\u044f \u0442\u0440\u0430\u043f\u043a\u0430"), null, this::lambda$new$10).register(this);
        this.stanKey = new BindSetting(class_2561.method_30163("\u0421\u0442\u0430\u043d"), null, this::lambda$new$11).register(this);
        this.explosiveItemKey = new BindSetting(class_2561.method_30163("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0448\u0442\u0443\u0447\u043a\u0430"), null, this::lambda$new$12).register(this);
        this.snowballKey = new BindSetting(class_2561.method_30163("\u041a\u043e\u043c \u0421\u043d\u0435\u0433\u0430"), null, this::lambda$new$13).register(this);
        this.jackLanternKey = new BindSetting(class_2561.method_30163("\u0421\u0432\u0435\u0442\u0438\u043b\u044c\u043d\u0438\u043a \u0414\u0436\u0435\u043a\u0430"), null, this::lambda$new$14).register(this);
        this.antiFlightKey = new BindSetting(class_2561.method_30163("\u0410\u043d\u0442\u0438 \u043f\u043e\u043b\u0435\u0442"), null, this::lambda$new$15).register(this);
        this.expScrollKey = new BindSetting(class_2561.method_30163("\u0421\u0432\u0438\u0442\u043e\u043a \u043e\u043f\u044b\u0442\u0430"), null, this::lambda$new$16).register(this);
        this.abilityBinds = new ArrayList();
        this.lastPressedKey = -1;
        this.lastPressTime = 0L;
        this.pendingUse = false;
        this.pendingInventorySlot = -1;
        this.pendingStage = 0;
        this.pendingTimer = 0;
        this.initializeAbilities();
    }

    private void initializeAbilities() {
        this.abilityBinds.add(new AbilityBind("\u0414\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044f", class_1802.field_8449, "FunTime", this.disorientationKey));
        this.abilityBinds.add(new AbilityBind("\u0422\u0440\u0430\u043f\u043a\u0430", class_1802.field_22021, "FunTime", this.trapKey));
        this.abilityBinds.add(new AbilityBind("\u042f\u0432\u043d\u0430\u044f \u043f\u044b\u043b\u044c", class_1802.field_8479, "FunTime", this.dustKey));
        this.abilityBinds.add(new AbilityBind("\u041f\u043b\u0430\u0441\u0442", class_1802.field_8551, "FunTime", this.plastKey));
        this.abilityBinds.add(new AbilityBind("\u0417\u0430\u043c\u043e\u0440\u043e\u0437\u043a\u0430", class_1802.field_8543, "FunTime", this.freezeKey));
        this.abilityBinds.add(new AbilityBind("\u041e\u0433\u043d\u0435\u043d\u043d\u044b\u0439 \u0441\u043c\u0435\u0440\u0447", class_1802.field_8814, "FunTime", this.fireSwirlKey));
        this.abilityBinds.add(new AbilityBind("\u0411\u043e\u0436\u044c\u044f \u0430\u0443\u0440\u0430", class_1802.field_8614, "FunTime", this.godAuraKey));
        this.abilityBinds.add(new AbilityBind("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0442\u0440\u0430\u043f\u043a\u0430", class_1802.field_8662, "HolyWorld", this.explosiveTrapKey));
        this.abilityBinds.add(new AbilityBind("\u041e\u0431\u044b\u0447\u043d\u0430\u044f \u0442\u0440\u0430\u043f\u043a\u0430", class_1802.field_8882, "HolyWorld", this.normalTrapKey));
        this.abilityBinds.add(new AbilityBind("\u0421\u0442\u0430\u043d", class_1802.field_8137, "HolyWorld", this.stanKey));
        this.abilityBinds.add(new AbilityBind("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0448\u0442\u0443\u0447\u043a\u0430", class_1802.field_8814, "HolyWorld", this.explosiveItemKey));
        this.abilityBinds.add(new AbilityBind("\u041a\u043e\u043c \u0421\u043d\u0435\u0433\u0430", class_1802.field_8543, "HolyWorld", this.snowballKey));
        this.abilityBinds.add(new AbilityBind("\u0421\u0432\u0435\u0442\u0438\u043b\u044c\u043d\u0438\u043a \u0414\u0436\u0435\u043a\u0430", class_1802.field_8693, "HolyWorld", this.jackLanternKey));
        this.abilityBinds.add(new AbilityBind("\u0410\u043d\u0442\u0438 \u043f\u043e\u043b\u0435\u0442", class_1802.field_8450, "ReallyWorld", this.antiFlightKey));
        this.abilityBinds.add(new AbilityBind("\u0421\u0432\u0438\u0442\u043e\u043a \u043e\u043f\u044b\u0442\u0430", class_1802.field_8498, "ReallyWorld", this.expScrollKey));
    }

    @Subscribe
    public void onKey(KeyEvent e) {
        if (!super.getEnabled().booleanValue()) {
            return;
        }
        if (mc.field_1724 == null || mc.field_1755 != null || e.getAction() != 1) {
            return;
        }
        if (mc.field_1755 instanceof class_408) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        if (e.getKey() == this.lastPressedKey && currentTime - this.lastPressTime < 200L) {
            return;
        }
        for (AbilityBind bind : this.abilityBinds) {
            if (!bind.keySetting.getKey().intValue() != -1 && e.getKey() == bind.keySetting.getKey().intValue() && bind.server.equals(this.serverMode.getValue())) continue;
            this.lastPressedKey = e.getKey();
            this.lastPressTime = currentTime;
            this.useAbility(bind.item, bind.name);
            break;
        }
    }

    @Subscribe
    public void onTick(TickEvent e) {
        if (this.pendingUse) {
            this.handlePendingUse();
        }
    }

    private void handlePendingUse() {
        if (mc.field_1724 == null || mc.field_1761 == null) {
            this.resetPending();
            return;
        }
        if (MovementFreeze.isActive()) {
            return;
        }
        if (this.pendingTimer > 0) {
            this.pendingTimer = this.pendingTimer - 1;
            return;
        }
        int hotbarIndex = mc.field_1724.method_31548().field_7545;
        switch (this.pendingStage) {
            case 0:
                SilentInventoryUtil.openInventory();
                this.pendingStage = 1;
                this.pendingTimer = 1;
                return;
            case 1:
                SilentInventoryUtil.swapWithHotbar(this.pendingInventorySlot, hotbarIndex);
                this.pendingStage = 2;
                this.pendingTimer = 1;
                return;
            case 2:
                mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
                MovementFreeze.request(1);
                this.pendingStage = 3;
                this.pendingTimer = 1;
                return;
            case 3:
                SilentInventoryUtil.swapWithHotbar(this.pendingInventorySlot, hotbarIndex);
                this.pendingStage = 4;
                this.pendingTimer = 1;
                return;
            case 4:
                SilentInventoryUtil.closeInventory();
                this.resetPending();
                return;
            default:
                this.resetPending();
                return;
        }
    }

    private void resetPending() {
        if (this.pendingUse) {
            if (mc.field_1724 != null) {
                if (mc.method_1562() != null) {
                    SilentInventoryUtil.closeInventory();
                }
            }
        }
        this.pendingUse = false;
        this.pendingInventorySlot = -1;
        this.pendingStage = 0;
        this.pendingTimer = 0;
    }

    @Subscribe
    public void onWorldRender(RenderEvent.AfterHand e) {
        if (!super.getEnabled().booleanValue()) {
            return;
        }
        if (!this.itemRadius.getEnabled().booleanValue() || !this.serverMode.getValue().equals("FunTime") || mc.field_1724 == null) {
            return;
        }
        class_1799 mainHand = mc.field_1724.method_6047();
        class_1799 offHand = mc.field_1724.method_6079();
        class_1792 mainItem = mainHand.method_7960() ? null : mainHand.method_7909();
        class_1792 offItem = offHand.method_7960() ? null : offHand.method_7909();
        if (mainItem == class_1802.field_22021 || offItem == class_1802.field_22021) {
            this.renderTrapka(e);
            return;
        }
        if (mainItem == class_1802.field_8551 || offItem == class_1802.field_8551) {
            this.renderPlast(e);
            return;
        }
        double radius = this.getItemRadius(mainHand);
        if (radius <= 0) {
            radius = this.getItemRadius(offHand);
        }
        if (radius > 0) {
            class_243 playerPos = mc.field_1724.method_19538();
            class_243 center = new class_243(playerPos.field_1352, playerPos.field_1351 - 1.4, playerPos.field_1350);
            this.renderCircle(e, center, radius);
        }
    }

    private double getItemRadius(class_1799 stack) {
        if (stack.method_7960()) {
            return 0;
        }
        class_1792 item = stack.method_7909();
        if (item == class_1802.field_8449) {
            return 10;
        }
        if (item == class_1802.field_8479) {
            return 10;
        }
        if (item == class_1802.field_8814) {
            return 10;
        }
        if (item == class_1802.field_8614) {
            return 2;
        }
        return 0;
    }

    private void renderTrapka(RenderEvent.AfterHand event) {
        class_243 position = mc.field_1724.method_19538();
        double cubeX = Math.floor(position.field_1352) + 0.5;
        double cubeY = Math.floor(position.field_1351) + 0.5 + 1.625;
        double cubeZ = Math.floor(position.field_1350) + 0.5;
        float halfSize = 2f;
        class_243 cameraPos = mc.field_1773.method_19418().method_19326();
        int color = -2907137;
        int outlineColor = color & 16777215 | -16777216;
        class_4587 stack = event.getStack();
        stack.method_22903();
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(class_10142.field_53876);
        class_289 tessellator = class_289.method_1348();
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_29344, class_290.field_1576);
        Matrix4f matrix = stack.method_23760().method_23761();
        float x1 = (cubeX - (double)halfSize - cameraPos.field_1352);
        float y1 = (cubeY - (double)halfSize - cameraPos.field_1351);
        float z1 = (cubeZ - (double)halfSize - cameraPos.field_1350);
        float x2 = (cubeX + (double)halfSize - cameraPos.field_1352);
        float y2 = (cubeY + (double)halfSize - cameraPos.field_1351);
        float z2 = (cubeZ + (double)halfSize - cameraPos.field_1350);
        this.drawLine(buffer, matrix, x1, y1, z1, x2, y1, z1, outlineColor);
        this.drawLine(buffer, matrix, x2, y1, z1, x2, y1, z2, outlineColor);
        this.drawLine(buffer, matrix, x2, y1, z2, x1, y1, z2, outlineColor);
        this.drawLine(buffer, matrix, x1, y1, z2, x1, y1, z1, outlineColor);
        this.drawLine(buffer, matrix, x1, y2, z1, x2, y2, z1, outlineColor);
        this.drawLine(buffer, matrix, x2, y2, z1, x2, y2, z2, outlineColor);
        this.drawLine(buffer, matrix, x2, y2, z2, x1, y2, z2, outlineColor);
        this.drawLine(buffer, matrix, x1, y2, z2, x1, y2, z1, outlineColor);
        this.drawLine(buffer, matrix, x1, y1, z1, x1, y2, z1, outlineColor);
        this.drawLine(buffer, matrix, x2, y1, z1, x2, y2, z1, outlineColor);
        this.drawLine(buffer, matrix, x2, y1, z2, x2, y2, z2, outlineColor);
        this.drawLine(buffer, matrix, x1, y1, z2, x1, y2, z2, outlineColor);
        class_286.method_43433(buffer.method_60800());
        stack.method_22909();
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private void renderPlast(RenderEvent.AfterHand event) {
        class_243 lookVec = mc.field_1724.method_5828(event.getTickCounter().method_60637(true));
        class_243 eyePos = mc.field_1724.method_33571();
        class_243 center = eyePos.method_1019(lookVec.method_1021(4));
        float width = 4f;
        float height = 4f;
        float halfWidth = width / 2f;
        float halfHeight = height / 2f;
        class_243 cameraPos = mc.field_1773.method_19418().method_19326();
        int color = -2907137;
        int outlineColor = color & 16777215 | -16777216;
        class_4587 stack = event.getStack();
        stack.method_22903();
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(class_10142.field_53876);
        class_289 tessellator = class_289.method_1348();
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_29344, class_290.field_1576);
        Matrix4f matrix = stack.method_23760().method_23761();
        double ax = Math.abs(lookVec.field_1352);
        double ay = Math.abs(lookVec.field_1351);
        double az = Math.abs(lookVec.field_1350);
        float cx = (center.field_1352 - cameraPos.field_1352);
        float cy = (center.field_1351 - cameraPos.field_1351);
        float cz = (center.field_1350 - cameraPos.field_1350);
        if (ay >= ax) {
            if (ay >= az) {
                this.drawLine(buffer, matrix, cx - halfWidth, cy, cz - halfHeight, cx + halfWidth, cy, cz - halfHeight, outlineColor);
                this.drawLine(buffer, matrix, cx + halfWidth, cy, cz - halfHeight, cx + halfWidth, cy, cz + halfHeight, outlineColor);
                this.drawLine(buffer, matrix, cx + halfWidth, cy, cz + halfHeight, cx - halfWidth, cy, cz + halfHeight, outlineColor);
                this.drawLine(buffer, matrix, cx - halfWidth, cy, cz + halfHeight, cx - halfWidth, cy, cz - halfHeight, outlineColor);
            }
        } else {
            if (ax >= az) {
                this.drawLine(buffer, matrix, cx, cy - halfHeight, cz - halfWidth, cx, cy - halfHeight, cz + halfWidth, outlineColor);
                this.drawLine(buffer, matrix, cx, cy - halfHeight, cz + halfWidth, cx, cy + halfHeight, cz + halfWidth, outlineColor);
                this.drawLine(buffer, matrix, cx, cy + halfHeight, cz + halfWidth, cx, cy + halfHeight, cz - halfWidth, outlineColor);
                this.drawLine(buffer, matrix, cx, cy + halfHeight, cz - halfWidth, cx, cy - halfHeight, cz - halfWidth, outlineColor);
            } else {
                this.drawLine(buffer, matrix, cx - halfWidth, cy - halfHeight, cz, cx + halfWidth, cy - halfHeight, cz, outlineColor);
                this.drawLine(buffer, matrix, cx + halfWidth, cy - halfHeight, cz, cx + halfWidth, cy + halfHeight, cz, outlineColor);
                this.drawLine(buffer, matrix, cx + halfWidth, cy + halfHeight, cz, cx - halfWidth, cy + halfHeight, cz, outlineColor);
                this.drawLine(buffer, matrix, cx - halfWidth, cy + halfHeight, cz, cx - halfWidth, cy - halfHeight, cz, outlineColor);
            }
        }
        class_286.method_43433(buffer.method_60800());
        stack.method_22909();
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private void drawLine(class_287 buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, int color) {
        buffer.method_22918(matrix, x1, y1, z1).method_39415(color);
        buffer.method_22918(matrix, x2, y2, z2).method_39415(color);
    }

    private void renderCircle(RenderEvent.AfterHand event, class_243 center, double radius) {
        int color = -2907137;
        int fillAlpha = 85;
        int outlineAlpha = 255;
        int fillColor = color & 16777215 | fillAlpha << 24;
        int outlineColor = color & 16777215 | outlineAlpha << 24;
        class_243 cameraPos = mc.field_1773.method_19418().method_19326();
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(class_10142.field_53876);
        class_4587 stack = event.getStack();
        stack.method_22903();
        class_289 tessellator = class_289.method_1348();
        Matrix4f matrix = stack.method_23760().method_23761();
        class_287 buffer = tessellator.method_60827(class_293.class_5596.field_29345, class_290.field_1576);
        float angle = 0f;
        while (angle <= 360f) {
            double radians = Math.toRadians((double)angle);
            double x = center.field_1352 + Math.sin(radians) * radius - cameraPos.field_1352;
            double y = center.field_1351 - cameraPos.field_1351;
            double z = center.field_1350 - Math.cos(radians) * radius - cameraPos.field_1350;
            buffer.method_22918(matrix, (float)x, (float)y, (float)z).method_39415(outlineColor);
            angle = angle + 5f;
        }
        class_286.method_43433(buffer.method_60800());
        buffer = tessellator.method_60827(class_293.class_5596.field_27381, class_290.field_1576);
        buffer.method_22918(matrix, (float)(center.field_1352 - cameraPos.field_1352), (float)(center.field_1351 - cameraPos.field_1351), (float)(center.field_1350 - cameraPos.field_1350)).method_39415(fillColor);
        float angle = 0f;
        while (angle <= 360f) {
            double radians = Math.toRadians((double)angle);
            double x = center.field_1352 + Math.sin(radians) * radius - cameraPos.field_1352;
            double y = center.field_1351 - cameraPos.field_1351;
            double z = center.field_1350 - Math.cos(radians) * radius - cameraPos.field_1350;
            buffer.method_22918(matrix, (float)x, (float)y, (float)z).method_39415(fillColor);
            angle = angle + 5f;
        }
        class_286.method_43433(buffer.method_60800());
        stack.method_22909();
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private void useAbility(class_1792 item, String name) {
        class_1799 itemStack = new class_1799(item);
        if (mc.field_1724.method_7357().method_7904(itemStack)) {
            float cooldownProgress = mc.field_1724.method_7357().method_7905(itemStack, mc.method_61966().method_60637(true));
            float cooldownSeconds = cooldownProgress * 20f / 20f;
            Object[] tmp0 = new Object[2];
            tmp0[0] = name;
            tmp0[1] = cooldownSeconds;
            NotificationLayer.addNotification(String.format("%s \u043d\u0430 \u043a\u0443\u043b\u0434\u0430\u0443\u043d\u0435! %.1f \u0441\u0435\u043a", tmp0), NotificationLayer.NotificationType.WARNING);
            return;
        }
        int hotbarSlot = this.findItemInHotbar(item, name);
        if (hotbarSlot != -1) {
            this.useFromHotbar(hotbarSlot);
            NotificationLayer.addNotification("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0430 \u0441\u043f\u043e\u0441\u043e\u0431\u043d\u043e\u0441\u0442\u044c: " + name, NotificationLayer.NotificationType.SUCCESS);
            return;
        }
        int inventorySlot = this.findItemInInventory(item, name);
        if (inventorySlot == -1) {
            NotificationLayer.addNotification("\u041f\u0440\u0435\u0434\u043c\u0435\u0442 " + name + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d!", NotificationLayer.NotificationType.ERROR);
            return;
        }
        this.startPendingUse(inventorySlot);
        NotificationLayer.addNotification("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0430 \u0441\u043f\u043e\u0441\u043e\u0431\u043d\u043e\u0441\u0442\u044c: " + name, NotificationLayer.NotificationType.SUCCESS);
    }

    private void startPendingUse(int inventorySlot) {
        if (this.pendingUse) {
            return;
        }
        this.pendingUse = true;
        this.pendingInventorySlot = inventorySlot;
        this.pendingStage = 0;
        this.pendingTimer = 0;
    }

    private void useFromHotbar(int hotbarSlot) {
        int originalSlot = mc.field_1724.method_31548().field_7545;
        mc.field_1724.method_31548().field_7545 = hotbarSlot;
        mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
        mc.field_1724.method_31548().field_7545 = originalSlot;
    }

    private int findItemInHotbar(class_1792 item, String name) {
        String searchName = name.toLowerCase();
        for (int i = 0; i < 9; i++) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            if (!stack.method_7960()) {
                if (stack.method_7909() == item) {
                    String itemName = stack.method_7964().getString().toLowerCase();
                    if (!itemName.contains(searchName) || this.matchesAbilityName(searchName, itemName)) continue;
                    return i;
                }
            }
        }
        return -1;
    }

    private int findItemInInventory(class_1792 item, String name) {
        String searchName = name.toLowerCase();
        for (int i = 9; i < 36; i++) {
            class_1799 stack = mc.field_1724.field_7498.method_7611(i).method_7677();
            if (!stack.method_7960()) {
                if (stack.method_7909() == item) {
                    String itemName = stack.method_7964().getString().toLowerCase();
                    if (!itemName.contains(searchName) || this.matchesAbilityName(searchName, itemName)) continue;
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean matchesAbilityName(String searchName, String itemName) {
        if (!searchName.contains("\u0434\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044f") || !itemName.contains("\u0434\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044f")) {
            if (!searchName.contains("\u0442\u0440\u0430\u043f\u043a\u0430") || !itemName.contains("\u0442\u0440\u0430\u043f\u043a\u0430")) {
                if (!searchName.contains("\u044f\u0432\u043d\u0430\u044f") || !itemName.contains("\u044f\u0432\u043d\u0430\u044f")) {
                    if (!searchName.contains("\u043f\u043b\u0430\u0441\u0442") || !itemName.contains("\u043f\u043b\u0430\u0441\u0442")) {
                        if (!searchName.contains("\u0437\u0430\u043c\u043e\u0440\u043e\u0437\u043a\u0430") || !itemName.contains("\u0437\u0430\u043c\u043e\u0440\u043e\u0437\u043a\u0430")) {
                            if (!searchName.contains("\u043e\u0433\u043d\u0435\u043d\u043d\u044b\u0439") || !itemName.contains("\u043e\u0433\u043d\u0435\u043d\u043d\u044b\u0439")) {
                                if (!searchName.contains("\u0431\u043e\u0436\u044c\u044f") || !itemName.contains("\u0431\u043e\u0436\u044c\u044f")) {
                                    if (!searchName.contains("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0442\u0440\u0430\u043f\u043a\u0430") || !itemName.contains("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f")) {
                                        if (!searchName.contains("\u043e\u0431\u044b\u0447\u043d\u0430\u044f") || !itemName.contains("\u043e\u0431\u044b\u0447\u043d\u0430\u044f")) {
                                            if (!searchName.contains("\u0441\u0442\u0430\u043d") || !itemName.contains("\u0441\u0442\u0430\u043d")) {
                                                if (!searchName.contains("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0448\u0442\u0443\u0447\u043a\u0430") || !itemName.contains("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0448\u0442\u0443\u0447\u043a\u0430")) {
                                                    if (!searchName.contains("\u043a\u043e\u043c \u0441\u043d\u0435\u0433\u0430") || !itemName.contains("\u043a\u043e\u043c")) {
                                                        if (!searchName.contains("\u0441\u0432\u0435\u0442\u0438\u043b\u044c\u043d\u0438\u043a") || !itemName.contains("\u0441\u0432\u0435\u0442\u0438\u043b\u044c\u043d\u0438\u043a")) {
                                                            if (!searchName.contains("\u0430\u043d\u0442\u0438 \u043f\u043e\u043b\u0435\u0442") || !itemName.contains("\u0430\u043d\u0442\u0438")) {
                                                                if (!searchName.contains("\u0441\u0432\u0438\u0442\u043e\u043a")) { /* goto @304; */ }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void deactivate() {
        super.deactivate();
        this.resetPending();
    }
}
