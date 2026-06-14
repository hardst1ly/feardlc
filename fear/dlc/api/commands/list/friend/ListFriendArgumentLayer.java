/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.friend;

import fear.dlc.api.commands.ArgumentLayer;
import fear.dlc.modules.more.FriendManager;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public class ListFriendArgumentLayer
extends ArgumentLayer {
    public ListFriendArgumentLayer() {
        super("list", 0);
    }

    public void execute(List<String> arguments) {
        List<String> friends = FriendManager.getFriends();
        if (friends.isEmpty()) {
            this.printInfo("\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439 \u043f\u0443\u0441\u0442!");
            return;
        }
        this.printInfo("\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439 (" + friends.size() + "):");
        for (int i = 0; i < friends.size(); i++) {
            class_5250 prefix = class_2561.method_43470("  " + i + 1 + ". ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(i * 50)));
            class_5250 name = class_2561.method_43470((String)friends.get(i)).method_10862(class_2583.field_24360.method_36139(-1));
            mc.field_1705.method_1743().method_1812(prefix.method_10852(name));
        }
    }
}
