/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.more;

import net.minecraft.class_2960;

public enum Category {
    public static final Category Combat = new Category("Combat", 0, class_2960.method_60655("funpay", "images/combat.png"));
    public static final Category Movement = new Category("Movement", 1, class_2960.method_60655("funpay", "images/movement.png"));
    public static final Category Render = new Category("Render", 2, class_2960.method_60655("funpay", "images/render.png"));
    public static final Category Player = new Category("Player", 3, class_2960.method_60655("funpay", "images/player.png"));
    public static final Category Misc = new Category("Misc", 4, class_2960.method_60655("funpay", "images/miscellaneous.png"));
    private final class_2960 icon;
    private static final /* synthetic */ Category[] $VALUES;

    public static Category[] values() {
        return $VALUES.clone();
    }

    public static Category valueOf(String name) {
        return Enum.valueOf(Category.class, name);
    }

    public class_2960 getIcon() {
        return this.icon;
    }

    private Category(class_2960 icon) {
        super(var1, var2);
        this.icon = icon;
    }

    static {
        $VALUES = Category.$values();
    }
}
