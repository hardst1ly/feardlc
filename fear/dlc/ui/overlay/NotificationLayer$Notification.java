/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

import fear.dlc.api.animations.Animation;
import fear.dlc.api.animations.Direction;
import fear.dlc.api.animations.implement.DecelerateAnimation;
import fear.dlc.ui.overlay.NotificationLayer;

private static class NotificationLayer.Notification {
    private final String text;
    private final NotificationLayer.NotificationType type;
    private final long startTime;
    private final long duration;
    private final Animation animation;

    public NotificationLayer.Notification(String text, NotificationLayer.NotificationType type) {
        this.text = text;
        this.type = type;
        this.startTime = System.currentTimeMillis();
        this.duration = 1000L;
        Animation anim = new DecelerateAnimation().setMs(400).setValue(1);
        anim.setDirection(Direction.FORWARDS);
        this.animation = anim;
    }

    public String getText() {
        return this.text;
    }

    public NotificationLayer.NotificationType getType() {
        return this.type;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getDuration() {
        return this.duration;
    }

    public Animation getAnimation() {
        return this.animation;
    }
}
