/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.utility;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class MsdfUtil {
    public static String cutString(String string, int size, float width) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] split = string.split(" ");
        AtomicReference<Float> lineWidth = new AtomicReference(0f);
        Arrays.stream(split).forEach(MsdfUtil::lambda$cutString$0 /* captured: size, lineWidth, width, stringBuilder */);
        return stringBuilder.toString();
    }
}
