/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.bind;

import com.google.common.collect.Lists;
import fear.dlc.FearDCP;
import fear.dlc.api.commands.CommandLayer;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.utility.keyboard.KeyBoardUtil;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public class BindLayerCommand
extends CommandLayer {
    public BindLayerCommand() {
        String[] tmp0 = new String[2];
        tmp0[0] = "bind";
        tmp0[1] = "b";
        super(Lists.newArrayList(tmp0));
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printUsage();
            return;
        }
        String subCommand = ((String)arguments.get(0)).toLowerCase();
        if (subCommand.equals("list") || subCommand.equals("l")) {
            this.listBinds();
        } else {
            this.printUsage();
        }
    }

    private void listBinds() {
        List<ModuleLayer> modulesWithBinds = FearDCP.getInstance().getModuleRepository().getModuleLayers().stream().filter(BindLayerCommand::lambda$listBinds$0).sorted(Comparator.comparing(BindLayerCommand::lambda$listBinds$1)).collect(Collectors.toList());
        if (modulesWithBinds.isEmpty()) {
            this.printError("\u041d\u0435\u0442 \u043c\u043e\u0434\u0443\u043b\u0435\u0439 \u0441 \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u043d\u044b\u043c\u0438 \u0431\u0438\u043d\u0434\u0430\u043c\u0438");
            return;
        }
        this.printHeader(modulesWithBinds.size());
        int offset = 0;
        for (ModuleLayer module : modulesWithBinds) {
            String moduleName = module.getModuleName().getString();
            String keyName = KeyBoardUtil.translate(module.getKey().intValue());
            String category = module.getCategory().name();
            this.printBind(moduleName, keyName, category, offset);
            offset += 30;
        }
    }

    private void printHeader(int count) {
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470("\u0421\u043f\u0438\u0441\u043e\u043a \u0431\u0438\u043d\u0434\u043e\u0432 (" + count + "):\n").method_10862(class_2583.field_24360.method_36139(-1));
        mc.field_1705.method_1743().method_1812(prefix.method_10852(msg));
    }

    private void printBind(String moduleName, String keyName, String category, int offset) {
        class_5250 moduleText = class_2561.method_43470("  " + moduleName + " ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(offset)));
        class_5250 keyText = class_2561.method_43470("[" + keyName + "] ").method_10862(class_2583.field_24360.method_36139(-256));
        class_5250 categoryText = class_2561.method_43470("(" + category + ")").method_10862(class_2583.field_24360.method_36139(-7829368));
        mc.field_1705.method_1743().method_1812(moduleText.method_10852(keyText).method_10852(categoryText));
    }

    private void printUsage() {
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435: .bind list - \u043f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u0432\u0441\u0435 \u0431\u0438\u043d\u0434\u044b").method_10862(class_2583.field_24360.method_36139(-5592406));
        mc.field_1705.method_1743().method_1812(prefix.method_10852(msg));
    }

    public void printError(String error) {
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(-43691));
        class_5250 msg = class_2561.method_43470(error).method_10862(class_2583.field_24360.method_36139(-21846));
        mc.field_1705.method_1743().method_1812(prefix.method_10852(msg));
    }
}
