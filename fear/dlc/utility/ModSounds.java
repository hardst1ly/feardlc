/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility;

import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_7923;

public class ModSounds {
    public static final class_3414 OPEN_GUI = ModSounds.register("open_gui");
    public static final class_3414 CHEST_DISABLED = ModSounds.register("chest_disabled");

    private static class_3414 register(String id) {
        class_2960 identifier = class_2960.method_60655("pasxalka", id);
        return class_2378.method_10230(class_7923.field_41172, identifier, class_3414.method_47908(identifier));
    }

    public static void init() {
        OPEN_GUI.toString();
        CHEST_DISABLED.toString();
    }
}
