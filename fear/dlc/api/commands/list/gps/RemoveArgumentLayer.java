/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.gps;

import fear.dlc.api.commands.ArgumentLayer;
import fear.dlc.api.commands.list.gps.GpsRepository;
import java.util.List;

public class RemoveArgumentLayer
extends ArgumentLayer {
    public RemoveArgumentLayer() {
        super("remove", 0);
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printError("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435: .gps remove <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435>");
            return;
        }
        String name = arguments.getFirst();
        boolean removed = GpsRepository.getGps().removeIf(RemoveArgumentLayer::lambda$execute$0 /* captured: name */);
        if (removed) {
            this.printSuccess("\u041c\u0435\u0442\u043a\u0430 \u0443\u0434\u0430\u043b\u0435\u043d\u0430: " + name);
        } else {
            this.printError("\u041c\u0435\u0442\u043a\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430: " + name);
        }
    }
}
