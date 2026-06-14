/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.config;

import fear.dlc.api.commands.ArgumentLayer;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LoadArgumentLayer
extends ArgumentLayer {
    public LoadArgumentLayer() {
        super("load", 0);
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printError("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435: .cfg load <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>");
            return;
        }
        String configName = arguments.getFirst();
        this.printInfo("\u0417\u0430\u0433\u0440\u0443\u0437\u043a\u0430 \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u0438: " + configName + "...");
        CompletableFuture.runAsync(this::lambda$execute$2 /* captured: configName */);
    }
}
