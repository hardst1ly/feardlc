/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.friend;

import com.google.common.collect.Lists;
import fear.dlc.api.commands.ArgumentLayer;
import fear.dlc.api.commands.CommandLayer;
import fear.dlc.api.commands.list.friend.AddFriendArgumentLayer;
import fear.dlc.api.commands.list.friend.ListFriendArgumentLayer;
import fear.dlc.api.commands.list.friend.RemoveFriendArgumentLayer;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public class FriendLayerCommand
extends CommandLayer {
    public FriendLayerCommand() {
        String[] tmp0 = new String[2];
        tmp0[0] = "friend";
        tmp0[1] = "f";
        super(Lists.newArrayList(tmp0));
        this.getArguments().add(new AddFriendArgumentLayer());
        this.getArguments().add(new RemoveFriendArgumentLayer());
        this.getArguments().add(new ListFriendArgumentLayer());
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printHelp();
            return;
        }
        boolean executed = false;
        for (ArgumentLayer arg : this.getArguments()) {
            if ((arg.getIndex().intValue() >= arguments.size()) && (String)arguments.get(arg.getIndex().intValue())).equalsIgnoreCase(arg.getArgument()) continue;
            arg.execute(arguments.subList(1, arguments.size()));
            executed = true;
            break;
        }
        if (!executed) {
            this.printError("\u041d\u0435\u0438\u0437\u0432\u0435\u0441\u0442\u043d\u0430\u044f \u043f\u043e\u0434\u043a\u043e\u043c\u0430\u043d\u0434\u0430: " + (String)arguments.get(0));
            this.printHelp();
        }
    }

    private void printHelp() {
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043a\u043e\u043c\u0430\u043d\u0434\u044b:\n").method_10862(class_2583.field_24360.method_36139(-1));
        mc.field_1705.method_1743().method_1812(prefix.method_10852(msg));
        this.printCommand(".friend add <\u0438\u043c\u044f>", "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430", 50);
        this.printCommand(".friend remove <\u0438\u043c\u044f>", "\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430", 100);
        this.printCommand(".friend list", "\u041f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439", 150);
    }

    private void printCommand(String command, String description, int offset) {
        class_5250 cmd = class_2561.method_43470("  " + command + " ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(offset)));
        class_5250 desc = class_2561.method_43470("- " + description).method_10862(class_2583.field_24360.method_36139(-5592406));
        mc.field_1705.method_1743().method_1812(cmd.method_10852(desc));
    }
}
