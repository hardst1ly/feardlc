/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.gps;

import java.util.ArrayList;
import java.util.List;

public class GpsRepository {
    private static final List<WayPoint> gps = new ArrayList();

    public static List<WayPoint> getGps() {
        return gps;
    }
}
