/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.record;

import com.google.common.collect.Lists;
import fear.dlc.api.commands.CommandLayer;
import fear.dlc.api.commands.list.record.DirArgumentLayer;
import fear.dlc.api.commands.list.record.ListArgumentLayer;
import fear.dlc.api.commands.list.record.LoadArgumentLayer;
import fear.dlc.api.commands.list.record.StartArgumentLayer;
import fear.dlc.api.commands.list.record.StopArgumentLayer;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public class RecordLayerCommand
extends CommandLayer {
    public RecordLayerCommand() {
        String[] tmp0 = new String[2];
        tmp0[0] = "record";
        tmp0[1] = "rec";
        super(Lists.newArrayList(tmp0));
        this.getArguments().add(new StartArgumentLayer());
        this.getArguments().add(new StopArgumentLayer());
        this.getArguments().add(new ListArgumentLayer());
        this.getArguments().add(new LoadArgumentLayer());
        this.getArguments().add(new DirArgumentLayer());
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printHelp();
            return;
        }
        this.getArguments().stream().filter(RecordLayerCommand::lambda$execute$0 /* captured: arguments */).filter(RecordLayerCommand::lambda$execute$1 /* captured: arguments */).forEach(RecordLayerCommand::lambda$execute$2 /* captured: arguments */);
    }

    private void printHelp() {
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043a\u043e\u043c\u0430\u043d\u0434\u044b:\n").method_10862(class_2583.field_24360.method_36139(-1));
        mc.field_1705.method_1743().method_1812(prefix.method_10852(msg));
        this.printCommand(".record start", "\u041d\u0430\u0447\u0430\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u0439 \u043c\u044b\u0448\u043a\u0438", 50);
        this.printCommand(".record stop", "\u041e\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0438 \u0441\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c", 100);
        this.printCommand(".record list", "\u041f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u0432\u0441\u0435 \u0437\u0430\u043f\u0438\u0441\u0438", 150);
        this.printCommand(".record load <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>", "\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u043f\u0440\u043e\u0444\u0438\u043b\u044c \u0432 \u0440\u0435\u0436\u0438\u043c Neuro", 200);
        this.printCommand(".record dir", "\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043f\u0430\u043f\u043a\u0443 \u0441 \u0437\u0430\u043f\u0438\u0441\u044f\u043c\u0438", 250);
    }

    private void printCommand(String command, String description, int offset) {
        class_5250 cmd = class_2561.method_43470("  " + command + " ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(offset)));
        class_5250 desc = class_2561.method_43470("- " + description).method_10862(class_2583.field_24360.method_36139(-5592406));
        mc.field_1705.method_1743().method_1812(cmd.method_10852(desc));
    }
}
