/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.keyboard;

import fear.dlc.Api;
import java.util.Locale;
import net.minecraft.class_304;
import net.minecraft.class_3675;

public class KeyBoardUtil
implements Api {
    public static boolean isKeyPressed(class_304 key) {
        if (key.method_1429().method_1444() == -1) {
            return false;
        }
        return class_3675.method_15987(mc.method_22683().method_4490(), class_3675.method_15981(key.method_1428()).method_1444());
    }

    public static String translate(int keyCodeIn) {
        if (keyCodeIn == -1) {
            return "N/A";
        }
        if (keyCodeIn < -90) {
            class_306 key = class_3675.class_307.field_1672.method_1447(keyCodeIn + 100);
        } else {
            if (keyCodeIn < 8) {
                class_306 key = class_3675.class_307.field_1672.method_1447(keyCodeIn);
            } else {
                class_306 key = class_3675.class_307.field_1668.method_1447(keyCodeIn);
            }
        }
        return key.method_1441().replace("key.keyboard.", "").replace("key.mouse.", "MOUSE ").toUpperCase(Locale.ROOT);
    }
}
