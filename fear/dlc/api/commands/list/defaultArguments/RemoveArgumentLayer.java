/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.defaultArguments;

import fear.dlc.api.commands.ArgumentLayer;
import java.util.List;
import java.util.function.Supplier;

public class RemoveArgumentLayer
extends ArgumentLayer {
    List<String> list;

    public RemoveArgumentLayer(Integer index, Supplier<List<String>> listSupplier) {
        super("remove", index);
        this.list = listSupplier.get();
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printError("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435: remove <\u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435>");
            return;
        }
        String value = arguments.getFirst();
        if (!this.list.contains(value)) {
            this.printError("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e: " + value);
            return;
        }
        this.list.remove(value);
        this.printSuccess("\u0423\u0441\u043f\u0435\u0448\u043d\u043e \u0443\u0434\u0430\u043b\u0435\u043d\u043e: " + value);
    }
}
