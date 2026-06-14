/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.defaultArguments;

import fear.dlc.api.commands.ArgumentLayer;
import java.util.List;
import java.util.function.Supplier;

public class AddArgumentLayer
extends ArgumentLayer {
    List<String> list;

    public AddArgumentLayer(Integer index, Supplier<List<String>> listSupplier) {
        super("add", index);
        this.list = listSupplier.get();
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printError("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435: add <\u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435>");
            return;
        }
        String value = arguments.getFirst();
        if (this.list.contains(value)) {
            this.printError("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u0443\u0436\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442: " + value);
            return;
        }
        this.list.add(value);
        this.printSuccess("\u0423\u0441\u043f\u0435\u0448\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u043e: " + value);
    }
}
