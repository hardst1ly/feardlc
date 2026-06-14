/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.config;

import fear.dlc.api.commands.ArgumentLayer;
import java.io.IOException;
import java.util.List;

public class DirArgumentLayer
extends ArgumentLayer {
    public DirArgumentLayer() {
        super("dir", 0);
    }

    public void execute(List<String> arguments) {
        try {
            Runtime.getRuntime().exec("explorer " + mc.field_1697.getAbsolutePath() + "\\rivalvisual\\configs");
            this.printSuccess("\u041f\u0430\u043f\u043a\u0430 \u0441 \u043a\u043e\u043d\u0444\u0438\u0433\u0430\u043c\u0438 \u043e\u0442\u043a\u0440\u044b\u0442\u0430");
        }
        catch (IOException e) {
            this.printError("\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u043e\u0442\u043a\u0440\u044b\u0442\u0438\u0438 \u043f\u0430\u043f\u043a\u0438: " + e.getMessage());
            return;
        }
    }
}
