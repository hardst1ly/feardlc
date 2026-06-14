/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.overlay;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.api.animations.Direction;
import fear.dlc.api.draggable.DraggableLayer;
import fear.dlc.modules.impl.render.HudModule;
import fear.dlc.ui.overlay.OverlayRenderer;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.minecraft.class_332;
import net.minecraft.class_408;
import net.minecraft.class_9779;

public class NotificationLayer
extends DraggableLayer {
    private static final List<Notification> notifications = new CopyOnWriteArrayList();
    private static final int MAX_NOTIFICATIONS = 5;
    private static final float NOTIFICATION_HEIGHT = 22f;
    private static final float NOTIFICATION_SPACING = 6f;
    private static final float PADDING = 6f;
    private static final float ICON_SIZE = 8f;
    static Supplier<HudModule> module = Suppliers.memoize(NotificationLayer::lambda$static$0);

    public NotificationLayer() {
        super(10f, 60f, 200f, 22f, NotificationLayer::lambda$new$1);
    }

    public static void addNotification(String text, NotificationType type) {
        if (notifications.size() >= 5) {
            notifications.remove(0);
        }
        notifications.add(new Notification(text, type));
    }

    public void render(class_332 context, double mouseX, double mouseY, class_9779 tickCounter) {
        boolean isChatOpen = mc.field_1755 instanceof class_408;
        if (isChatOpen) {
            Notification exampleNotification = new Notification("\u041f\u0440\u0438\u043c\u0435\u0440 \u0443\u0432\u0435\u0434\u043e\u043c\u043b\u0435\u043d\u0438\u044f", NotificationType.INFO);
            float textWidth = Api.inter().getWidth(exampleNotification.text, 6.5f);
            float iconWidth = Api.inter().getWidth("\u2139", 8f, 0.15f, 0f);
            float notificationWidth = textWidth + 24f + iconWidth + 4f;
            this.setWidth(notificationWidth);
            this.renderNotification(context, this.getX().floatValue(), this.getY().floatValue(), notificationWidth, 22f, exampleNotification, 1f, 1f);
            return;
        }
        if (notifications.isEmpty()) {
            return;
        }
        float currentY = this.getY().floatValue();
        List<Notification> toRemove = new ArrayList();
        Iterator iconWidth = notifications.iterator();
        while (iconWidth.hasNext()) {
            Notification notification = iconWidth.next();
            if (System.currentTimeMillis() - notification.startTime > notification.duration) {
                if (notification.animation.getDirection() != Direction.FORWARDS) continue;
                notification.animation.setDirection(Direction.BACKWARDS);
            }
            while (notification.animation.isFinished(Direction.BACKWARDS)) {
                toRemove.add(notification);
            }
            float animationProgress = notification.animation.getOutput().floatValue();
            float textWidth = Api.inter().getWidth(notification.text, 6.5f);
            float iconWidth = Api.inter().getWidth("\u2713", 8f, 0.15f, 0f);
            float notificationWidth = textWidth + 24f + iconWidth + 4f;
            float slideOffset = (1f - animationProgress) * 80f;
            float alpha = animationProgress;
            float bounceProgress = Math.min(1f, animationProgress * 1.2f);
            float bounce = (1 - Math.pow((double)(1f - bounceProgress), 3));
            this.renderNotification(context, this.getX().floatValue() + slideOffset, currentY, notificationWidth, 22f, notification, alpha, bounce);
            currentY = currentY + 28f * animationProgress;
        }
        notifications.removeAll(toRemove);
    }

    private void renderNotification(class_332 context, float x, float y, float width, float height, Notification notification, float alpha, float bounce) {
        int alphaValue = (alpha * 255f);
        float finalWidth = width * bounce;
        float finalHeight = height * bounce;
        float offsetX = (width - finalWidth) / 2f;
        float offsetY = (height - finalHeight) / 2f;
        OverlayRenderer.rect(context, x + offsetX, y + offsetY, finalWidth, finalHeight);
        switch (notification.type.ordinal()) {
            case 1:
                String icon = "\u2713";
                break;
            case 2:
                icon = "\u2715";
                break;
            case 3:
                icon = "\u26a0";
                break;
            default:
                icon = "\u2139";
                break;
        }
        switch (notification.type.ordinal()) {
            case 1:
                int iconColor = ColorUtility.applyOpacity(-11141291, alphaValue);
                break;
            case 2:
                iconColor = ColorUtility.applyOpacity(-43691, alphaValue);
                break;
            case 3:
                iconColor = ColorUtility.applyOpacity(-22016, alphaValue);
                break;
            default:
                iconColor = ColorUtility.applyOpacity(-7893505, alphaValue);
                break;
        }
        float iconWidth = Api.inter().getWidth(icon, 8f, 0.15f, 0f);
        float textWidth = Api.inter().getWidth(notification.text, 6.5f);
        float textHeight = Api.inter().getHeight(notification.text, 6.5f);
        float contentWidth = iconWidth + 4f + textWidth;
        float contentStartX = x + offsetX + (finalWidth - contentWidth) / 2f;
        ((BuiltText)Api.text().font(Api.inter()).text(icon).color(iconColor).size(8f).thickness(0.15f).build()).render(context.method_51448().method_23760().method_23761(), contentStartX, y + offsetY + (finalHeight - 8f) / 2f - 1f);
        switch (notification.type.ordinal()) {
            case 1:
                int textColor = ColorUtility.applyOpacity(-11141291, alphaValue);
                break;
            case 2:
                textColor = ColorUtility.applyOpacity(-43691, alphaValue);
                break;
            case 3:
                textColor = ColorUtility.applyOpacity(-22016, alphaValue);
                break;
            default:
                textColor = ColorUtility.applyOpacity(-11167233, alphaValue);
                break;
        }
        ((BuiltText)Api.text().font(Api.inter()).text(notification.text).color(textColor).size(6.5f).build()).render(context.method_51448().method_23760().method_23761(), contentStartX + iconWidth + 4f, y + offsetY + (finalHeight - textHeight) / 2f);
    }

    private int getAccentColor(NotificationType type) {
        switch (type.ordinal()) {
            case 1:
                return -11141291;
            case 2:
                return -43691;
            case 3:
                return -22016;
            default:
                return -11167233;
        }
    }
}
