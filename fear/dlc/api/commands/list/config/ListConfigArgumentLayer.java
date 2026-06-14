/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.config;

import fear.dlc.api.commands.ArgumentLayer;
import java.io.File;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public class ListConfigArgumentLayer
extends ArgumentLayer {
    public ListConfigArgumentLayer() {
        super("list", 0);
    }

    public void execute(List<String> arguments) {
        File configsDir = new File(mc.field_1697, "rivalvisual/configs");
        if (!configsDir.exists() || !configsDir.isDirectory()) {
            this.printInfo("\u041f\u0430\u043f\u043a\u0430 \u0441 \u043a\u043e\u043d\u0444\u0438\u0433\u0430\u043c\u0438 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430!");
            return;
        }
        File[] files = configsDir.listFiles(ListConfigArgumentLayer::lambda$execute$0);
        if (files == null || files.length == 0) {
            this.printInfo("\u041a\u043e\u043d\u0444\u0438\u0433\u043e\u0432 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e!");
            return;
        }
        this.printInfo("\u0421\u043f\u0438\u0441\u043e\u043a \u043a\u043e\u043d\u0444\u0438\u0433\u043e\u0432 (" + files.length + "):");
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName().replaceFirst("\\.json$", "");
            class_5250 prefix = class_2561.method_43470("  " + i + 1 + ". ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(i * 50)));
            class_5250 nameText = class_2561.method_43470(name).method_10862(class_2583.field_24360.method_36139(-1));
            mc.field_1705.method_1743().method_1812(prefix.method_10852(nameText));
        }
    }
}
