/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.friend;

import fear.dlc.api.commands.ArgumentLayer;
import fear.dlc.modules.more.FriendManager;
import java.util.List;

public class RemoveFriendArgumentLayer
extends ArgumentLayer {
    public RemoveFriendArgumentLayer() {
        super("remove", 0);
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printError("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435: .friend remove <\u0438\u043c\u044f>");
            return;
        }
        String friendName = ((String)arguments.getFirst()).toLowerCase();
        if (!FriendManager.isFriend(friendName)) {
            this.printError("\u0418\u0433\u0440\u043e\u043a " + friendName + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d \u0432 \u0441\u043f\u0438\u0441\u043a\u0435 \u0434\u0440\u0443\u0437\u0435\u0439!");
            return;
        }
        FriendManager.deleteFriend(friendName);
        this.printSuccess("\u0414\u0440\u0443\u0433 \u0443\u0434\u0430\u043b\u0435\u043d: " + friendName);
    }
}
