/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.collection;

import fear.dlc.Api;
import fear.dlc.modules.settings.impl.Collection;
import fear.dlc.ui.clickGui.Component;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import fear.dlc.ui.clickGui.components.settings.collection.CollectionHelper;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.class_332;

public class CollectionComponent
extends SettingComponent {
    List<SettingComponent> childSettingsComponents = new ArrayList();

    public CollectionComponent(Collection collection) {
        super(collection);
        this.childSettingsComponents.addAll(CollectionHelper.childSettingComponents(collection));
    }

    public void init() {
        this.childSettingsComponents.forEach(Component::init);
        this.size(110f, Api.inter().getHeight(this.getSettingLayer().getName().getString(), 7.5f) + 5f + CollectionHelper.collectionHeight(this.childSettingsComponents) + 2.5f);
    }

    public CollectionComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        ((BuiltText)Api.text().font(Api.inter()).color(-1).text(this.getSettingLayer().getName().getString()).size(7.5f).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + this.getWidth() / 2f - Api.inter().getWidth(this.getSettingLayer().getName().getString(), 7.5f) / 2f, this.getY());
        AtomicReference<Float> offset = new AtomicReference(0f);
        this.childSettingsComponents.forEach(this::lambda$render$0 /* captured: offset, context, mouseX, mouseY, delta */);
        return null;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return this.childSettingsComponents.stream().anyMatch(CollectionComponent::lambda$mouseReleased$1 /* captured: mouseX, mouseY, button */);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.childSettingsComponents.stream().anyMatch(CollectionComponent::lambda$mouseClicked$2 /* captured: mouseX, mouseY, button */);
    }
}
