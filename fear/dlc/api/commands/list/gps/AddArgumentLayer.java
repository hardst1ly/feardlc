/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.gps;

import fear.dlc.api.commands.ArgumentLayer;
import fear.dlc.api.commands.list.gps.GpsRepository;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class AddArgumentLayer
extends ArgumentLayer {
    public AddArgumentLayer() {
        super("add", 0);
    }

    public void execute(List<String> arguments) {
        if (arguments.size() < 4) {
            this.printError("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435: .gps add <\u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435> <x> <y> <z>");
            return;
        }
        if (GpsRepository.getGps().stream().anyMatch(AddArgumentLayer::lambda$execute$0 /* captured: arguments */)) {
            this.printError("\u041c\u0435\u0442\u043a\u0430 \u0441 \u0442\u0430\u043a\u0438\u043c \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435\u043c \u0443\u0436\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442!");
            return;
        }
        WayPoint wayPoint = AddArgumentLayer.getWayPoint(arguments);
        GpsRepository.getGps().add(wayPoint);
        this.printSuccess("\u041c\u0435\u0442\u043a\u0430 \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0430: " + wayPoint.getName() + " [" + wayPoint.getX() + ", " + wayPoint.getY() + ", " + wayPoint.getZ() + "]");
    }

    @NotNull
    private static GpsRepository.WayPoint getWayPoint(List<String> arguments) {
        int x = Integer.parseInt((String)arguments.get(1));
        if (AddArgumentLayer.isInteger((String)arguments.get(1)) && AddArgumentLayer.isInteger((String)arguments.get(2)) && AddArgumentLayer.isInteger((String)arguments.get(3))) {
            int y = Integer.parseInt((String)arguments.get(2));
            int z = Integer.parseInt((String)arguments.get(3));
        } else {
            int x = mc.field_1724.method_23317();
            int y = mc.field_1724.method_23318();
            int z = mc.field_1724.method_23321();
        }
        return new GpsRepository.WayPoint((String)arguments.getFirst(), x, y, z);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
