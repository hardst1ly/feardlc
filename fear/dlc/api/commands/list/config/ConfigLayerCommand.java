/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.config;

import com.google.common.collect.Lists;
import fear.dlc.api.commands.CommandLayer;
import fear.dlc.api.commands.list.config.DirArgumentLayer;
import fear.dlc.api.commands.list.config.ListConfigArgumentLayer;
import fear.dlc.api.commands.list.config.LoadArgumentLayer;
import fear.dlc.api.commands.list.config.SaveArgumentLayer;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public class ConfigLayerCommand
extends CommandLayer {
    public ConfigLayerCommand() {
        String[] tmp0 = new String[2];
        tmp0[0] = "config";
        tmp0[1] = "cfg";
        super(Lists.newArrayList(tmp0));
        this.getArguments().add(new DirArgumentLayer());
        this.getArguments().add(new LoadArgumentLayer());
        this.getArguments().add(new SaveArgumentLayer());
        this.getArguments().add(new ListConfigArgumentLayer());
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printHelp();
            return;
        }
        this.getArguments().stream().filter(ConfigLayerCommand::lambda$execute$0 /* captured: arguments */).filter(ConfigLayerCommand::lambda$execute$1 /* captured: arguments */).forEach(ConfigLayerCommand::lambda$execute$2 /* captured: arguments */);
    }

    private void printHelp() {
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043a\u043e\u043c\u0430\u043d\u0434\u044b:\n").method_10862(class_2583.field_24360.method_36139(-1));
        mc.field_1705.method_1743().method_1812(prefix.method_10852(msg));
        this.printCommand(".cfg save <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044e", 50);
        this.printCommand(".cfg load <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044e", 100);
        this.printCommand(".cfg dir", "\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043f\u0430\u043f\u043a\u0443 \u0441 \u043a\u043e\u043d\u0444\u0438\u0433\u0430\u043c\u0438", 150);
        this.printCommand(".cfg list", "\u0412\u044b\u0432\u0435\u0434\u0435\u0442 \u0432\u0441\u0435 \u043a\u043e\u043d\u0444\u0438\u0433\u0438", 150);
    }

    private void printCommand(String command, String description, int offset) {
        class_5250 cmd = class_2561.method_43470("  " + command + " ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(offset)));
        class_5250 desc = class_2561.method_43470("- " + description).method_10862(class_2583.field_24360.method_36139(-5592406));
        mc.field_1705.method_1743().method_1812(cmd.method_10852(desc));
    }
}
