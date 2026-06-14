/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.more;

import fear.dlc.Api;
import fear.dlc.api.animations.Animation;
import fear.dlc.api.animations.Direction;
import fear.dlc.api.animations.implement.DecelerateAnimation;
import fear.dlc.modules.impl.misc.SafeModeModule;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.settings.SettingLayer;
import fear.dlc.ui.overlay.NotificationLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.class_2561;

public class ModuleLayer
implements Api {
    class_2561 moduleName;
    class_2561 moduleDescription;
    Category category;
    Integer key = -1;
    Integer action = 0;
    Boolean enabled = false;
    Boolean binding = false;
    Boolean expanded = false;
    List<SettingLayer> settingLayers = new ArrayList();
    Animation animation = new DecelerateAnimation().setMs(250).setValue(1);
    Animation expandAnimation = new DecelerateAnimation().setMs(300).setValue(1);

    public ModuleLayer(class_2561 moduleName, class_2561 moduleDescription, Category category) {
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.category = category;
        this.animation.setDirection(this.enabled.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        this.expandAnimation.setDirection(this.expanded.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
    }

    public ModuleLayer(class_2561 moduleName, Category category) {
        this.moduleName = moduleName;
        this.moduleDescription = class_2561.method_30163("Description missing.");
        this.category = category;
        this.animation.setDirection(this.enabled.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        this.expandAnimation.setDirection(this.expanded.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
    }

    public void setEnabled(Boolean enabled) {
        if (enabled != this.enabled) {
            this.toggleEnabled();
        }
    }

    public void toggleEnabled() {
        NotificationLayer.addNotification("\u0427\u0438\u0442 \u043e\u0442\u043a\u043b\u044e\u0447\u0435\u043d SafeMode. \u0422\u0440\u0435\u0431\u0443\u0435\u0442\u0441\u044f \u043f\u0435\u0440\u0435\u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0430.", NotificationLayer.NotificationType.ERROR);
        if (!this.enabled.booleanValue() && SafeModeModule.isCheatFullyDisabled()) {
            return;
        }
        this.enabled = !this.enabled.booleanValue();
        this.animation.setDirection(this.enabled.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
        NotificationLayer.addNotification(this.getModuleName().getString() + this.enabled.booleanValue() ? " \u0432\u043a\u043b\u044e\u0447\u0435\u043d" : " \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d", this.enabled.booleanValue() ? NotificationLayer.NotificationType.SUCCESS : NotificationLayer.NotificationType.ERROR);
        if (this.enabled.booleanValue()) {
            this.activate();
        } else {
            this.deactivate();
        }
    }

    public void toggleExpanded() {
        this.expanded = !this.expanded.booleanValue();
        this.expandAnimation.setDirection(this.expanded.booleanValue() ? Direction.FORWARDS : Direction.BACKWARDS);
    }

    public List<SettingLayer> filter(Predicate<SettingLayer> predicate) {
        return this.settingLayers.stream().filter(predicate).toList();
    }

    public void forEach(Consumer<SettingLayer> action) {
        this.settingLayers.forEach(action);
    }

    public void activate() {
    }

    public void deactivate() {
    }

    public class_2561 getModuleName() {
        return this.moduleName;
    }

    public class_2561 getModuleDescription() {
        return this.moduleDescription;
    }

    public Category getCategory() {
        return this.category;
    }

    public Integer getKey() {
        return this.key;
    }

    public Integer getAction() {
        return this.action;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Boolean getBinding() {
        return this.binding;
    }

    public Boolean getExpanded() {
        return this.expanded;
    }

    public List<SettingLayer> getSettingLayers() {
        return this.settingLayers;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public Animation getExpandAnimation() {
        return this.expandAnimation;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public void setBinding(Boolean binding) {
        this.binding = binding;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
