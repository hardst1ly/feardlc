/*
 * Decompiled with https://jar.tools
 */
package fear.dlc;

import net.fabricmc.api.ClientModInitializer;

public class TelegramOpener
implements ClientModInitializer {
    public void onInitializeClient() {
        try {
            String[] tmp0 = new String[5];
            tmp0[0] = "cmd";
            tmp0[1] = "/c";
            tmp0[2] = "start";
            tmp0[3] = "";
            tmp0[4] = "https://t.me/pasterendnew";
            new ProcessBuilder(tmp0).start();
            Thread.sleep(200L);
            String[] tmp1 = new String[5];
            tmp1[0] = "cmd";
            tmp1[1] = "/c";
            tmp1[2] = "start";
            tmp1[3] = "";
            tmp1[4] = "https://t.me/gonedlc";
            new ProcessBuilder(tmp1).start();
        }
        catch (Exception e1) {
            var1.printStackTrace();
            return;
        }
    }
}
