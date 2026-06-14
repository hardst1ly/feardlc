/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.collection;

import fear.dlc.modules.settings.impl.Collection;
import fear.dlc.ui.clickGui.Helper;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import java.util.List;
import java.util.Objects;

public final class CollectionHelper {
    public static List<SettingComponent> childSettingComponents(Collection collection) {
        return collection.getSettingLayers().stream().map(Helper::find).filter(Objects::nonNull).toList();
    }

    public static float collectionHeight(List<SettingComponent> settingComponents) {
        return ((Float)settingComponents.stream().filter(CollectionHelper::lambda$collectionHeight$0).map(CollectionHelper::lambda$collectionHeight$1).reduce(0f, Float::sum)).floatValue();
    }
}
