/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

public static enum NotificationLayer.NotificationType {
    public static final NotificationLayer.NotificationType INFO = new NotificationLayer.NotificationType("INFO", 0);
    public static final NotificationLayer.NotificationType SUCCESS = new NotificationLayer.NotificationType("SUCCESS", 1);
    public static final NotificationLayer.NotificationType ERROR = new NotificationLayer.NotificationType("ERROR", 2);
    public static final NotificationLayer.NotificationType WARNING = new NotificationLayer.NotificationType("WARNING", 3);
    private static final /* synthetic */ NotificationLayer.NotificationType[] $VALUES;

    public static NotificationLayer.NotificationType[] values() {
        return (NotificationLayer.NotificationType[])$VALUES.clone();
    }

    public static NotificationLayer.NotificationType valueOf(String name) {
        return (NotificationLayer.NotificationType)Enum.valueOf(NotificationLayer.NotificationType.class, name);
    }

    private NotificationLayer.NotificationType() {
        super(var1, var2);
    }

    static {
        $VALUES = NotificationLayer.NotificationType.$values();
    }
}
