/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import fear.dlc.api.events.impl.CollisionEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.ModeListSetting;
import net.minecraft.class_2561;

public class NoPushModule
extends ModuleLayer {
    ModeListSetting collisions;

    public NoPushModule() {
        super(class_2561.method_30163("No Push"), null, Category.Player);
        String[] tmp0 = new String[2];
        tmp0[0] = "\u0418\u0433\u0440\u043e\u043a\u043e\u0432";
        tmp0[1] = "\u0411\u043b\u043e\u043a\u043e\u0432";
        this.collisions = new ModeListSetting(class_2561.method_30163("\u041e\u0442\u043c\u0435\u043d\u044f\u0442\u044c \u043e\u0442"), null, NoPushModule::lambda$new$0).set(tmp0).register(this);
    }

    @Subscribe
    public void playersCollisionEvent(CollisionEvent.PlayerCollisionEvent playerCollisionEvent) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        if (this.collisions.get("\u0418\u0433\u0440\u043e\u043a\u043e\u0432").getEnabled().booleanValue()) {
            playerCollisionEvent.cancel();
        }
    }

    @Subscribe
    public void blocksCollisionEvent(CollisionEvent.BlocksCollisionEvent blocksCollisionEvent) {
        if (!this.getEnabled().booleanValue()) {
            return;
        }
        if (this.collisions.get("\u0411\u043b\u043e\u043a\u043e\u0432").getEnabled().booleanValue()) {
            blocksCollisionEvent.cancel();
        }
    }
}
