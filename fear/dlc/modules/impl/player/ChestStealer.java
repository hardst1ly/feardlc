/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.player;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import java.util.Locale;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.class_2561;

public class ChestStealer
extends ModuleLayer {
    public final SliderSetting delay;
    public final BooleanSetting autoClose;
    private long lastStealTime;
    private boolean eventsRegistered;
    private boolean pendingSound;

    public ChestStealer() {
        super(class_2561.method_30163("Chest Stealer"), class_2561.method_30163("\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0437\u0430\u0431\u0438\u0440\u0430\u0435\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u0438\u0437 \u0441\u0443\u043d\u0434\u0443\u043a\u0430."), Category.Player);
        this.delay = new SliderSetting(class_2561.method_30163("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430"), class_2561.method_30163("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430 \u043a\u043b\u0438\u043a\u043e\u0432"), ChestStealer::lambda$new$0).set(0f, 200f, 1f).set(2.5f).register(this);
        this.autoClose = new BooleanSetting(class_2561.method_30163("\u0410\u0432\u0442\u043e \u0437\u0430\u043a\u0440\u044b\u0442\u0438\u0435"), class_2561.method_30163("\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0417\u0430\u043a\u0440\u044b\u0432\u0430\u0435\u0442 \u0441\u0443\u043d\u0434\u0443\u043a"), ChestStealer::lambda$new$1).set(true).register(this);
        this.lastStealTime = 0L;
        this.eventsRegistered = false;
        this.pendingSound = false;
        this.registerEvents();
    }

    private void registerEvents() {
        if (this.eventsRegistered) {
            return;
        }
        this.eventsRegistered = true;
        ClientTickEvents.END_CLIENT_TICK.register(this::lambda$registerEvents$2);
        ClientSendMessageEvents.ALLOW_COMMAND.register(this::lambda$registerEvents$3);
    }

    private String normalizeCommand(String command) {
        String normalized = command.trim().toLowerCase(Locale.ROOT);
        if (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        return normalized;
    }

    private boolean isBlockedCommand(String command) {
        return command.equals("ah") || command.startsWith("ah ") || command.equals("auction") || command.startsWith("auction ") || command.equals("ec") || command.startsWith("ec ") || command.equals("clan storage") || command.startsWith("clan storage ");
    }

    private void disableModule() {
        this.deactivate();
    }

    public void activate() {
        this.lastStealTime = 0L;
        this.pendingSound = false;
    }

    public void deactivate() {
    }
}
