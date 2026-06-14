/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.gps;

import com.google.common.collect.Lists;
import fear.dlc.api.commands.CommandLayer;
import fear.dlc.api.commands.list.gps.AddArgumentLayer;
import fear.dlc.api.commands.list.gps.RemoveArgumentLayer;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public class GpsLayerCommand
extends CommandLayer {
    public GpsLayerCommand() {
        String[] tmp0 = new String[1];
        tmp0[0] = "gps";
        super(Lists.newArrayList(tmp0));
        this.getArguments().add(new AddArgumentLayer());
        this.getArguments().add(new RemoveArgumentLayer());
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printHelp();
            return;
        }
        this.getArguments().stream().filter(GpsLayerCommand::lambda$execute$0 /* captured: arguments */).filter(GpsLayerCommand::lambda$execute$1 /* captured: arguments */).forEach(GpsLayerCommand::lambda$execute$2 /* captured: arguments */);
    }

    private void printHelp() {
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043a\u043e\u043c\u0430\u043d\u0434\u044b:\n").method_10862(class_2583.field_24360.method_36139(-1));
        mc.field_1705.method_1743().method_1812(prefix.method_10852(msg));
        this.printCommand(".gps add <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435> <x> <y> <z>", "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c GPS \u043c\u0435\u0442\u043a\u0443", 50);
        this.printCommand(".gps remove <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "\u0423\u0434\u0430\u043b\u0438\u0442\u044c GPS \u043c\u0435\u0442\u043a\u0443", 100);
    }

    private void printCommand(String command, String description, int offset) {
        class_5250 cmd = class_2561.method_43470("  " + command + " ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(offset)));
        class_5250 desc = class_2561.method_43470("- " + description).method_10862(class_2583.field_24360.method_36139(-5592406));
        mc.field_1705.method_1743().method_1812(cmd.method_10852(desc));
    }
}
