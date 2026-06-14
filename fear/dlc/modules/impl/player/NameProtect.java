/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.TextFactoryEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.FriendManager;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.TextSetting;
import net.minecraft.class_2561;

public class NameProtect
extends ModuleLayer {
    private static NameProtect instance;
    public final TextSetting nameSetting;
    public final BooleanSetting friendsSetting;

    public NameProtect() {
        super(class_2561.method_30163("NameProtect"), class_2561.method_30163("\u0421\u043a\u0440\u044b\u0432\u0430\u0435\u0442 \u0442\u0432\u043e\u0439 \u043d\u0438\u043a \u0438 \u043d\u0438\u043a\u0438 \u0434\u0440\u0443\u0437\u0435\u0439"), Category.Player);
        this.nameSetting = new TextSetting(class_2561.method_30163("\u0424\u0435\u0439\u043a \u0438\u043c\u044f"), class_2561.method_30163("\u0418\u043c\u044f \u043d\u0430 \u043a\u043e\u0442\u043e\u0440\u043e\u0435 \u0431\u0443\u0434\u0435\u0442 \u0437\u0430\u043c\u0435\u043d\u0451\u043d \u0442\u0432\u043e\u0439 \u043d\u0438\u043a"), NameProtect::lambda$new$0).placeholder("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043d\u0438\u043a...").maxLength(32).set("Fear DLC");
        this.nameSetting.register(this);
        this.friendsSetting = new BooleanSetting(class_2561.method_30163("\u0414\u0440\u0443\u0437\u044c\u044f"), class_2561.method_30163("\u0421\u043a\u0440\u044b\u0432\u0430\u0435\u0442 \u043d\u0438\u043a\u043d\u0435\u0439\u043c\u044b \u0434\u0440\u0443\u0437\u0435\u0439"), NameProtect::lambda$new$1).set(true).register(this);
        instance = this;
    }

    public void activate() {
        super.activate();
        FearDCP.getInstance().getEventBus().register(this);
    }

    public void deactivate() {
        FearDCP.getInstance().getEventBus().unregister(this);
        super.deactivate();
    }

    @Subscribe
    public void onTextFactory(TextFactoryEvent event) {
        if (!this.getEnabled().booleanValue() || mc.field_1724 == null) {
            return;
        }
        String fakeName = this.nameSetting.getValue();
        if (fakeName == null || fakeName.isEmpty()) {
            return;
        }
        String playerName = mc.method_1548().method_1676();
        if (playerName != null) {
            if (!playerName.isEmpty()) {
                event.replaceText(playerName, fakeName);
            }
        }
        if (this.friendsSetting.getEnabled().booleanValue()) {
            for (String friend : FriendManager.getFriends()) {
                if (friend != null) {
                    if (friend.isEmpty()) continue;
                    event.replaceText(friend, fakeName);
                }
            }
        }
    }

    public static String replaceName(String originalName) {
        if (instance == null || !instance.getEnabled().booleanValue() || originalName == null) {
            return originalName;
        }
        String fakeName = instance.nameSetting.getValue();
        if (fakeName == null || fakeName.isEmpty()) {
            return originalName;
        }
        if (mc.method_1548() != null) {
            String playerName = mc.method_1548().method_1676();
            if (originalName.equals(playerName)) {
                return fakeName;
            }
        }
        if (!instance.friendsSetting.getEnabled().booleanValue()) return originalName;
        for (String friend : FriendManager.getFriends()) {
            if (!originalName.equals(friend)) continue;
            return fakeName;
        }
        return originalName;
    }

    public static NameProtect getInstance() {
        return instance;
    }
}
