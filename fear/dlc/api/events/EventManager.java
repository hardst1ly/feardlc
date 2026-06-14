/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events;

import fear.dlc.FearDCP;
import fear.dlc.api.events.EventLayer;

public class EventManager {
    public static void register(Class<?> clazz) {
        FearDCP.getInstance().getEventBus().register(clazz);
    }

    public static void call(EventLayer eventLayer) {
        FearDCP.getInstance().getEventBus().post(eventLayer);
    }

    public static void cancel(EventLayer eventLayer) {
        eventLayer.cancel();
    }

    public static boolean isCancel(EventLayer eventLayer) {
        return eventLayer.isCanceled();
    }
}
